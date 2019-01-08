package com.hobak.gurum.account;

import java.util.Map;

public interface AccountMapper {
	public int loginCheck(AccountDTO accountDTO);
	
	public void joinMember(AccountDTO accountDTO);
	
	public int joinIdCheck(String mid);
	
	public void createUserTable(Map<String, String> map);
	
	public void dropUserTable(Map<String, String> map);
	
	public int deleteMember(String mid);
}
