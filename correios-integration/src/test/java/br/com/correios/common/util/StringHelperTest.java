package br.com.correios.common.util;

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
        String rightReplacedStr = stringHelper.rightReplaceWithZeroFirstNonZeroChar("04018181");
        assertThat(rightReplacedStr, is(equalTo("04018180")));
    }

    @Test
    public void rightReplaceWithMoreThanOneChar() {
        String rightReplacedStr = stringHelper.rightReplaceWithZeroFirstNonZeroChar("04018181");
        assertThat(rightReplacedStr, is(equalTo("04018000")));
    }

    @Test
    public void rightReplaceWithSizeBiggerThanString() {
        String rightReplacedStr = stringHelper.rightReplaceWithZeroFirstNonZeroChar("04018181");
        assertThat(rightReplacedStr, isEmptyOrNullString());
    }

    @Test
    public void rightReplaceWithSizeEqualsStringSize() {
        String rightReplacedStr = stringHelper.rightReplaceWithZeroFirstNonZeroChar("04018181");
        assertThat(rightReplacedStr, is(equalTo("00000000")));
    }
}
