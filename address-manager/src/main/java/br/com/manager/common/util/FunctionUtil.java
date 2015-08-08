package br.com.manager.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by rpeixoto on 08/08/15.
 */
public class FunctionUtil {

    public static void updateIntegerIfNotNull(Supplier<Integer> getter, Consumer<Integer> setter) {
        final Integer value = getter.get();
        if(value != null) {
            setter.accept(value);
        }
    }

    public static void updateStringIfNotEmpty(Supplier<String> getter, Consumer<String> setter) {
        final String value = getter.get();
        if(StringUtils.isNotEmpty(value)) {
            setter.accept(value);
        }
    }
}
