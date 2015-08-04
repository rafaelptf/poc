package br.com.stream.finder;

import br.com.stream.finder.domain.CharCounter;
import br.com.stream.finder.domain.Stream;
import br.com.stream.finder.exception.FirstUniqueCharNotFoundException;

/**
 * Created by rpeixoto on 04/08/15.
 */
public class OptimizedStreamFinder {

    public static char firstChar(Stream input) throws FirstUniqueCharNotFoundException {

        //Se a stream for nula, retorna que nao achou o caracter
        if (input == null) {
            throw getFirstUniqueCharNotFoundException();
        }

        // Agrupa os caracteres e conta eles
        final CharCounter[] charCounters = groupAndCountChar(input);

        // busca o primeiro caracter unico
        final CharCounter firstChar = findFirstUniqueChar(charCounters);

        //Caso nao ache o caracter, retorna uma excecao indicando
        if(firstChar == null) {
            throw getFirstUniqueCharNotFoundException();
        }

        //Retorna o caracter encontrado
        return firstChar.getCharacter();
    }

    private static FirstUniqueCharNotFoundException getFirstUniqueCharNotFoundException() {
        return new FirstUniqueCharNotFoundException("Caracter unico nao encontrado");
    }

    /**
     * Percorre todos os possíveis caracteres (127) da tabela ASCII e verifica se ele é unico na stream.
     * Caso seja único, verifica se ele estava inserido na stream antes do último primeiro caracter único encontrado,
     * caso afirmativo, este é o novo primeiro caracter único
     *
     * @param charCounters
     * @return
     */
    private static CharCounter findFirstUniqueChar(CharCounter[] charCounters) {
        CharCounter firstChar = null;
        for (CharCounter charCounter : charCounters) {
            //Se o caracter nao foi encontrado na stream, passa para o proximo
            if(charCounter == null) {
                continue;
            }

            if (charCounter.isUnique()) {
                // Se nao tem primeiro caracter unico, ou se este caracter tem indice menor, ou seja, apareceu antes na stream
                if(firstChar == null || charCounter.getIndex() < firstChar.getIndex() ) {
                    firstChar = charCounter;
                }
            }
        }
        return firstChar;
    }

    /**
     * Dado um stream percorre o mesmo contando a aparição de cada caracter, agrupando pelo charCode da tabela ASCII
     *
     * @param input stream a ser percorrido
     * @return array contendo todos os possíveis caracteres, e suas respectivas contagens
     */
    private static CharCounter[] groupAndCountChar(Stream input) {
        final CharCounter[] charCounters = new CharCounter [256];
        int streamIndex = 0;
        while (input.hasNext()) {
            final char nextChar = input.getNext();

            final int numericValue = (int) nextChar;
            if(numericValue >= charCounters.length) {
                // Desconsidera caracteres especiais que tem valor na tabela ASCII maior que 255
                continue;
            }

            // Obtem a contagem do caracter do Array, utilizando o valor do caracter na tabela ASCII como indice
            final CharCounter newCharCounter = charCounters[numericValue];
            if(newCharCounter == null) {
                // Primeira vez que o caracter aparece na stream
                charCounters[numericValue] = new CharCounter(streamIndex, nextChar, 1);
            } else {
                // caracter ja apareceu na stream, incrementa a contagem
                newCharCounter.incrementCount();
            }
            streamIndex++;
        }
        return charCounters;
    }

}
