package br.com.stream.finder;

import br.com.stream.finder.domain.CharCounter;
import br.com.stream.finder.domain.Stream;
import br.com.stream.finder.exception.FirstUniqueCharNotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rpeixoto on 04/08/15.
 */
public class StreamFinder {

    public static char firstChar(Stream input) throws FirstUniqueCharNotFoundException {

        //Se a stream for nula, retorna que nao achou o caracter
        if (input == null) {
            throw getFirstUniqueCharNotFoundException();
        }

        // Agrupa os caracteres e conta eles
        final Map<Character, CharCounter> charsMap = groupAndCountChars(input);

        // busca o primeiro caracter unico
        CharCounter firstChar = findFirstUniqueChar(charsMap);

        //Caso nao ache o caracter, retorna uma excecao indicando
        if (firstChar == null) {
            throw getFirstUniqueCharNotFoundException();
        }

        //Retorna o caracter encontrado
        return firstChar.getCharacter();
    }

    private static CharCounter findFirstUniqueChar(Map<Character, CharCounter> charsMap) {
        CharCounter firstChar = null;
        final Collection<CharCounter> values = charsMap.values();
        for (CharCounter charCounter : values) {
            if (charCounter.isUnique()) {
                if (firstChar == null || charCounter.getIndex() < firstChar.getIndex()) {
                    firstChar = charCounter;
                }
            }
        }
        return firstChar;
    }

    private static Map<Character, CharCounter> groupAndCountChars(Stream input) {
        final Map<Character, CharCounter> charsMap = new HashMap<Character, CharCounter>();
        int streamIndex = 0;
        while (input.hasNext()) {
            final char nextChar = input.getNext();
            CharCounter newCharCounter = charsMap.get(nextChar);
            if (newCharCounter == null) {
                charsMap.put(nextChar, new CharCounter(streamIndex, nextChar, 1));
            } else {
                newCharCounter.incrementCount();
            }
            streamIndex++;
        }
        return charsMap;
    }

    private static FirstUniqueCharNotFoundException getFirstUniqueCharNotFoundException() {
        return new FirstUniqueCharNotFoundException("Caracter unico nao encontrado");
    }
}
