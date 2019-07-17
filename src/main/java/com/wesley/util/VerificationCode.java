package com.wesley.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("verificationCode")
@Scope("prototype")
public class VerificationCode {
	
	private static final Log log = LogFactory.getLog(VerificationCode.class);

	public static final String RANDOMCODEKEY = "VARIFICATIONCODEKEY";
	private Random random = new Random();
	private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private int width = 80;
	private int height = 24;
	private int interferingLineSize = 40;
	private int randomCharsSize = 4;

	/**
	 * Get font.
	 */
	private Font getFont() {
		return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
	}

	/**
	 * Get color in random.
	 */
	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/**
	 * Generate random verification code image.
	 */
	public void generateVerifyCode(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		//no-cache
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // buffered image
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
		g.setColor(getRandColor(110, 133));
		
		// draw interfering lines.
		for (int i = 0; i <= interferingLineSize; i++) {
			drawLine(g);
		}
		
		// draw char
		String codeString = "";
		for (int i = 1; i <= randomCharsSize; i++) {
			codeString = drawString(g, codeString, i);
		}
		log.info("Current Verification Code String :" + codeString);

		// save code string
		session.removeAttribute(RANDOMCODEKEY);
		session.setAttribute(RANDOMCODEKEY, codeString);

		g.dispose();
		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Draw string.
	 */
	private String drawString(Graphics g, String randomString, int i) {
		g.setFont(getFont());
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
		String rand = String.valueOf(getRandomString(random.nextInt(randString.length())));
		randomString += rand;
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(rand, 13 * i, 16);
		return randomString;
	}

	/*
	 * Draw lines.
	 */
	private void drawLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(13);
		int yl = random.nextInt(15);
		g.drawLine(x, y, x + xl, y + yl);
	}

	/*
	 * Get random chars.
	 */
	public String getRandomString(int num) {
		return String.valueOf(randString.charAt(num));
	}
}