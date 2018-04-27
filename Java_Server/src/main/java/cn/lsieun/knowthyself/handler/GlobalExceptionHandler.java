package cn.lsieun.knowthyself.handler;

import cn.lsieun.knowthyself.dto.CommonDTO;
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
    public Map<String, Object> exceptionHandler(HttpServletRequest req, Exception ex) throws Exception {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("success", false);
        modelMap.put("errMsg", ex.getMessage());
        return modelMap;
    }

    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public CommonDTO userExceptionHandler(HttpServletRequest req, Exception ex) throws Exception {
        CommonDTO dto = new CommonDTO(false, ex.getMessage());
        return dto;
    }
}
