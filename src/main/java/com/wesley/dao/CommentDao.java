package com.wesley.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.dao.DataAccessException;

import com.wesley.entity.Comment;

@MapperScan
public interface CommentDao {

	/**
	 * Insert comment.
	 * @param comment
	 */
	void insertComment(Comment comment) throws DataAccessException;
	
	/**
	 * Delete comment.
	 * @param id
	 */
	void deleteComment(int id) throws DataAccessException;
	
	/**
	 * Find a comment by id.
	 * @param id
	 * @return
	 */
	List<Comment> findCommentByVideoId(int id) throws DataAccessException;
	
	/**
	 * Find all comments.
	 * @return
	 */
	List<Comment> findAllComment() throws DataAccessException;
	
}
