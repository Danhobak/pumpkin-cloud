package com.hobak.gurum.account;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class AccountDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	public String loginCheck(HttpServletRequest request, AccountDTO accountDTO) {
		int login_ck = sqlSession.getMapper(AccountMapper.class).loginCheck(accountDTO);
		if(login_ck > 0) {	//select 된게 있다면
			HttpSession session = request.getSession();	// 세션 생성
			String userDefaultPath = request.getSession().getServletContext().getRealPath("resources/cloudService/");
			session.setAttribute("sessionMid", accountDTO.getMid());
			session.setAttribute("userDefaultPath", userDefaultPath+accountDTO.getMid());
			session.setAttribute("nowPath", userDefaultPath+accountDTO.getMid());
			System.out.println("임시 : 로그인 ID["+accountDTO.getMid()+"]");
			return "redirect:/main";
		}else {
			System.out.println("임시 : 로그인 시도 실패");
			return "redirect:/"; 
		}
	}
	
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("sessionMid");
		session.invalidate();
		return "redirect:/";
	}
	
	public String join(AccountDTO accountDTO, HttpServletRequest request, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			System.out.println("임시 : 회원가입 실패 >> 유효성검사 실패");
			return "account/join";
		}else {
			boolean createTable = false;
			boolean createDirec = false;
			try {
				createUserTable(accountDTO.getMid());
				createTable = true;
			}catch (Exception e) {
				System.out.println("임시 : 테이블 생성 실패!!!!!!");
				System.out.println(e.getMessage());
				createTable = false;
			}
			if(createTable) {
				try {
					createUserDirectory(accountDTO.getMid(), request);
					createDirec = true;
				} catch (Exception e) {
					System.out.println("임시 : 디렉토리 생성 실패!!!!!!");
					System.out.println(e.getMessage());
					createDirec = false;
				}
			}
			if(createTable && createDirec) {
				sqlSession.getMapper(AccountMapper.class).joinMember(accountDTO);
				HttpSession session = request.getSession(); //가입 성공시 세션을 하나 만들어주자
				session.setAttribute("sessionMid", accountDTO.getMid());
				System.out.println("임시 : 회원가입 성공 가입 ID["+session.getAttribute("sessionMid")+"]");
				return "redirect:/login";
			}else {
				return "account/join";
			}
		}
	}
	
	public String joinIdCheck(String mid) {
		return String.valueOf(sqlSession.getMapper(AccountMapper.class).joinIdCheck(mid));
	}
	
	//계정 생성시 해당 UT_계정명 으로 테이블 생성 쿼리
	public void createUserTable(String mid) {
		String qry = "CREATE TABLE UT_"+mid+ 
				" (UT_FILE_ID NUMBER, " + 
				"UT_FILE_NAME VARCHAR2(100) NOT NULL, " + 
				"UT_FILE_PATH VARCHAR2(500), " + 
				"UT_FILE_SIZE NUMBER, " + 
				"UT_FILE_REG_DATE DATE, " +
				"CONSTRAINT UT_"+mid+"_CONST PRIMARY KEY(UT_FILE_ID))"; 

		Map<String,String> map = new HashMap<String,String>();
		map.put("create_table_query", qry);
		
		sqlSession.getMapper(AccountMapper.class).createUserTable(map);
	}
	
	public void createUserDirectory(String mid, HttpServletRequest request) {
		String fullPath = request.getSession().getServletContext().getRealPath("resources/cloudService/");
		File file = new File(fullPath+mid);
		boolean result = file.mkdir();
		System.out.println("임시 : ["+mid+"]'s directory created? : "+result);
	}
	
	public String deleteMember(String mid, HttpServletRequest request) {
		try {
			sqlSession.getMapper(AccountMapper.class).deleteMember(mid);
			dropUserTable(mid);
			removeUserDirectory(mid, request);
			System.out.println("임시 : ["+mid+"]의 계정 정보, Table, Directory 삭제 완료");
			logout(request);
			return "account/login";
		} catch (Exception e) {
			System.out.println("계정 삭제 실패!!!!");
			System.out.println(e.getMessage());
			return "redirect:main/main";
		}
	}
	
	public void dropUserTable(String mid) {
		String qry = "DROP TABLE UT_"+mid+" PURGE"; 

		Map<String,String> map = new HashMap<String,String>();
		map.put("drop_table_query", qry);
		
		sqlSession.getMapper(AccountMapper.class).dropUserTable(map);
	}
	
	public void removeUserDirectory(String mid, HttpServletRequest request) {
		String fullPath = request.getSession().getServletContext().getRealPath("resources/cloudService/");
		File file = new File(fullPath+mid);
		boolean result = file.delete();
		System.out.println("임시 : ["+mid+"]'s directory removed? : "+result);
	}
}
