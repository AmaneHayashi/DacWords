package com.amane.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public final class Base64Tools {
	
	public final static String encode(String encoder) throws UnsupportedEncodingException {
		return Base64.getEncoder().encodeToString(encoder.getBytes("utf-8"));
	}
	
	public final static String decode(String decoder) throws UnsupportedEncodingException {
		return new String(Base64.getDecoder().decode(decoder.getBytes()), "utf-8");
	}
}
