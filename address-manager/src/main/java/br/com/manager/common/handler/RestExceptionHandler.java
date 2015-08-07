package br.com.manager.common.handler;

import br.com.manager.common.constants.WsResponseCode;
import br.com.manager.common.domain.FieldValidationError;
import br.com.manager.common.domain.WsResponse;
import br.com.manager.common.util.WsResponseBuilder;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public WsResponse requestHandlingHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        final Throwable exCause = ex.getCause();
        if (exCause instanceof InvalidFormatException) {
            final InvalidFormatException invalidFormatException = (InvalidFormatException) exCause;
            final FieldValidationError[] fieldValidationErrors = getFieldBinding(invalidFormatException);
            return wsResponseBuilder.getFieldValidationErrorWsResponse(WsResponseCode.REQUEST_VALIDATION_ERROR, fieldValidationErrors);
        }

        //Retorna erro mais generica pois nao conseguiu ler a requisicao
        return wsResponseBuilder.getFieldValidationErrorWsResponse(WsResponseCode.REQUEST_VALIDATION_ERROR, null);
    }


    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public WsResponse requestHandlingHttpMediaTypeException(HttpMediaTypeNotSupportedException ex) {
        return wsResponseBuilder.getNoContetyWsResponse(WsResponseCode.UNSUPPORTED_MEDIA_TYPE, ex.getContentType());
    }


    private FieldValidationError[] getFieldBinding(InvalidFormatException invalidFormatException) {
        final List<FieldValidationError> fieldValidationsErrors = new ArrayList<FieldValidationError>();
        final List<JsonMappingException.Reference> path = invalidFormatException.getPath();

        final JsonMappingException.Reference reference;
        //Nao tem como obter o nome da variavel que deu problema no Json, retorna somente com os valores
        if (path.isEmpty()) {
            final FieldValidationError fieldValidationError = new FieldValidationError(StringUtils.EMPTY, String.valueOf(invalidFormatException.getValue()), invalidFormatException.getOriginalMessage());
            return new FieldValidationError[]{fieldValidationError};
        }

        //Tem como obter o nome da variavel que deu problema no Json
        if (path.size() > 1) {
            reference = path.get(path.size() - 1);
        } else {
            reference = path.get(0);
        }

        final FieldValidationError fieldValidationError = new FieldValidationError(reference.getFieldName(), String.valueOf(invalidFormatException.getValue()), invalidFormatException.getOriginalMessage());
        return new FieldValidationError[]{fieldValidationError};
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

