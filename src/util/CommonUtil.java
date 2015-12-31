package util;

import java.text.SimpleDateFormat;

public interface CommonUtil {

	public static SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static String NAMEDELIM = ", ";
	public static String DELIM = " | ";
	public static String BLANK = "";
	public static enum Sex {
		FEMALE, MALE
	}
	
}
