package com.wesley.service;

import java.util.List;

import com.wesley.entity.User;

public interface IUserService {
	
	/**
	 * Insert a user.
	 * @param user
	 */
	void insert(User user);
	
	/**
	 * Delete a user.
	 * @param id
	 */
	void delete(int id);
	
	/**
	 * Update information of a user.
	 * @param user
	 */
	void update(User user);
	
	/**
	 * Find a user by user_id.
	 * @param id
	 * @return
	 */
	User findUserById(int id);
	
	/**
	 * Find a user by user_name which is unique in database table.
	 * @param name
	 * @return
	 */
	User findUserByName(String name);

	/**
	 * Find all users.
	 * @return
	 */
	List<User> findAllUser();
	
	/**
	 * Get some users in random.
	 * @param uList
	 * @param num
	 * @return
	 */
	List<User> getRandomUsers(List<User> uList, int num);
	
	/**
	 * Judge whether user exists.
	 * @param username
	 * @param password
	 * @return
	 */
	boolean isUserExist(String username, String password);
	
	/**
	 * Judge whether username exists.
	 * @param username
	 * @return
	 */
	boolean isUsernameExist(String username);
	
	/**
	 * Judge whether email exists.
	 * @param email
	 * @return
	 */
	boolean isEmailExist(String email);
	
	/**
	 * Judge whether user has collected this video.
	 * @param user
	 * @param video_id
	 * @return
	 */
	boolean isCollected(User user, String video_id);
	
	/**
	 * Add a video to video_collection.
	 */
	void addVideoCollection(User user, String video_id);
	
	/**
	 * Remove a video form user's collection.
	 * @param video_id
	 */
	void removeVideoFromCollection(User user, String video_id);
	
	/**
	 * Add play record of a user.
	 * @param user
	 * @param video_id
	 */
	void addPlayRecord(User user, String video_id);
	
}
