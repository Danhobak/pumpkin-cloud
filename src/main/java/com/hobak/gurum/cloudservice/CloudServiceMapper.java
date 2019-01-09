package com.hobak.gurum.cloudservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CloudServiceMapper {
	public List<CloudServiceDTO> getUserData(String mid, String path);
	
	public int uploadUserData(Map<String, Object> map);
	
	public int deleteUserData(HashMap<String, Object> map);
	
	public List<CloudServiceDTO> getFileSelect(Map<String, Object> map);
}
	