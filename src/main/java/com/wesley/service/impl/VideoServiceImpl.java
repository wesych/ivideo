package com.wesley.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wesley.dao.UserDao;
import com.wesley.dao.VideoDao;
import com.wesley.entity.User;
import com.wesley.entity.Video;
import com.wesley.service.IVideoService;
import com.wesley.util.RandomNumeral;

@Service("videoService")
@Scope("prototype")
public class VideoServiceImpl implements IVideoService {

	@Autowired
	@Qualifier("videoDao")
	private VideoDao videoDao;

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	@Override
	public void insertVideo(Video video) {
		// TODO Auto-generated method stub
		videoDao.insertVideo(video);
	}

	@Override
	public void deleteVideo(int id) {
		// TODO Auto-generated method stub
		videoDao.deleteVideo(id);
	}

	@Override
	public void updateVideo(Video video) {
		// TODO Auto-generated method stub
		videoDao.updateVideo(video);
	}

	@Override
	public Video findVideoById(int id) {
		Video video = videoDao.findVideoById(id);

		// Inject 'userName' for video
		User user = userDao.findUserById(video.getUserId());
		video.setUserName(user.getUserName());

		return video;
	}

	@Override
	public List<Video> findAllVideo() {
		List<Video> videoList = videoDao.findAllVideo();

		// Inject 'userName' for video
		for (Video video : videoList) {
			User user = userDao.findUserById(video.getUserId());
			video.setUserName(user.getUserName());
		}

		return videoList;
	}

	@Override
	public List<Video> sortByPlayCount(List<Video> vList) {
		int max = 0;
		Video temp = new Video();
		for (int i = 0; i < vList.size(); i++) {
			max = i;
			for (int j = i + 1; j < vList.size(); j++) {
				if (vList.get(max).getPlayCount() < vList.get(j).getPlayCount())
					max = j;
			}
			// 位置交换
			if (max != i) {
				temp = vList.get(max);
				vList.set(max, vList.get(i));
				vList.set(i, temp);
			}
		}
		return vList;
	}

	@Override
	public List<Video> sortByCommentCount(List<Video> vList) {
		int max = 0;
		Video temp = new Video();
		for (int i = 0; i < vList.size(); i++) {
			max = i;
			for (int j = i + 1; j < vList.size(); j++) {
				if (vList.get(max).getCommentCount() < vList.get(j).getCommentCount())
					max = j;
			}
			// 位置交换
			if (max != i) {
				temp = vList.get(max);
				vList.set(max, vList.get(i));
				vList.set(i, temp);
			}
		}
		return vList;
	}

	@Override
	public List<Video> getSpecificLabelVideo(List<Video> vList, String label) {
		List<Video> labelList = new ArrayList<Video>();
		for (Video v : vList) {
			if (v.getLabel().equals(label)) {
				labelList.add(v);
			}
		}
		return labelList;
	}

	@Override
	public List<Video> getSpecificUserVideos(List<Video> vList, int userId) {
		List<Video> userVideoList = new ArrayList<Video>();
		for (Video v : vList) {
			if (v.getUserId() == userId)
				userVideoList.add(v);
		}
		return userVideoList;
	}

	@Override
	public List<Video> getRandomVideo(List<Video> vList, int num) {
		List<Video> resultList = new ArrayList<Video>();
		int vlID[] = RandomNumeral.getNumeral(vList.size(), num);
		if (vlID != null) {
			for (int i = 0; i < vlID.length; i++) {
				resultList.add(vList.get(vlID[i] - 1));
			}
		}
		return resultList;
	}
	
	@Override
	public List<Video> getTopNumVideos(List<Video> vList, int num) {
		List<Video> playRecordList = sortByPlayCount(vList);
		List<Video> topList = new ArrayList<Video>(num);
		if (playRecordList.size() >= num) {
			for (int i = 0; i < num; i++) {
				topList.add(playRecordList.get(i));
			}
		}
		return topList;
	}

	@Override
	public List<Video> getTop4VideosByPlayCount() {
		List<Video> videoList = findAllVideo();
		List<Video> playRecordList = sortByPlayCount(videoList);
		List<Video> top4List = new ArrayList<Video>(4);
		if (playRecordList.size() >= 4) {
			for (int i = 0; i < 4; i++) {
				top4List.add(playRecordList.get(i));
			}
		}
		return top4List;
	}
	
	@Override
	public List<Video> getPlayRecordList(String record) {
		List<Video> videos = new ArrayList<Video>();
		if (record != null) {
			String[] ids = record.trim().split(",");
			for (int i = 0; i < ids.length; i++) {
				videos.add(findVideoById(Integer.parseInt(ids[i])));
			}
		}
		return videos;
	}

	@Override
	public List<Video> getCollectionList(String collection) {
		List<Video> videos = new ArrayList<Video>();
		if (collection != null) {
			String[] ids = collection.trim().split(",");
			for (int i = 0; i < ids.length; i++) {
				videos.add(findVideoById(Integer.parseInt(ids[i])));
			}
		}
		return videos;
	}

	@Override
	public List<Video> getSearchList(String key) {
		String input = disPreprocess(key);
		List<Video> videos = findAllVideo();
		List<Video> searchList = new ArrayList<Video>();
		for (Video video : videos) {
			String desc = disPreprocess(video.getDescription());
			String title = disPreprocess(video.getTitle());
			if (desc.contains(input) || title.contains(input)) {
				searchList.add(video);
			}
		}
		return searchList;
	}

	/**
	 * Remove noise char.
	 */
	private String disPreprocess(String discription) {
		StringBuilder builder = new StringBuilder();
		discription = discription.replaceAll(" ", "");
		char[] dis = discription.toCharArray();
		for (char c : dis) {
			if (isGBK(c) || Character.isDigit(c) || (c <= 'Z' && c >= 'A') || (c <= 'z' && c >= 'a')) {
				builder.append(String.valueOf(c));
			}
		}
		return builder.toString();
	}

	/**
	 * Check whether string be Chinese or not.
	 */
	private boolean isGBK(char ch) {
		boolean isGB2312 = false;
		byte[] bytes = ("" + ch).getBytes();
		if (bytes.length == 2) {
			int[] ints = new int[2];
			ints[0] = bytes[0] & 0xff;
			ints[1] = bytes[1] & 0xff;
			if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
				isGB2312 = true;
			}
		}
		return isGB2312;
	}

}
