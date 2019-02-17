package cn.bmaster.member;

import com.renaissance.core.web.filter.LoggingFilter;
import com.renaissance.core.web.interceptor.HttpAuthenticationInterceptor;
import com.renaissance.core.web.resolver.SecureArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LoggingFilter loggingFilter() {
        return new LoggingFilter()
                .excludePath("/swagger")
                .excludePath("/csrf")
                .excludePath("/v2/api-docs")
                .excludePath("/webjars")
                .excludePath("/favicon.ico");
    }

    @Bean
    public HttpAuthenticationInterceptor httpAuthenticationInterceptor() {
        return new HttpAuthenticationInterceptor();
    }

    @Bean
    public SecureArgumentResolver secureArgumentResolver() {
        return new SecureArgumentResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加http authentication拦截器
        registry.addInterceptor(httpAuthenticationInterceptor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        // 添加secureResolver
        resolvers.add(secureArgumentResolver());
    }

}
