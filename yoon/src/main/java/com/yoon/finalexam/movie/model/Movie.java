package com.yoon.finalexam.movie.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MOVIE")
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "GROUPNAME")
	private String groupName;	

	@Column(name = "KORNAME")
	private String korName;
	
	@Column(name = "ENGNAME")
	private String engName;
	
	@Column(name = "CONTENT")
	private String content;
	
	@Column(name = "IMAGE")
	private String image;

	// 기본 생성자
	public Movie() {}

	// 모든 필드를 포함하는 생성자 (id를 제외한 생성자)
	public Movie(String groupName, String korName, String engName, String content, String image) {
		this.groupName = groupName;
		this.korName = korName;
		this.engName = engName;
		this.content = content;
		this.image = image;
	}

	// getters and setters

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", groupName=" + groupName + ", korName=" + korName + ", engName=" + engName
				+ ", content=" + content + ", image=" + image + "]";
	}
}
