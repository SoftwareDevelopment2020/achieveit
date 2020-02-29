package com.softwaredevelopment.achieveit;

import com.softwaredevelopment.achieveit.utils.log.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AchieveitApplication {

    public static void main(String[] args) throws Exception {
        Logger.init();
        SpringApplication.run(AchieveitApplication.class, args);
    }

}
