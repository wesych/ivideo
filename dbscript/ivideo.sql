/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : ivideo

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-09-13 00:42:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `video_id` int(11) NOT NULL,
  `content` varchar(255) NOT NULL,
  `comment_time` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '1', '2', '泪点比较低，不过那种真挚的感情演绎真的很动人，加油，阿妹！', '2012-04-25 00:00:00', '0');
INSERT INTO `comment` VALUES ('2', '2', '1', '演唱会很棒，燕姿的首次红磡演唱会完美，最后的天黑黑音乐也超赞！', '2014-01-12 00:00:00', '0');
INSERT INTO `comment` VALUES ('3', '4', '1', '超喜欢孙燕姿的，那场演唱会超赞啊，一直有保存video，常有回忆看一下的。', '2013-03-01 00:00:00', '0');
INSERT INTO `comment` VALUES ('4', '1', '3', '高中时代最喜欢的mv了，那是一种期待爱情的美好！', '2013-02-17 00:00:00', '0');
INSERT INTO `comment` VALUES ('5', '2', '2', '一直有在看这个现场版的，很动听，很感人！', '2013-06-03 21:55:23', '0');
INSERT INTO `comment` VALUES ('6', '1', '1', '回味初中时代最喜欢的歌曲真的很温馨，音乐好棒，加油！', '2013-06-04 17:09:54', '0');
INSERT INTO `comment` VALUES ('7', '5', '1', '听了好多次！', '2013-06-05 10:41:07', '0');
INSERT INTO `comment` VALUES ('8', '4', '8', '真的是碉堡了！', '2013-06-07 21:27:58', '0');
INSERT INTO `comment` VALUES ('9', '1', '1', '再一次表示很喜欢！', '2013-06-19 20:38:12', '0');
INSERT INTO `comment` VALUES ('10', '6', '6', '很有味道，蛮伤感的。', '2017-07-27 03:03:55', '0');
INSERT INTO `comment` VALUES ('11', '6', '4', 'really love song', '2017-07-28 02:41:59', '0');
INSERT INTO `comment` VALUES ('12', '7', '18', 'a really realing song', '2017-09-05 01:22:15', '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `pwd` varchar(1024) NOT NULL,
  `gender` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `join_time` datetime DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `play_record` varchar(255) DEFAULT NULL,
  `video_collection` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'D大调的黄昏', '8f02cce1f69d08c858404cc4cc57cad3f08d51e7325b34f2496397cdee493c5c', '1', 'zhangwei4815@yeah.net', '2013-03-16 00:00:00', '1.jpg', '1', '1,2,3,4,5', '1,4,5');
INSERT INTO `user` VALUES ('2', '土豆吃土豆', 'b8eb12162b441ab81b9228d35e303f0efea9bc2620367bd4ec88b71d29841319', '0', 'tudou@163.com', '2013-05-01 00:00:00', '2.jpg', '0', '1,2', '2');
INSERT INTO `user` VALUES ('3', '老大一条狗', 'ea07e2e3ccaf2f51f29ff862bf8c1ed48485e431715c4c5040403a0dc6ecf37a', '1', 'bigdog@yahoo.com', '2013-04-11 00:00:00', '3.jpg', '0', '1,3,5', '3,5');
INSERT INTO `user` VALUES ('4', '风中熏衣香', '45d31d50c6fd18226ab6548d7b87a0d571900e70f4ada2d7b66a0623d66f1f2a', '0', 'lavender@live.com', '2013-02-17 00:00:00', '4.jpg', '0', '1,3,8,16,7', '3,16');
INSERT INTO `user` VALUES ('5', '雪色倾城', '2932cf0895b8bfeb0797bdd2361f07976871faf7f823e7e8546389598db64b12', '0', 'snowmorning@yahoo.com', '2013-04-25 00:00:00', 'Default.jpg', '0', '1,4', '4');
INSERT INTO `user` VALUES ('6', 'DTwilight', '8f02cce1f69d08c858404cc4cc57cad3f08d51e7325b34f2496397cdee493c5c', '1', 'zhangwei4815@sina.com', '2013-05-28 21:25:05', '6.jpg', '0', '1,2', '1,2');
INSERT INTO `user` VALUES ('7', 'mr.Cheung', '8f02cce1f69d08c858404cc4cc57cad3f08d51e7325b34f2496397cdee493c5c', '1', 'cheung@163.com', '2017-07-30 17:31:49', '7.jpg', '0', '17,5,18,2', '6,4,5,18,17,1');

