package com.hobak.gurum.cloudservice;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

@Service
public class CloudServiceDownload {
	
	private ZipFile zipFile = null;
	private ZipParameters parameters = null;
	
	public CloudServiceDownload() {
		parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
	}
	
	/*
	 * filesToZip
	 * 다중 파일 및 디렉토리 압축 기능 함수
	 */
	public String filesToZip(String[] fileNames, String path, String mid) {
		ArrayList<String> sourceFileList = new ArrayList<String>(Arrays.asList(fileNames));
		String zipFileName = path+"\\"+mid+"_"+System.currentTimeMillis()+".zip";
		try {
			zipFile = new ZipFile(zipFileName);
			for(int i=0 ; i < sourceFileList.size() ; i++ ) {
				File file = new File(path+File.separator+sourceFileList.get(i));
				if(file.isDirectory()) {
					zipFile.addFolder(file, parameters);
				}else {
					zipFile.addFile(file, parameters);
				}
			}
			return zipFile.getFile().getPath();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/*
	 * dirToZip
	 * 단일 디렉토리 압축 기능 함수
	 */
	public String dirToZip(String fileName, String path, String mid) {
		String zipFileName = path+"\\"+mid+"_"+System.currentTimeMillis()+".zip";
		
		try {
			File file = new File(path+File.separator+fileName); 
			zipFile = new ZipFile(zipFileName);
			zipFile.addFolder(file, parameters);
			return zipFile.getFile().getPath();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
