package cn.bmaster.member.web.controller;

import com.renaissance.core.Result;
import com.renaissance.core.exception.AuthorizationException;
import com.renaissance.core.exception.BusinessException;
import com.renaissance.core.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author Wilson
 */
@ControllerAdvice
@ApiIgnore
public class GlobalExceptionAdviceController {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdviceController.class);

    /**
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result handleBusinessException(BusinessException exception) {
        Result result = new Result();
        result.fail(exception.getCode()).message(exception.getMessage());
        logger.error(" handleBusinessException() ", exception);
        return result;
    }

    /**
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleAuthorizationException(AuthorizationException exception) {
        Result result = new Result();
        result.fail(exception.getCode()).message(exception.getMessage());
        logger.info(" AuthorizationException: {}", exception.getMessage());
        return result;
    }

    /**
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result handleForbiddenException(ForbiddenException exception) {
        Result result = new Result();
        result.fail(exception.getCode()).message(exception.getMessage());
        logger.info(" handleForbiddenException: {}", exception.getMessage());
        return result;
    }

    /**
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Result result = new Result();

        // Always get the first error's default message
        BindingResult bindingResult = exception.getBindingResult();
        ObjectError error = bindingResult.getAllErrors().get(0);

        String message = "";
        if (error instanceof FieldError) {
            FieldError fieldError = (FieldError) error;
            message = "Field error on field '" + fieldError.getField() + "': " + fieldError.getDefaultMessage();
        } else {
            message = error.getDefaultMessage();
        }

        result.fail("400").message(message);
        logger.error(" handleMethodArgumentNotValidException() ", exception);
        return result;
    }

    /**
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        Result result = new Result();

        result.fail("400").message(exception.getMessage());

        logger.error(" handleHttpMessageNotReadableException() ", exception);
        return result;
    }
    /**
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        Result result = new Result();
        result.fail("400").message(exception.getMessage());
        logger.error(" handleMissingServletRequestParameterException() ", exception);
        return result;
    }

    /**
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleBindException(BindException exception) {

        Result result = new Result();

        // Always get the first error's default message
        BindingResult bindingResult = exception.getBindingResult();
        ObjectError error = bindingResult.getAllErrors().get(0);

        String message = "";
        if (error instanceof FieldError) {
            FieldError fieldError = (FieldError) error;
            message = "Field error on field '" + fieldError.getField() + "': " + fieldError.getDefaultMessage();
        } else {
            message = error.getDefaultMessage();
        }

        result.fail("400").message(message);
        logger.error(" handleBindException() ", exception);
        return result;
    }


    /**
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {

        Result result = new Result();
        result.fail("400").message(exception.getMessage());
        logger.error(" handleMethodNotSupportedException() ", exception);
        return result;
    }

    /**
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(Exception exception) {
        Result result = new Result();
        result.fail("500").message("服务器未知异常");
        logger.error(" handleException() ", exception);
        return result;
    }

}