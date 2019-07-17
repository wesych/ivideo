package com.wesley.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6755849609958587698L;
	
	// Fields
	private Integer commentId;
	private int userId;
	private Integer videoId;
	private String content;
	private Timestamp commentTime;
	private Integer isDelete;
	
	// additional fields
	private String userName;
	private String avatar;

	// Constructors
	/** default constructor */
	public Comment() {
	}

	/** minimal constructor */
	public Comment(Integer commentId, String content) {
		this.commentId = commentId;
		this.content = content;
	}

	/** full constructor */
	public Comment(Integer commentId, int userId, Integer videoId, String content, Timestamp commentTime,
			Integer isDelete) {
		this.commentId = commentId;
		this.userId = userId;
		this.videoId = videoId;
		this.content = content;
		this.commentTime = commentTime;
		this.isDelete = isDelete;
	}

	// Getters and setters for fields

	public Integer getCommentId() {
		return this.commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Integer getVideoId() {
		return this.videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getCommentTime() {
		return this.commentTime;
	}

	public void setCommentTime(Timestamp commentTime) {
		this.commentTime = commentTime;
	}

	public Integer getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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

	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "[User:" + userId + ", videoId:" + videoId + ", content:" + content + ", time:" + commentTime + "avatar：" + avatar + "]";
	}
}
