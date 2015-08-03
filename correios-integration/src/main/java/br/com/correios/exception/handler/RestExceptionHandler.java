package br.com.correios.exception.handler;

import br.com.correios.constants.WsErrors;
import br.com.correios.json.FieldValidationError;
import br.com.correios.json.RequestError;
import br.com.correios.json.RequestValidationError;
import br.com.correios.util.MessageHelper;
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
    private MessageHelper messageHelper;

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public RequestError requestMethodNotSupported(HttpServletRequest req, HttpRequestMethodNotSupportedException ex) {
        final String errorMessage = messageHelper.getWsErrorMessage(WsErrors.HTTP_METHOD_NOT_ALLOWED_ERROR, ex.getMethod());
        final RequestError requestError = new RequestError(WsErrors.HTTP_METHOD_NOT_ALLOWED_ERROR.getErrorCode(), errorMessage);
        return requestError;
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RequestError requestHandlingGenericException(HttpServletRequest req, Exception ex) {
        logger.error("Erro ao processar requisicao. url={}", req.getRequestURI(), ex);

        final String errorMessage = messageHelper.getWsErrorMessage(WsErrors.GENERIC_ERROR);
        final RequestError requestError = new RequestError(WsErrors.GENERIC_ERROR.getErrorCode(), errorMessage);
        return requestError;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RequestValidationError requestHandlingMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException ex) {
        final FieldValidationError[] fieldValidationErrors = getFieldValidationErrors(ex);
        final String errorMessage = messageHelper.getWsErrorMessage(WsErrors.REQUEST_VALIDATION_ERROR);
        final RequestValidationError requestValidationError = new RequestValidationError(WsErrors.REQUEST_VALIDATION_ERROR.getErrorCode(), errorMessage, fieldValidationErrors);
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