-- ----------------------------
-- Table structure for video
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `video_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `play_count` int(11) DEFAULT NULL,
  `comment_count` int(11) DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`video_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO `video` VALUES ('1', '天黑黑-孙燕姿', 'music', '孙燕姿2005香港红磡体育馆演唱会', '2013-05-18 00:00:00', '1.mp4', '2', '40', '5', '0');
INSERT INTO `video` VALUES ('2', '如果你也听说-阿妹', 'music', '阿妹现场版飙泪-如果你也听说', '2013-04-21 00:00:00', '2.mp4', '1', '13', '2', '0');
INSERT INTO `video` VALUES ('3', '暧昧-杨丞琳', 'music', '暧昧mv', '2013-05-01 00:00:00', '3.mp4', '1', '15', '1', '0');
INSERT INTO `video` VALUES ('4', 'Because of  you - Kelly Clarkson', 'music', '凯莉·克莱森的mv版because of you', '2013-04-11 00:00:00', '4.mp4', '4', '19', '1', '0');
INSERT INTO `video` VALUES ('5', 'when you\'re gone', 'music', '艾薇儿现场钢琴唯美版when you\'re gone', '2013-03-16 00:00:00', '5.mp4', '3', '24', '0', '0');
INSERT INTO `video` VALUES ('6', '习惯两个人-言承旭', 'music', '言承旭最新作品mv-习惯两个人', '2013-02-25 00:00:00', '6.mp4', '1', '22', '1', '0');
INSERT INTO `video` VALUES ('7', '一路上有你-张学友', 'music', '张学友台北演唱会-一路上有你', '2013-03-11 00:00:00', '7.mp4', '3', '4', '0', '0');
INSERT INTO `video` VALUES ('8', '胥渡吧恶搞2012', 'funny', '胥渡吧年度巨献，恶搞配音：2012，你怎么看？', '2013-06-07 20:52:25', '8.mp4', '4', '3', '1', '0');
INSERT INTO `video` VALUES ('9', '大学考试周', 'funny', '恶搞【大学考试周】，必须要顶！！！快笑喷我了，不过看完之后又些许伤感。', '2013-06-07 22:37:05', '9.mp4', '4', '7', '0', '0');
INSERT INTO `video` VALUES ('10', '现任是极品', 'funny', '胥渡吧：我的现任是极品！！！', '2013-06-07 22:50:16', '10.mp4', '5', '13', '0', '0');
INSERT INTO `video` VALUES ('11', '周星驰恶搞杜甫', 'funny', '【恶搞配音】周星驰恶搞杜甫很忙 扮屌丝潜入华府卖毒胶囊', '2013-06-07 22:54:31', '11.mp4', '3', '9', '0', '0');
INSERT INTO `video` VALUES ('12', '娱乐无极限', 'amuse', '今日主题：谢霆锋首当评委累过拍打戏[娱乐无极限]', '2013-06-07 23:01:04', '12.mp4', '6', '3', '0', '0');
INSERT INTO `video` VALUES ('13', '陆敏雪 暗香', 'amuse', '陆敏雪 《暗香》 中国梦之声 130602 标清版', '2013-06-07 23:02:37', '13.mp4', '6', '5', '0', '0');
INSERT INTO `video` VALUES ('14', '我想我没那么坚强', 'amuse', '中国梦之声 徐云霄《我想我没那么坚强》 高清', '2013-06-07 23:03:39', '14.mp4', '1', '6', '0', '0');
INSERT INTO `video` VALUES ('15', '新娱乐在线', 'amuse', '中国梦之声：冷碗碗前后大反转 [新娱乐在线]', '2013-06-07 23:04:57', '15.mp4', '5', '2', '0', '0');
INSERT INTO `video` VALUES ('16', '周立波骂人', 'music', '周立波怒斥杜海涛无脑：“小台来的就这样”', '2013-06-09 21:49:26', '16.mp4', '5', '9', '0', '0');
INSERT INTO `video` VALUES ('17', 'Utada Hikaru - First Love', 'music', '宇多田光 Utada Hikaru - First Love （中日字幕）_超清', '2017-08-08 22:49:27', '17.mp4', '7', '37', '0', '0');
INSERT INTO `video` VALUES ('18', 'It Is Well - Kristene DiMarco & Bethel Music ', 'music', 'It Is Well - Kristene DiMarco & Bethel Music - You Make Me Brave_超清.mp4', '2017-08-21 23:09:07', '18.mp4', '7', '51', '1', '0');
SET FOREIGN_KEY_CHECKS=1;
