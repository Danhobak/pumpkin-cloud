package com.hobak.gurum.account;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.Session;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller	
public class AccountController {
	
	@Inject
	private AccountDAO accountDAO;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "account/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, AccountDTO accountDTO) {
		return accountDAO.loginCheck(request, accountDTO);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		return accountDAO.logout(request);
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(AccountDTO accountDTO) {
		return "account/join";
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@Valid AccountDTO accountDTO, HttpServletRequest request, BindingResult bindingResult) {
		return accountDAO.join(accountDTO, request, bindingResult);
	}
	
	@RequestMapping(value = "/joinIdCheck", method = RequestMethod.POST)
	public @ResponseBody String joinIdCheck(@RequestParam("id") String mid, HttpServletRequest request) {
		return accountDAO.joinIdCheck(mid);
	}
	
	@RequestMapping(value = "/leaveMember", method = RequestMethod.GET)
	public String leaveMember(HttpServletRequest request){
		String mid = (String) request.getSession().getAttribute("sessionMid");
		return accountDAO.deleteMember(mid, request);
	}
}
