package com.example.unittestdemo;

import com.example.unittestdemo.dtos.SortDirection;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToStatusConverter());
    }

    private static class StringToStatusConverter implements Converter<String, SortDirection> {
        @Override
        public SortDirection convert(String source) {
            return SortDirection.fromValue(source);
        }
    }
}
