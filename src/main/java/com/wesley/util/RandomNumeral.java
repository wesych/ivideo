package com.wesley.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class RandomNumeral {

	public static int[] getNumeral(int range, int count) {
		if (count > range) {
			return null;
		}
		HashSet<Integer> set = new HashSet<Integer>();
		Random ran = new Random();
		while (set.size() < count) {
			set.add(ran.nextInt(range) + 1);
		}

		// store data in array
		Iterator<Integer> it = set.iterator();
		int i = 0;
		int[] data = new int[count];
		while (it.hasNext()) {
			data[i++] = (Integer) it.next();
		}
		return data;
	}
}
