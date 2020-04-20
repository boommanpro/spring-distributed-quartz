package cn.boommanpro.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 用户将Throwable转为String 常用于日志保存
 * @author <a href="mailto:boommanpro@gmail.com">BoomManPro</a>
 * @date 2019/5/29 16:38
 * @created by BoomManPro
 */
public class ThrowableUtil {

    /**
     * 获取堆栈信息
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
