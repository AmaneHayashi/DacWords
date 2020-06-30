package com.amane.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5Tools {
	
	public static final String salt = "0dac9chu0words8";

    public final static String md5(String text) {
    
        MessageDigest digest = null;
        String realText = text.concat(salt);
        try {
            digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(realText.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : result){
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1){
                    sb.append("0" + hex);
                }else {
                    sb.append(hex);
                }
            }
            return sb.toString().substring(8, 24).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            //e.printStackTrace();
            return "";
        }
    }
    
    public static void main(String[] args) {
    	System.out.println(md5("12345678"));
    	System.out.println(md5("neeaneea"));
    }
}
