package com.fnx.webapp.controller.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.fnx.webapp.util.TimeUtil;

public class TimeUtilTest {

	@Test
	public void loadDateAfterMins() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date testDate = sdf.parse("2015-07-29 12:10:12");
		Date afterDate = TimeUtil.loadDateAfterMins(testDate, 20);
		Assert.assertEquals("2015-07-29 12:30:12", sdf.format(afterDate));
	}
}

