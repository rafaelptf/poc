package br.com.correios.util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by rpeixoto on 03/08/15.
 */
public class StringHelperTest {

    private StringHelper stringHelper;

    @Before
    public void setUp() {
        stringHelper = new StringHelper();
    }

    @Test
    public void rightReplaceWithOneCharOnly() {
        String rightReplacedStr = stringHelper.rightReplaceWithZeros("04018181", 1);
        assertThat(rightReplacedStr, is(equalTo("04018180")));
    }

    @Test
    public void rightReplaceWithMoreThanOneChar() {
        String rightReplacedStr = stringHelper.rightReplaceWithZeros("04018181", 3);
        assertThat(rightReplacedStr, is(equalTo("04018000")));
    }

    @Test
    public void rightReplaceWithSizeBiggerThanString() {
        String rightReplacedStr = stringHelper.rightReplaceWithZeros("04018181", 9);
        assertThat(rightReplacedStr, isEmptyOrNullString());
    }

    @Test
    public void rightReplaceWithSizeEqualsStringSize() {
        String rightReplacedStr = stringHelper.rightReplaceWithZeros("04018181", 8);
        assertThat(rightReplacedStr, is(equalTo("00000000")));
    }
}
