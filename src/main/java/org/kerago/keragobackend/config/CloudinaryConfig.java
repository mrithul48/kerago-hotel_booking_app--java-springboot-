package org.kerago.keragobackend.config;

import com.cloudinary.Cloudinary;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dr7bozhoi",
                "api_key", "188487161324453",
                "api_secret", "wpIvWjL2f5TQ9qTpifQLOrj8u-Y"));

    }
}
