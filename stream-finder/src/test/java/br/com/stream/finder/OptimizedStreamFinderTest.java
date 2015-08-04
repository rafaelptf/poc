package br.com.stream.finder;

import br.com.stream.finder.domain.Stream;
import br.com.stream.finder.exception.FirstUniqueCharNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by rpeixoto on 04/08/15.
 */
public class OptimizedStreamFinderTest {

    @Test
    public void firstCharWithOneUniqueCharShouldReturnThisChar() throws Exception {
        final Stream testStream = new ListStream('a', 'A', 'B', 'B', 'A', 'z', 'a');
        final char firstUniqueChar = OptimizedStreamFinder.firstChar(testStream);

        Assert.assertThat(firstUniqueChar, is(equalTo('z')));
    }

    @Test
    public void firstCharWithTwoUniqueCharsShouldReturnFirst() throws Exception {
        final Stream testStream = new ListStream('a', 'A', 'b', 'B', 'A', 'B', 'a', 'c');
        final char firstUniqueChar = OptimizedStreamFinder.firstChar(testStream);

        Assert.assertThat(firstUniqueChar, is(equalTo('b')));
    }

    @Test
    public void firstCharWithLastTwoUniqueCharsShouldReturnFirst() throws Exception {
        final Stream testStream = new ListStream('a', 'A', 'B', 'A', 'B', 'a', 'c', 'b');
        final char firstUniqueChar = OptimizedStreamFinder.firstChar(testStream);

        Assert.assertThat(firstUniqueChar, is(equalTo('c')));
    }

    @Test
    public void firstCharWithOnlyUniqueCharsShouldReturnFirst() throws Exception {
        final Stream testStream = new ListStream('H', 'a', 'k', 'n', 'e', 'c', 'r');
        final char firstUniqueChar = OptimizedStreamFinder.firstChar(testStream);

        Assert.assertThat(firstUniqueChar, is(equalTo('H')));
    }

    @Test
    public void firstCharWithOneCharStreamShouldReturnThisChar() throws Exception {
        final Stream testStream = new ListStream('R');
        final char firstUniqueChar = OptimizedStreamFinder.firstChar(testStream);

        Assert.assertThat(firstUniqueChar, is(equalTo('R')));
    }

    @Test(expected = FirstUniqueCharNotFoundException.class)
    public void firstCharWithNoUniqueCharsShouldThrowsException() throws Exception {
        final Stream testStream = new ListStream('a', 'B', 'z', 'A', 'z', 'B', 'a', 'A');
        OptimizedStreamFinder.firstChar(testStream);
    }

    @Test
    public void firstCharWithUniqueSpecialCharsShouldReturnFirst() throws Exception {
        final Stream testStream = new ListStream('a', 'A', '+', 'B', 'A', 'B', '@' , 'a', ' ', 'c', 'b');
        final char firstUniqueChar = OptimizedStreamFinder.firstChar(testStream);

        Assert.assertThat(firstUniqueChar, is(equalTo('+')));
    }

    @Test
    public void firstCharWithNotASCIIStandardCharsShouldReturnFirst() throws Exception {
        final Stream testStream = new ListStream('A', 'ű', 'A', 'ȹ','a', 'Ǖ', 'č');
        final char firstUniqueChar = OptimizedStreamFinder.firstChar(testStream);

        Assert.assertThat(firstUniqueChar, is(equalTo('a')));
    }

    @Test(expected = FirstUniqueCharNotFoundException.class)
    public void firstCharWithOneUniqueNotASCIIStandardCharShouldThrowsException() throws Exception {
        final Stream testStream = new ListStream('a', 'A', 'ű', 'A', 'a');
        OptimizedStreamFinder.firstChar(testStream);
    }

    @Test(expected = FirstUniqueCharNotFoundException.class)
    public void firstCharWithEmptyStreamShouldThrowsException() throws Exception {
        final Stream testStream = new ListStream();
        OptimizedStreamFinder.firstChar(testStream);
    }

    @Test(expected = FirstUniqueCharNotFoundException.class)
    public void firstCharWithNullStreamShouldThrowsException() throws Exception {
        OptimizedStreamFinder.firstChar(null);
    }
}
