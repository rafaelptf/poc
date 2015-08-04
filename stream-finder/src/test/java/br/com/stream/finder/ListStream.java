package br.com.stream.finder;

import br.com.stream.finder.domain.Stream;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rpeixoto on 04/08/15.
 */
public class ListStream implements Stream {

    private final List<Character> characters;
    private int index;

    public ListStream(Character... characters) {
        this.characters = Arrays.asList(characters);
    }

    public char getNext() {
        return characters.get(index++);
    }

    public boolean hasNext() {
        if (index >= characters.size()) {
            return false;
        }
        return true;
    }
}
