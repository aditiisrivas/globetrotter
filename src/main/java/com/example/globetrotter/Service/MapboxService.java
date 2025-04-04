package com.example.globetrotter.Service;


import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class MapboxService {
    @Value("${mapbox.access.token}")
    private String accessToken;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getLocationData(String cityName) {
        String url = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + cityName + ".json?access_token=" + accessToken;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray features = jsonResponse.getJSONArray("features");

            if (features.length() > 0) {
                JSONArray coordinates = features.getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates");
                double longitude = coordinates.getDouble(0);
                double latitude = coordinates.getDouble(1);

                Map<String, Object> locationData = new HashMap<>();
                locationData.put("city", cityName);
                locationData.put("latitude", latitude);
                locationData.put("longitude", longitude);
                return locationData;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching location data for: " + cityName, e);
        }

        return Collections.singletonMap("city", cityName);
    }
}

