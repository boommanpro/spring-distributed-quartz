package cn.boommanpro.web.filter;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.*;

import cn.boommanpro.common.TraceConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * @author boommanpro
 * @date 2020/3/16 11:20
 */
@Slf4j
public class TraceLogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("TraceLogFilter init success");

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put(TraceConfig.TRACE_STRING, UUID.randomUUID().toString().replace("-", ""));
        chain.doFilter(request, response);
    }
}
