package br.com.stream.finder;

import br.com.stream.finder.domain.Stream;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by rpeixoto on 04/08/15.
 */
public class StreamFinderBenchmarking {

    @Test
    public void benchmarking() throws Exception {

        final Character[] charArray = getCharArray();

        final Stream testStream = new ListStream(charArray);
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final char streamFinderFirstChar = StreamFinder.firstChar(testStream);
        stopWatch.stop();
        System.out.println("StreamFinder: " + stopWatch.getNanoTime());

        stopWatch.reset();
        final Stream testStream2 = new ListStream(charArray);
        stopWatch.start();
        final char optimzedStreamFinderFirstChar = OptimizedStreamFinder.firstChar(testStream2);
        stopWatch.stop();
        System.out.println("OptimizedStreamFinder: " + stopWatch.getNanoTime());

        Assert.assertThat(streamFinderFirstChar, is(equalTo('b')));
        Assert.assertThat(optimzedStreamFinderFirstChar, is(equalTo('b')));
    }

    /**
     * Gera um array de char suficientemente grande para conseguir validar a diferença de performance
     * @return
     */
    private Character[] getCharArray() {
        Character[] characters = new Character[Integer.MAX_VALUE/15];
        final Random random = new Random();
        for (int i = 0; i < characters.length; i++) {
            int randomInt = random.nextInt(125);
            if(randomInt == 98) {
                randomInt = 97;
            }
            characters[i] = (char) randomInt;
        }
        characters[characters.length - 1] = 'b';
        return characters;
    }
}
