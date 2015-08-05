package br.com.correios.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by rpeixoto on 03/08/15.
 */
@Component
public class StringHelper {

    public String rightReplaceWithZeros(String str, int size) {
        return rightReplace(str, size, '0');
    }

    public String rightReplace(String str, int size, char padChar) {

        int strLength = str.length();
        if (strLength < size) {
            return null;
        }

        int endSubstringIndex = strLength - size;
        final String substring = str.substring(0, endSubstringIndex);
        final String rightStr = StringUtils.repeat(padChar, size);

        final StringBuilder newStr = new StringBuilder(substring)
                .append(rightStr);
        return newStr.toString();
    }
}
