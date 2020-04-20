package cn.boommanpro.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:boommanpro@gmail.com">boomman</a>
 * @date 2019/5/29 17:10
 * @created by boommanpro
 */

@Slf4j
@Component
public class TestTask {

    public void run(){
        log.info("执行成功");
    }

    public void run1(String str){
        log.info("执行成功，参数为： {}" ,str);
    }
}
