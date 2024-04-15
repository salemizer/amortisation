package com.ldms.amortisation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Utility {

	private static Logger log = LoggerFactory.getLogger(Utility.class);

	public static Double twoDecimalPlaces(Double num) {

		return Double.parseDouble(String.format("%.2f", num));
	}

	public static Double[] twoDecimalPlaces(Double... nums) {

		Double[] result = new Double[nums.length];
		for (int i = 0; i < nums.length; i++) {
			result[i] = Math.round(nums[i] * 100.0) / 100.0;
		}
		return result;
	}
}
