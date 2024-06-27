package com.yoon.finalexam.movie.dto;

import org.springframework.web.multipart.MultipartFile;

public class MovieForm {
	
	private Long id;
	private String groupName;	
	private String korName;
	private String engName;
	private String content;
	private MultipartFile image;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getKorName() {
		return korName;
	}
	public void setKorName(String korName) {
		this.korName = korName;
	}
	public String getEngName() {
		return engName;
	}
	public void setEngName(String engName) {
		this.engName = engName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	
	public MovieForm(Long id, String groupName, String korName, String engName, String content, MultipartFile image) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.korName = korName;
		this.engName = engName;
		this.content = content;
		this.image = image;
	}
	
	public MovieForm() {
		super();
	}
	
	@Override
	public String toString() {
		return "MovieForm [id=" + id + ", groupName=" + groupName + ", korName=" + korName + ", engName=" + engName
				+ ", content=" + content + ", image=" + image + "]";
	}
}
