package com.wesley.ffmpeg;

import java.io.File;

public class Capture {

	public static boolean capture(String vpath, String apath, String ffmpegPath) {

		FFMpegUtil ffmpeg = new FFMpegUtil(ffmpegPath, vpath);
		ffmpeg.makeScreenCut(apath, "134x83");

		File afile = new File(apath);
		if (afile.exists()) {
			return true;
		} else {
			return false;
		}

	}

}
