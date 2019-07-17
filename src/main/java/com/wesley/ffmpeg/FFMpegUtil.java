package com.wesley.ffmpeg;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FFMpegUntil
 */
public class FFMpegUtil implements IStringGetter {

	private int runtime = 0;
	private String ffmpegUri;
	private String originFileUri;

	private enum FFMpegUtilStatus {
		Empty, CheckingFile, GettingRuntime
	};

	private FFMpegUtilStatus status = FFMpegUtilStatus.Empty;

	/**
	 * 
	 * @param ffmpegUri
	 * @param originFileUri
	 */
	public FFMpegUtil(String ffmpegUri, String originFileUri) {
		this.ffmpegUri = ffmpegUri;
		this.originFileUri = originFileUri;
	}

	/**
	 * 
	 * @return
	 */
	public int getRuntime() {
		runtime = 0;
		status = FFMpegUtilStatus.GettingRuntime;
		cmd.clear();
		cmd.add(ffmpegUri);
		cmd.add("-i");
		cmd.add(originFileUri);
		CmdExecuter.exec(cmd, this);
		return runtime;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSupported() {
		isSupported = false;
		status = FFMpegUtilStatus.CheckingFile;
		cmd.clear();
		cmd.add(ffmpegUri);
		cmd.add("-i");
		cmd.add(originFileUri);
		CmdExecuter.exec(cmd, this);
		return isSupported;
	}

	private boolean isSupported;

	/**
	 * 
	 * @param imageSavePath
	 * @param screenSize
	 */
	public void makeScreenCut(String imageSavePath, String screenSize) {
		cmd.clear();
		cmd.add(ffmpegUri);
		cmd.add("-i");
		cmd.add(originFileUri);
		cmd.add("-y");
		cmd.add("-f");
		cmd.add("image2");
		cmd.add("-ss");
		cmd.add("2");
		cmd.add("-t");
		cmd.add("0.001");
		cmd.add("-s");
		cmd.add(screenSize);
		cmd.add(imageSavePath);
		CmdExecuter.exec(cmd, this);
	}

	private List<String> cmd = new ArrayList<String>();

	@Override
	public void dealString(String str) {
		System.out.println(str);

		switch (status) {
		case Empty:
			break;
		case CheckingFile: {
			if (-1 != str.indexOf("Metadata:"))
				this.isSupported = true;
			break;
		}
		case GettingRuntime: {
			Matcher m = Pattern.compile("Duration: //w+://w+://w+").matcher(str);
			while (m.find()) {
				String msg = m.group();
				msg = msg.replace("Duration: ", "");
			}
			break;
		}
		}
	}
}