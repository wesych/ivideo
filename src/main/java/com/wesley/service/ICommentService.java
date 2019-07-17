package com.wesley.service;

import java.util.List;

import com.wesley.entity.Comment;

public interface ICommentService {
	
	/**
	 * Insert a comment.
	 * @param comment
	 */
	void insertComment(Comment comment);
	
	/**
	 * Delete comment.
	 * @param id
	 */
	void deleteComment(int id);
	
	/**
	 * Find all comments that belongs to the specific video.
	 * @param video_id
	 * @return
	 */
	List<Comment> findCommentsByVideoId(int video_id);
	
	/**
	 * Find all comments.
	 * @return
	 */
	List<Comment> findAllComment();
}
