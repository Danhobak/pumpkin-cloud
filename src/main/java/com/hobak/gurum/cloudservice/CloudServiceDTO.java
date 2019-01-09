package com.hobak.gurum.cloudservice;

import java.sql.Date;


public class CloudServiceDTO {
	private int ut_file_id;
	private String ut_file_name;
	private String ut_file_path;
	private int ut_file_size;
	private Date ut_file_reg_date;
	
	
	public int getUt_file_id() {
		return ut_file_id;
	}
	public void setUt_file_id(int ut_file_id) {
		this.ut_file_id = ut_file_id;
	}
	public String getUt_file_name() {
		return ut_file_name;
	}
	public void setUt_file_name(String ut_file_name) {
		this.ut_file_name = ut_file_name;
	}
	public String getUt_file_path() {
		return ut_file_path;
	}
	public void setUt_file_path(String ut_file_path) {
		this.ut_file_path = ut_file_path;
	}
	public Date getUt_file_reg_date() {
		return ut_file_reg_date;
	}
	public void setUt_file_reg_date(Date ut_file_reg_date) {
		this.ut_file_reg_date = ut_file_reg_date;
	}
	public int getUt_file_size() {
		return ut_file_size;
	}
	public void setUt_file_size(int ut_file_size) {
		this.ut_file_size = ut_file_size;
	}
}


/*이름               널?       유형            
---------------- -------- ------------- 
UT_FILE_ID       NOT NULL NUMBER        
UT_FILENAME               VARCHAR2(100) 
UT_FILE_PATH              VARCHAR2(500) 
UT_FILESIZE               NUMBER        
UT_FILE_REG_DATE          DATE    */      
