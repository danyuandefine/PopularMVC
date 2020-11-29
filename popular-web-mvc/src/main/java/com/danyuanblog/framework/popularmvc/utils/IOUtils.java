package com.danyuanblog.framework.popularmvc.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class IOUtils {
	public static String getThrowableInfo(Throwable throwable) {
		
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        PrintStream printStream = new PrintStream(outputStream);
        throwable.printStackTrace(printStream);
        return outputStream.toString();
	}
	
	public static String getThrowableInfoLess(Throwable throwable) {
		
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);
        PrintStream printStream = new PrintStream(outputStream);
        throwable.printStackTrace(printStream);
        return outputStream.toString();
	}
}
