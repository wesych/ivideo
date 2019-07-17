package com.wesley.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2541729298754845061L;
	
	// Fields
	private Integer userId;
	private String userName;
	private String pwd;
	private Integer gender;
	private String email;
	private Timestamp joinTime;
	private String avatar;
	private Integer grade;
	private String playRecord;
	private String videoCollection;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(Integer userId, String userName, String pwd) {
		this.userId = userId;
		this.userName = userName;
		this.pwd = pwd;
	}

	/** full constructor */
	public User(Integer userId, String userName, String pwd, Integer gender, String email, Timestamp joinTime,
			String avatar, Integer grade, String playRecord, String videoCollection) {
		this.userId = userId;
		this.userName = userName;
		this.pwd = pwd;
		this.gender = gender;
		this.email = email;
		this.joinTime = joinTime;
		this.avatar = avatar;
		this.grade = grade;
		this.playRecord = playRecord;
		this.videoCollection = videoCollection;
	}

	// Getters and setters for fields
	
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getGender() {
		return this.gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getJoinTime() {
		return this.joinTime;
	}

	public void setJoinTime(Timestamp joinTime) {
		this.joinTime = joinTime;
	}

	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getGrade() {
		return this.grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getPlayRecord() {
		return this.playRecord;
	}

	public void setPlayRecord(String playRecord) {
		this.playRecord = playRecord;
	}

	public String getVideoCollection() {
		return this.videoCollection;
	}

	public void setVideoCollection(String videoCollection) {
		this.videoCollection = videoCollection;
	}
	
	@Override
	public String toString() {
		return "[User:" + userName + ", email:" + email + ", joinTime:" + joinTime + "]";
	}

}
