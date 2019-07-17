package com.wesley.service;

import java.util.List;

import com.wesley.entity.Video;

public interface IVideoService {
	
	/**
	 * Insert a video.
	 * @param video
	 */
	void insertVideo(Video video);
	
	/**
	 * Delete a video.
	 * @param id
	 */
	void deleteVideo(int id);
	
	/**
	 * Update info of a video.
	 * @param video
	 */
	void updateVideo(Video video);
	
	/**
	 * Find a video by id.
	 * @param id
	 * @return
	 */
	Video findVideoById(int id);

	/**
	 * Find all videos.
	 * @return
	 */
	List<Video> findAllVideo();
	
	/**
	 * Sort videos by play count.
	 * @param vList
	 * @return
	 */
	List<Video> sortByPlayCount(List<Video> vList);
	
	/**
	 * Sort videos by comment count.
	 * @param vList
	 * @return
	 */
	List<Video> sortByCommentCount(List<Video> vList);
	
	/**
	 * Get specific label videos.
	 * @param vList
	 * @param label
	 * @return
	 */
	List<Video> getSpecificLabelVideo(List<Video> vList,String label);
	
	/**
	 * Get specific user videos.
	 * @param vList
	 * @param userId
	 * @return
	 */
	List<Video> getSpecificUserVideos(List<Video> vList,int userId);
	
	/**
	 * Get videos in random.
	 * @param vList
	 * @param num
	 * @return
	 */
	List<Video> getRandomVideo(List<Video> vList, int num);
	
	/**
	 * Get top num videos by play count.
	 * @param vList
	 * @param num
	 * @return
	 */
	List<Video> getTopNumVideos(List<Video> vList, int num);
	
	/**
	 * Get top 4 videos by play count.
	 * @return
	 */
	List<Video> getTop4VideosByPlayCount();
	
	/**
	 * Get personal play record.
	 * @param record
	 * @return
	 */
	List<Video> getPlayRecordList(String record);
	
	/**
	 * Get personal collection videos.
	 * @param collection
	 * @return
	 */
	List<Video> getCollectionList(String collection);
	
	/**
	 * Search videos with key.
	 * @param key
	 * @return
	 */
	List<Video> getSearchList(String key);
	
}
