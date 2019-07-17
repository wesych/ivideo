package com.wesley.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wesley.dao.CommentDao;
import com.wesley.dao.UserDao;
import com.wesley.entity.Comment;
import com.wesley.entity.User;
import com.wesley.service.ICommentService;

@Service("commentService")
@Scope("prototype")
public class CommentServiceImpl implements ICommentService {

	@Autowired
	@Qualifier("commentDao")
	private CommentDao commentDao;

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	@Override
	public void insertComment(Comment comment) {
		commentDao.insertComment(comment);
	}
	
	@Override
	public void deleteComment(int id) {
		commentDao.deleteComment(id);
	}

	@Override
	public List<Comment> findCommentsByVideoId(int video_id) {
		List<Comment> comments = commentDao.findCommentByVideoId(video_id);

		// Inject attribute 'username' and 'avatar' for comment.
		injectAttributesForComments(comments);

		return comments;
	}

	@Override
	public List<Comment> findAllComment() {
		List<Comment> comments = commentDao.findAllComment();

		// Inject attribute 'username' and 'avatar' for comment.
		injectAttributesForComments(comments);

		return comments;
	}

	private void injectAttributesForComments(List<Comment> commentList) {
		for (Comment comment : commentList) {
			User user = userDao.findUserById(comment.getUserId());
			comment.setUserName(user.getUserName());
			comment.setAvatar(user.getAvatar());
		}
	}

	

}
