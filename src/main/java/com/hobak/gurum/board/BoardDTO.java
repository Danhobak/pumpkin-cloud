package com.hobak.gurum.board;

public class BoardDTO {
	private String btitle;
	private String bcontent;
	private String bfname;	//filename
	private String mid;		// FK(hg_member)
	
	public String getBtitle() {
		return btitle;
	}
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}
	public String getBcontent() {
		return bcontent;
	}
	public void setBcontent(String bcontent) {
		this.bcontent = bcontent;
	}
	public String getBfname() {
		return bfname;
	}
	public void setBfname(String bfname) {
		this.bfname = bfname;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
}
