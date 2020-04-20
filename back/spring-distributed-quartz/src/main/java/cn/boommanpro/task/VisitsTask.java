package cn.boommanpro.task;

import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:boommanpro@gmail.com">boomman</a>
 * @date 2019/5/29 17:10
 * @created by boommanpro
 */

@Component
public class VisitsTask {



    public void run(){

        System.out.println("可以注入Service进行配置");
    }
}
