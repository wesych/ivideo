package com.wesley.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.wesley.dao.UserDao;
import com.wesley.entity.User;
import com.wesley.service.IUserService;
import com.wesley.util.Encript;
import com.wesley.util.RandomNumeral;

@Service("userService")
@Scope("prototype")
public class UserServiceImpl implements IUserService {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	@Override
	public void insert(User user) {
		user.setPwd(Encript.SHA256(user.getPwd()));
		userDao.insertUser(user);
	}

	@Override
	public void delete(int id) {
		userDao.deleteUser(id);
	}

	@Override
	public void update(User user) {
		userDao.updateUser(user);
	}

	@Override
	public User findUserById(int id) {
		return userDao.findUserById(id);
	}

	@Override
	public User findUserByName(String name) {
		List<User> users = findAllUser();
		User resultUser = null;
		for (User user : users) {
			if (user.getUserName().equals(name)) {
				resultUser = user;
				break;
			}
		}

		return resultUser;
	}

	@Override
	public List<User> findAllUser() {
		return userDao.findAllUser();
	}

	@Override
	public List<User> getRandomUsers(List<User> uList, int num) {
		List<User> userList = new ArrayList<User>();
		int[] userArr = RandomNumeral.getNumeral(uList.size(), 3);
		if (userArr != null) {
			for (int i = 0; i < userArr.length; i++) {
				userList.add(uList.get(userArr[i] - 1));
			}
		}
		return userList;
	}

	@Override
	public boolean isUserExist(String username, String password) {
		boolean isExist = false;
		String storePwd = Encript.SHA256(password);
		// System.err.println("encrypted pwd:" + storePwd);
		List<User> userList = userDao.findAllUser();
		for (User user : userList) {
			if (user.getUserName().equals(username) && user.getPwd().equals(storePwd)) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	@Override
	public boolean isUsernameExist(String username) {
		boolean isExist = false;
		List<User> userList = userDao.findAllUser();
		for (User user : userList) {
			if (user.getUserName().equals(username)) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	@Override
	public boolean isEmailExist(String email) {
		boolean isExist = false;
		List<User> userList = userDao.findAllUser();
		for (User user : userList) {
			if (user.getEmail().equals(email)) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	@Override
	public boolean isCollected(User user, String video_id) {
		String collection = user.getVideoCollection();
		if (collection == null || collection.length() == 0) {
			return false;
		} else {
			String[] arr = collection.split(",");
			return Arrays.asList(arr).contains(video_id);
		}
	}

	@Override
	public void addVideoCollection(User user, String video_id) {
		String collection = user.getVideoCollection();
		if (collection == null || collection.length() == 0) {
			collection = video_id;
		} else {
			String[] arr = collection.split(",");
			if (!Arrays.asList(arr).contains(video_id)) {
				collection = collection + "," + video_id;
			}
		}
		user.setVideoCollection(collection);
		update(user);
	}

	@Override
	public void removeVideoFromCollection(User user, String video_id) {
		String collection = user.getVideoCollection();
		String[] arr = collection.split(",");
		List<String> colList = Arrays.asList(arr);
		List<String> cur = new ArrayList<String>();
		for (String str : colList) {
			if (!str.equals(video_id)) {
				cur.add(str);
			}
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < cur.size() - 1; i++) {
			builder.append(cur.get(i));
			builder.append(",");
		}
		if (cur.size() > 0) {
			builder.append(cur.get(cur.size() - 1));
		}

		user.setVideoCollection(builder.toString());
		update(user);
	}

	@Override
	public void addPlayRecord(User user, String video_id) {
		String record = user.getPlayRecord();
		if (record == null || record.length() == 0) {
			record = video_id;
			user.setPlayRecord(record);
			update(user);
		} else {
			String[] arr = record.split(",");
			if (!Arrays.asList(arr).contains(video_id)) {
				record = record + "," + video_id;
				user.setPlayRecord(record);
				update(user);
			}
		}
	}

}
