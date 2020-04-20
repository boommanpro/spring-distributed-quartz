package cn.boommanpro.web;

import cn.boommanpro.web.filter.TraceLogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author boommanpro
 * @date 2020/3/16 19:44
 */
@Configuration
public class WebServletComponentsConfiguration {

    @Bean
    public FilterRegistrationBean<TraceLogFilter> traceLogFilter() {
        FilterRegistrationBean<TraceLogFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TraceLogFilter());
        registrationBean.setOrder(Integer.MIN_VALUE + 2);
        return registrationBean;
    }

}
