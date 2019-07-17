package com.wesley.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.dao.DataAccessException;

import com.wesley.entity.Video;

@MapperScan
public interface VideoDao {
	
	/**
	 * Insert a video.
	 * @param video
	 */
	void insertVideo(Video video) throws DataAccessException;
	
	/**
	 * Delete a video.
	 * @param id
	 */
	void deleteVideo(int id) throws DataAccessException;
	
	/**
	 * Update info of a video.
	 * @param video
	 */
	void updateVideo(Video video) throws DataAccessException;
	
	/**
	 * Find a video by id.
	 * @param id
	 * @return
	 */
	Video findVideoById(int id) throws DataAccessException;
	
	/**
	 * Find all videos.
	 * @return
	 */
	List<Video> findAllVideo() throws DataAccessException;
}
