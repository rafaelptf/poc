package br.com.manager.common.util;

import br.com.manager.common.constants.WsResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by rpeixoto on 03/08/15.
 */
@Component
public class MessageSourceWrapper {

    @Autowired
    private MessageSource messageSource;

    public String getWsResponseMessage(WsResponseCode wsResponseCode) {
        return getWsResponseMessage(wsResponseCode, null);
    }

    public String getWsResponseMessage(WsResponseCode wsResponseCode, Object... args) {
        return getMessage(wsResponseCode.getMessageKey(), args);
    }

    private String getMessage(String messageKey, Object[] args) {
        final Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, args, locale);
    }


}
