package com.oranbyte.recipebook.service;
public interface UserAgentService {
    String detectDevice(String userAgent);
    String detectBrowser(String userAgent);
    String detectOS(String userAgent);
    String getLocationFromIP(String ip);
}