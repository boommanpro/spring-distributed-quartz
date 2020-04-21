package cn.boommanpro.web;

import java.util.List;

import cn.boommanpro.web.filter.TraceLogFilter;
import cn.boommanpro.web.resolver.JpaSortsMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author boommanpro
 * @date 2020/3/16 19:44
 */
@Configuration
public class WebServletComponentsConfiguration implements WebMvcConfigurer {

    @Autowired
    private JpaSortsMethodArgumentResolver jpaSortsMethodArgumentResolver;

    @Bean
    public FilterRegistrationBean<TraceLogFilter> traceLogFilter() {
        FilterRegistrationBean<TraceLogFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TraceLogFilter());
        registrationBean.setOrder(Integer.MIN_VALUE + 2);
        return registrationBean;
    }



    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(jpaSortsMethodArgumentResolver);
    }
}
