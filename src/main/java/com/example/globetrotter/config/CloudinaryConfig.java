package com.example.globetrotter.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
//import lombok.Value;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@ConfigurationProperties(prefix = "cloudinary")
@Getter
@Setter
@Configuration
public class CloudinaryConfig {
    private String cloudName;
    private String apiKey;
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }
}
