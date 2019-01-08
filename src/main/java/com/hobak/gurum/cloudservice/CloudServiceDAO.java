package com.hobak.gurum.cloudservice;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class CloudServiceDAO {
	@Inject
	private SqlSession sqlSession;
	
	@Inject
	CloudServiceDownload csDownload;
	
	/*
	 * getUserData
	 * 해당 경로의 파일 정보를 DB에서 가져와 실제 서버 로컬 폴더의 해당 파일을 출력함
	 * */
	public void getUserData(HttpServletRequest request, Model model, String mid, String path) {
		File file = new File(path+"\\");
		File[] list = file.listFiles();
		
		List<String> dirs = new ArrayList<String>();
		
		for(File temp : list) {
			if(temp.isDirectory()) {
				dirs.add(temp.getName());
			}
		}
		
		List<CloudServiceDTO> userData = sqlSession.getMapper(CloudServiceMapper.class).getUserData(mid, path);
		String userDefaultPath = (String)request.getSession().getAttribute("userDefaultPath");
		String nowPath = (String)request.getSession().getAttribute("nowPath");
		String displayPath = "";
		boolean flag = false;
		
		if(!(userDefaultPath.equals(nowPath))) {
			flag = true;
		}
		
		for(int i=userDefaultPath.length()-mid.length(); i < nowPath.length();i++) {
			displayPath += nowPath.charAt(i);
		}
		
		model.addAttribute("dirList",dirs);
		model.addAttribute("dirListSize",dirs.size());
		model.addAttribute("userData", userData);
		model.addAttribute("userDataSize", userData.size());
		model.addAttribute("nowPath", displayPath);
		model.addAttribute("isDefaultPath", flag);
		model.addAttribute("urlPath", "/");
	}
	
	/*
	 * uploadUserData
	 * 여러개 또는 단일 파일을 해당 경로에 저장 및 DB에 레코드를 추가함
	 * param - mp_req : MultiPart를 지원하는 HttpServletRequest 변수
	 * */
	public int uploadUserData(MultipartHttpServletRequest mp_req) {
		String mid = (String)mp_req.getSession().getAttribute("sessionMid");
		String path = (String)mp_req.getSession().getAttribute("nowPath");
		int insertResult = 0;
		List<MultipartFile> fileList = mp_req.getFiles("file");
		Map<String, Object> uploadMap = new HashMap<String, Object>(); //DB foreach Insert용 HashMap
		
		if(fileList.size() == 0) {
			System.out.println("upload에 null 들어옴");
			return 0;
		}
		
		for(MultipartFile mf :fileList) {
			CloudServiceDTO csDTO = new CloudServiceDTO();
			csDTO.setUt_file_name(mf.getOriginalFilename());
			csDTO.setUt_file_path(path);
			csDTO.setUt_file_size((int) mf.getSize());
			
			uploadMap.put("mid", mid);
			uploadMap.put("csDTO",csDTO);
			
			try {
				File file = new File(path+"/"+mf.getOriginalFilename());
				if(file.exists()) {
					System.out.println("이미 파일이 존재함");
					return 0;
				}else {
					mf.transferTo(new File(path+"/"+mf.getOriginalFilename()));
				}
				insertResult = sqlSession.getMapper(CloudServiceMapper.class).uploadUserData(uploadMap);
				if(insertResult < 0) {
					break;
				}
				uploadMap.clear();
			} catch (Exception e) {
				System.out.println("파일 생성 실패!");
				System.out.println(e.getMessage());
			}
		}
		return insertResult;
	}
	
	/*
	 * deleteUserData
	 * 단일 또는 복수의 file이나 dir을 삭제하는 함수
	 * deleteAllFiles()을 통해 dir삭제를 처리함
	 */
	public void deleteUserData(String mid, String fileNames, String path) {
		String[] items = fileNames.split(",");
		System.out.println(path);
		HashMap<String, Object> param = new HashMap<String, Object>();
		File file = null;
		
		for(int i=0 ; i<items.length ; i++) {
			file = new File(path+File.separator+items[i]);
			if(file.isDirectory()) {	//dir이라면
				deleteAllFiles(mid, file);
			}else {
				if(file.exists()) {
					if(file.delete()) {
						param.put("mid", mid);
						param.put("fileName",items[i]);
						param.put("path", path);
						int sqlResult = sqlSession.getMapper(CloudServiceMapper.class).deleteUserData(param);
						
						if(sqlResult > 0) {
							System.out.println(items[i]+" 파일 삭제 및 DB 레코드 삭제 성공");
						}
					}else {
						System.out.println("파일 삭제 실패!");
					}
				}else {
					System.out.println("파일이 존재하지 않습니다.");
				}
			}
		}
	}
	
	/*
	 * deleteAllFiles
	 * 디렉토리의 내용을 전부 삭제하는 재귀용 함수
	 */
	public void deleteAllFiles(String mid, File file) {
  	    File[] deleteList = file.listFiles();
  	    
  	    if(deleteList.length > 0) {
  	    	for(int i = 0; i < deleteList.length; i++) {
  	    		if(deleteList[i].isFile()) {
  	    			deleteUserData(mid, deleteList[i].getName(), file.getPath());
  	    		}else {
  	    			deleteAllFiles(mid, deleteList[i]);	//재귀함수로 다시 dir 탐색
  	    		}
  	    		deleteList[i].delete();
  	    	}
  	    	file.delete();
  	    }
  	    if(deleteList.length == 0 && file.isDirectory()) {
  	    	System.out.println("dir : "+file.getName()+"삭제");
  	    	file.delete();
  	    }
	}
	
	/*
	 * downloadUserData
	 * 단일 또는 복수개의 파일을 다운로드하는 함수
	 * 참고 : 같은 패키지/CloudServiceDownload.java
	 */
	public void downloadUserData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mid = (String)request.getSession().getAttribute("sessionMid");
		String path = (String)request.getSession().getAttribute("nowPath");
		String[] fileNames = request.getParameter("file_names").split(",");
		byte[] fileByte = null;
		File file = null;
		String creatfileName = "";
		
		if(fileNames.length > 1) {	//다중 선택 다운로드
			file = new File(csDownload.filesToZip(fileNames, path, mid));	//압축 후
			creatfileName = file.getName();	// response에 넘길  파일 이름 설정
			fileByte = FileUtils.readFileToByteArray(file); //서버에 파일 생성
			
			if(file.exists()) {
				System.out.println("임시 zip파일 삭제함? : "+file.delete());
			}
		}else {	//단일 선택
			file = new File(path +File.separator+ fileNames[0]);
			
			if(file.isDirectory()) {	//디렉토리라면
				file = new File(csDownload.dirToZip(fileNames[0], path, mid));
				
				fileByte = FileUtils.readFileToByteArray(file);
				if(file.exists()) {
					System.out.println("임시 zip파일 삭제함? : "+file.delete());
				}
			}else {
				fileByte = FileUtils.readFileToByteArray(file);
			}
			creatfileName = file.getName();
		}
		
		
		System.out.println("다운로드 ↓↓↓");
		System.out.println(path+"\\"+creatfileName);
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(creatfileName,"UTF-8") + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);
		
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	/*
	 * mkdir
	 * 서버에 dir을 생성함
	 */
	public void mkdir(String path, String trigger, String dirName) {
	      File file = new File(path+"/"+dirName+"/");
	      
	      if(file.exists()) {
	    	  System.out.println("이미 존재하는 DIR이름");
	      }else {
	    	  if(trigger.equals("mkdir")) {
	    		  System.out.println("임시 : mkdir successed ?? "+file.mkdirs());
	    	  }else {
	    		  System.out.println("DIR 생성 실패!!");
	    	  }
	      }
	}
	
	/*
	 * moveDir
	 * 디렉토리 이동관련 기능
	 */
	public void moveDir(HttpServletRequest request, String dir_depth, int trigger) {
		String nowPath = (String)request.getSession().getAttribute("nowPath");
		if(trigger == 1) {
			//1 : 해당 폴더로 이동
			request.getSession().setAttribute("nowPath", nowPath+"\\"+dir_depth);
		}else if(trigger == 0) {
			//0 : 상위 폴더로 이동
			String backPath = "";
			for(int i = 0;i<nowPath.lastIndexOf("\\");i++) {
				backPath += nowPath.charAt(i);
			}
			request.getSession().setAttribute("nowPath", backPath);
		}
	}
}
