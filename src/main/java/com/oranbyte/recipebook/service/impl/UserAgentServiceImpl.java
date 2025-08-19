package com.oranbyte.recipebook.service.impl;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.oranbyte.recipebook.service.UserAgentService;

@Service
public class UserAgentServiceImpl implements UserAgentService {

    @Override
    public String detectDevice(String userAgent) {
        if (userAgent.toLowerCase().contains("mobile")) return "Mobile";
        if (userAgent.toLowerCase().contains("tablet")) return "Tablet";
        if (userAgent.toLowerCase().matches(".*(laptop|notebook).*")) return "Laptop";
        return "Desktop";
    }

    @Override
    public String detectBrowser(String userAgent) {
        if (userAgent.contains("Brave") || userAgent.contains("Vivaldi")) return "Brave";
        if (userAgent.contains("Opera") || userAgent.contains("OPR")) return "Opera";
        if (userAgent.contains("Chrome")) return "Chrome";
        if (userAgent.contains("Firefox")) return "Firefox";
        if (userAgent.contains("Safari") && !userAgent.contains("Chrome")) return "Safari";
        if (userAgent.contains("Edge")) return "Edge";
        return "Other";
    }

    @Override
    public String detectOS(String userAgent) {
        if (userAgent.contains("Windows")) return "Windows";
        if (userAgent.contains("Mac OS")) return "macOS";
        if (userAgent.contains("Linux")) return "Linux";
        if (userAgent.contains("Android")) return "Android";
        if (userAgent.contains("iPhone")) return "iOS";
        return "Other";
    }

    @Override
    public String getLocationFromIP(String ip) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://ip-api.com/json/" + ip;
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map<String, Object> data = response.getBody();

            if (data == null || "fail".equals(data.get("status"))) {
                return "Unknown Location";
            }

            String city = (String) data.getOrDefault("city", "Unknown City");
            String country = (String) data.getOrDefault("country", "Unknown Country");

            return city + ", " + country;

        } catch (Exception e) {
            return "Unknown Location";
        }
    }
}
