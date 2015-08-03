package br.com.correios.util;

import br.com.correios.constants.MessageKey;
import br.com.correios.constants.WsErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by rpeixoto on 03/08/15.
 */
@Component
public class MessageHelper {

    @Autowired
    private MessageSource messageSource;

    public String getWsErrorMessage(WsErrors wsErrors) {
        return getWsErrorMessage(wsErrors, null);
    }

    public String getWsErrorMessage(WsErrors wsErrors, Object... args) {
        return getMessage(wsErrors.getMessageKey(), args);
    }

    public String getMessage(MessageKey messageKey) {
        return getMessage(messageKey, null);
    }

    public String getMessage(MessageKey messageKey, Object[] args) {
        final Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey.getMessageKey(), args, locale);
    }


}
