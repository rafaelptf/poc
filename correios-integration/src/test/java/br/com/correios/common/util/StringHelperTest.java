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
    public void rightReplaceWithFirstCharNonZero() {
        String rightReplacedStr = stringHelper.rightReplaceWithZeroFirstNonZeroChar("04018181");
        assertThat(rightReplacedStr, is(equalTo("04018180")));
    }

    @Test
    public void rightReplaceWithThirdCharNonZero() {
        String rightReplacedStr = stringHelper.rightReplaceWithZeroFirstNonZeroChar("04018100");
        assertThat(rightReplacedStr, is(equalTo("04018000")));
    }

    @Test
    public void rightReplaceWithLastCharNonZero() {
        String rightReplacedStr = stringHelper.rightReplaceWithZeroFirstNonZeroChar("90000000");
        assertThat(rightReplacedStr, is(equalTo("00000000")));
    }

    @Test
    public void rightReplaceAlreadyOnlyZerosString() {
        String rightReplacedStr = stringHelper.rightReplaceWithZeroFirstNonZeroChar("00000000");
        assertThat(rightReplacedStr, is(equalTo("00000000")));
    }
}
