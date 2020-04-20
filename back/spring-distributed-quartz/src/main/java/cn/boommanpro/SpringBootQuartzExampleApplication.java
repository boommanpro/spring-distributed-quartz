package cn.boommanpro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

/**
 * @author <a href="mailto:boommanpro@gmail.com">boomman</a>
 * @date 2019/5/29 17:10
 * @created by boommanpro
 */

@Slf4j
@SpringBootApplication
public class SpringBootQuartzExampleApplication {
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = new SpringApplication(SpringBootQuartzExampleApplication.class).run(args);
        ServerProperties serverProperties = context.getBean(ServerProperties.class);
        Integer port = Optional.ofNullable(serverProperties.getPort()).orElse(8080);
        String contextPath = Optional.ofNullable(serverProperties.getServlet().getContextPath()).orElse("");
        String ipAddress = InetAddress.getLocalHost().getHostAddress();
        log.info("<------------------------- http://{}:{}{}/  ------------------------->", ipAddress, port, contextPath);
    }
}
