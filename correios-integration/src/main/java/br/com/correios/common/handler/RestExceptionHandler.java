package br.com.correios.common.handler;

import br.com.correios.common.constants.WsResponseCode;
import br.com.correios.common.domain.FieldValidationError;
import br.com.correios.common.domain.WsResponse;
import br.com.correios.common.util.WsResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
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
    private WsResponseBuilder wsResponseBuilder;

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public WsResponse requestMethodNotSupported(HttpServletRequest req, HttpRequestMethodNotSupportedException ex) {
        return wsResponseBuilder.getNoContetyWsResponse(
                WsResponseCode.HTTP_METHOD_NOT_ALLOWED_ERROR,
                ex.getMethod());
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public WsResponse requestHandlingGenericException(HttpServletRequest req, Exception ex) {
        logger.error("Erro ao processar requisicao. url={}", req.getRequestURI(), ex);
        return wsResponseBuilder.getNoContetyWsResponse(WsResponseCode.GENERIC_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public WsResponse requestHandlingMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        final FieldValidationError[] fieldValidationErrors = getFieldValidationErrors(ex);
        return wsResponseBuilder.getFieldValidationErrorWsResponse(WsResponseCode.REQUEST_VALIDATION_ERROR, fieldValidationErrors);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public WsResponse requestHandlingHttpMediaTypeException(HttpMediaTypeNotSupportedException ex) {
        return wsResponseBuilder.getNoContetyWsResponse(WsResponseCode.UNSUPPORTED_MEDIA_TYPE, ex.getContentType());
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
