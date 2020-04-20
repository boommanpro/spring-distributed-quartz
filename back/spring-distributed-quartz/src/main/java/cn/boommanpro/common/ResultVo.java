package cn.boommanpro.common;

import lombok.Data;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;

/**
 * @author boommanpro
 * @date 2020/3/16 9:17
 */
@Data
public class ResultVo<T> {

    private int code;

    private String showMsg;

    private String errorMsg;

    private T data;

    private String traceId;

    public ResultVo(int code, String showMsg, String errorMsg, T data) {
        this.code = code;
        this.showMsg = showMsg;
        this.errorMsg = errorMsg;
        this.data = data;
        this.traceId = MDC.get(TraceConfig.TRACE_STRING);
    }

    public static <T> ResultVo<T> success() {
        return new ResultVo<>(ResultCode.SUCCESS.getCode(), "", ResultCode.SUCCESS.getShowMsg(), null);
    }

    public static<E> ResultVo<PageVo<E>> success(Page<E> page){
        return new ResultVo<>(ResultCode.SUCCESS.getCode(), "", ResultCode.SUCCESS.getShowMsg(), PageVo.of(page));
    }

    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<>(ResultCode.SUCCESS.getCode(), "", ResultCode.SUCCESS.getShowMsg(), data);
    }

    public static <T> ResultVo<T> success(String showMsg, T data) {
        return new ResultVo<>(ResultCode.SUCCESS.getCode(), showMsg, ResultCode.SUCCESS.getShowMsg(), data);
    }

    public static <T> ResultVo<T> error(ResultCode code, String errorMeg, T data) {
        return new ResultVo<>(code.getCode(), code.getShowMsg(), errorMeg, data);
    }

}
