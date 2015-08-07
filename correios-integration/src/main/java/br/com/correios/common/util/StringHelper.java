package br.com.correios.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by rpeixoto on 03/08/15.
 */
@Component
public class StringHelper {

    public String rightReplaceWithZeroFirstNonZeroChar(String str) {
        final char padChar = '0';

        // Itera na String do fim ao começo
        Integer firstDifferentCharIndex = null;
        for (int i = str.length() - 1; i >= 0; i--) {
            char strChar = str.charAt(i);
            if (strChar != padChar) {
                firstDifferentCharIndex = i;
                break;
            }
        }

        //String nao contem caracteres diferentes de zero, retorna ela mesmo
        if (firstDifferentCharIndex == null) {
            return str;
        }

        //Substitui o caracter diferente por 0
        StringBuilder returnStr = new StringBuilder(str);
        returnStr.setCharAt(firstDifferentCharIndex, padChar);
        return returnStr.toString();
    }

}
