package com.hobak.gurum.account;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Entity;

@Entity
public class AccountDTO {
	
	@Id
	@Pattern(regexp = "\\S{4,8}", message = "공백없이 4~8자를 입력하세요.")
	private String mid;
	
	@Column
	@NotNull
	@Pattern(regexp = "\\S{4,12}", message = "패스워드는 4자~12자로 입력해주세요.")
	private String mpassword;
	
	@Column
	@NotNull
	@Pattern(regexp = "\\S{2,6}", message = "이름은 2~6자로 입력해주세요.")
	private String mname;
	
	@Column
	@NotNull
	@Pattern(regexp = "\\d{10,11}", message = "'-'없이 숫자만 입력해주세요.")
	private String mphone;
	
	@Column
	@NotNull 
	@Pattern(regexp="^[_0-9a-zA-Z-]+@[0-9a-zA-Z_-]+\\.[0-9a-zA-Z]+", message="메일형식을 준수하여 작성해주세요.")
	private String memail;
	
	@Column
	private Date mregdate;
	
	@Column
	private String macclock;
	
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMpassword() {
		return mpassword;
	}
	public void setMpassword(String mpassword) {
		this.mpassword = mpassword;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getMphone() {
		return mphone;
	}
	public void setMphone(String mphone) {
		this.mphone = mphone;
	}
	public String getMemail() {
		return memail;
	}
	public void setMemail(String memail) {
		this.memail = memail;
	}
	public Date getMregdate() {
		return mregdate;
	}
	public void setMregdate(Date mregdate) {
		this.mregdate = mregdate;
	}
	public String getMacclock() {
		return macclock;
	}
	public void setMacclock(String macclock) {
		this.macclock = macclock;
	}
}
