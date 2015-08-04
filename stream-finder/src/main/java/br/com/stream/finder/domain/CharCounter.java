package br.com.stream.finder.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by rpeixoto on 04/08/15.
 */
public class CharCounter {

    private final int index;
    private final char character;
    private int count;

    public CharCounter(int index, char character, int count) {
        this.index = index;
        this.character = character;
        this.count = count;
    }

    public void incrementCount() {
        count++;
    }

    public boolean isUnique() {
        if (count > 1) {
            return false;
        }
        return true;
    }

    public int getIndex() {
        return index;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("index", index)
                .append("character", character)
                .append("count", count)
                .toString();
    }
}
