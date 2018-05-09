package cn.lsieun.knowthyself.handler;

import cn.lsieun.knowthyself.dto.ResultDTO;
import cn.lsieun.knowthyself.exception.UserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultDTO exceptionHandler(HttpServletRequest req, Exception ex) throws Exception {
        ResultDTO dto = new ResultDTO(false, ex.getMessage());
        return dto;
    }

    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public ResultDTO userExceptionHandler(HttpServletRequest req, Exception ex) throws Exception {
        ResultDTO dto = new ResultDTO(false, ex.getMessage());
        return dto;
    }
}
