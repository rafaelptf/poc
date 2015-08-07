package br.com.correios.common.handler;

import br.com.correios.common.constants.WsResponseCode;
import br.com.correios.common.domain.BaseResponse;
import br.com.correios.common.domain.FieldValidationError;
import br.com.correios.common.domain.RequestValidationError;
import br.com.correios.common.util.MessageSourceWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rpeixoto on 03/08/15.
 */
@ControllerAdvice
public class RestExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @Autowired
    private MessageSourceWrapper messageSourceWrapper;

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public BaseResponse requestMethodNotSupported(HttpServletRequest req, HttpRequestMethodNotSupportedException ex) {
        final String errorMessage = messageSourceWrapper.getWsResponseMessage(WsResponseCode.HTTP_METHOD_NOT_ALLOWED_ERROR, ex.getMethod());
        final BaseResponse baseResponse = new BaseResponse(WsResponseCode.HTTP_METHOD_NOT_ALLOWED_ERROR.getCode(), errorMessage);
        return baseResponse;
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse requestHandlingGenericException(HttpServletRequest req, Exception ex) {
        logger.error("Erro ao processar requisicao. url={}", req.getRequestURI(), ex);

        final String errorMessage = messageSourceWrapper.getWsResponseMessage(WsResponseCode.GENERIC_ERROR);
        final BaseResponse baseResponse = new BaseResponse(WsResponseCode.GENERIC_ERROR.getCode(), errorMessage);
        return baseResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RequestValidationError requestHandlingMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        final FieldValidationError[] fieldValidationErrors = getFieldValidationErrors(ex);
        final String errorMessage = messageSourceWrapper.getWsResponseMessage(WsResponseCode.REQUEST_VALIDATION_ERROR);
        final RequestValidationError requestValidationError = new RequestValidationError(WsResponseCode.REQUEST_VALIDATION_ERROR.getCode(), errorMessage, fieldValidationErrors);
        return requestValidationError;
    }

    private FieldValidationError[] getFieldValidationErrors(MethodArgumentNotValidException ex) {
        final List<FieldValidationError> fieldValidationsErrors = new ArrayList<FieldValidationError>();

        final BindingResult bindingResult = ex.getBindingResult();
        final List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError objectError : allErrors) {
            if (objectError instanceof FieldError) {
                final FieldError fieldError = (FieldError) objectError;

                final FieldValidationError fieldValidationError = new FieldValidationError(fieldError.getField(),
                        String.valueOf(fieldError.getRejectedValue()),
                        fieldError.getDefaultMessage());

                fieldValidationsErrors.add(fieldValidationError);
            }
        }

        return fieldValidationsErrors.toArray(new FieldValidationError[fieldValidationsErrors.size()]);
    }
}
