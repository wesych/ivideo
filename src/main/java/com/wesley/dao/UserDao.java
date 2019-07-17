package com.wesley.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.dao.DataAccessException;

import com.wesley.entity.User;

@MapperScan
public interface UserDao {
	
	//The name of method should be same with those in mapper.xml
	
	/**
	 * Insert.
	 * @param user
	 */
	void insertUser(User user) throws DataAccessException;
	
	/**
	 * Delete.
	 * @param id
	 */
	void deleteUser(int id) throws DataAccessException;
	
	/**
	 * Update.
	 * @param user
	 */
	void updateUser(User user) throws DataAccessException;
	
	/**
	 * Find user by id.
	 * @param id
	 * @return
	 */
	User findUserById(int id) throws DataAccessException;
	
	/**
	 * Find all user.
	 * @return
	 */
	List<User> findAllUser() throws DataAccessException;

}
