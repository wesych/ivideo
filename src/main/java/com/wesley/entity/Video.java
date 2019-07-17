package com.wesley.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Video implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8847966842873060012L;
	
	// Fields
	private Integer videoId;
	private String title;
	private String label;
	private String description;
	private Timestamp uploadTime;
	private String filename;
	private Integer userId;
	private Integer playCount;
	private Integer commentCount;
	private Integer isDelete;
	
	//additional field
	private String userName;

	// Constructors

	/** default constructor */
	public Video() {
	}

	/** minimal constructor */
	public Video(Integer videoId) {
		this.videoId = videoId;
	}

	/** full constructor */
	public Video(Integer videoId, String title, String label, String description, Timestamp uploadTime, String filename,
			Integer userId, Integer playCount, Integer commentCount, Integer isdelete) {
		this.videoId = videoId;
		this.title = title;
		this.label = label;
		this.description = description;
		this.uploadTime = uploadTime;
		this.filename = filename;
		this.userId = userId;
		this.playCount = playCount;
		this.commentCount = commentCount;
		this.isDelete = isdelete;
	}

	// Getters and setters for fields
	
	public Integer getVideoId() {
		return this.videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getUploadTime() {
		return this.uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPlayCount() {
		return this.playCount;
	}

	public void setPlayCount(Integer playCount) {
		this.playCount = playCount;
	}

	public Integer getCommentCount() {
		return this.commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Integer isdelete) {
		this.isDelete = isdelete;
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "[Title:" + title + ", uploadTime:" + uploadTime + ", user:" + userName + ", description:" + description + "]";
	}
}
