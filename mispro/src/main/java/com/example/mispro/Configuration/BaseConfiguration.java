package com.example.mispro.Configuration;

import com.example.mispro.Interceptor.BaseInterceptor;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BaseConfiguration implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BaseInterceptor())
                .addPathPatterns("/myHome").excludePathPatterns("/css/*","/fonts/**","/images/**","/","/js/**","/index"
                ,"/login","/register","/test","/newTest","/print","/statistics","success");
    }
    @Bean
    public CharacterEncodingFilter characterEncodingFilter()
    {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        return characterEncodingFilter;
    }
    @Bean
   public Gson getGosn(){
        return new Gson();
    }

}
