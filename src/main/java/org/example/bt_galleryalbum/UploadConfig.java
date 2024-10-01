package org.example.bt_galleryalbum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class UploadConfig implements WebMvcConfigurer {
    @Value("${UPLOAD_DIR}")
    String UPLOAD_DIR;
    // add your configuration here
    //lấy ra tháng hiện tại
    String month = new SimpleDateFormat("MM").format(new Date());
    //lay ra nam hien tai
    String year = new SimpleDateFormat("yyyy").format(new Date());
    String folderName = month+"-"+year+"/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println(UPLOAD_DIR+folderName);
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("File:" + UPLOAD_DIR);
    }
}
