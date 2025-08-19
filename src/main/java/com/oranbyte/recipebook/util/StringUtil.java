package com.oranbyte.recipebook.util;

import org.springframework.stereotype.Component;

@Component("stringUtil")
public class StringUtil {
    public String truncate(String str, int length) {
        if (str == null) return null;
        return str.length() > length ? str.substring(0, length) + "..." : str;
    }
    
    public boolean isEmpty(String str) {
    	if (str == null) return true;
    	return str.trim().equals("");
    }
    
}

