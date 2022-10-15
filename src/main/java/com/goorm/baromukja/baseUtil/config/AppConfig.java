package com.goorm.baromukja.baseUtil.config;

import com.goorm.baromukja.baseUtil.formatter.LocalDateFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/04
 *
 */

@Configuration
public class AppConfig {

    @Bean
    public LocalDateFormatter localDateFormatter() {
        return new LocalDateFormatter();
    }
}
