package com.hobak.gurum.cloudservice;




import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class CloudServiceController {
	
	@Inject
	private CloudServiceDAO cloudServiceDAO;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String goToLogin() {
				
		return "account/login";
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String mainGet(Model model, HttpServletRequest request) {
		String mid = (String)request.getSession().getAttribute("sessionMid");
		String userPath = (String)request.getSession().getAttribute("nowPath");
		
		System.out.println("mainGet");
		System.out.println("userPath : "+userPath);
		cloudServiceDAO.getUserData(request, model, mid, userPath);
		
		return "main/main";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String userFileUpload(MultipartHttpServletRequest mp_req) {
		System.out.println("컨트롤러 업로드 진입");
		int insertResult = 0;
		
		try {
			insertResult = cloudServiceDAO.uploadUserData(mp_req);
			if(insertResult > 0) {
				System.out.println("File Upload 성공!");
			}else {
				System.out.println("DB에 File 정보 Upload 실패!");
			}
			return "redirect:/main";
		}catch (Exception e) {
			System.out.println("File Upload 실패!");
			System.out.println(e.getMessage());
		}
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String userFileDelete(HttpServletRequest request, @RequestParam("file_name") String filename) {
		String path = (String)request.getSession().getAttribute("nowPath");
		String mid = (String)request.getSession().getAttribute("sessionMid");
		try {
			cloudServiceDAO.deleteUserData(mid, filename, path);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void userFileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		cloudServiceDAO.downloadUserData(request, response);
	}
	
	@RequestMapping(value="/mkDir", method = RequestMethod.POST)
	public String modifyUserDir(HttpServletRequest request, @RequestParam("trigger") String trigger, @RequestParam("dirName") String dirName) {
		String path = (String)request.getSession().getAttribute("nowPath");
	    
		try {
			cloudServiceDAO.mkdir(path, trigger, dirName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	    return "redirect:/main";
	}
	
	@RequestMapping(value = "/moveDir", method = RequestMethod.POST)
	public String userFileDownload(HttpServletRequest request,
			@RequestParam("dirName") String dir_depth,
			@RequestParam("trigger") int trigger){
		String temp_depth = "";
		
		if(dir_depth != null) {
			temp_depth = dir_depth;
		}
		cloudServiceDAO.moveDir(request, temp_depth, trigger);
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/typeSelect", method = RequestMethod.GET)
	public String userFileSelect(Model model, HttpServletRequest request) {
		String mid = (String)request.getSession().getAttribute("sessionMid");
		String type = request.getParameter("type");
		
		cloudServiceDAO.fileTypeSelector(model, mid, type);
		
		return "main/main";
	}
}
