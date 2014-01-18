package com.h4313.deephouse.server.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Tool
{
	public static boolean isValidIp(final String ip)
	{
		final String PATTERN = 
		        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}
	
	public static boolean isValidPort(final String port)
	{
		final String PATTERN = "^([0-9]*)$";

		Pattern pattern = Pattern.compile(PATTERN);
		Matcher matcher = pattern.matcher(port);
		return matcher.matches();
	}
}
