package ru.job4j;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CountTest {
    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        var count = new Count();
        var first = new Thread(count::increment);
        var second = new Thread(count::increment);
        /* Start threads. */
        first.start();
        second.start();
        /* Force the main thread to wait for the execution of our threads. */
        first.join();
        second.join();
        /* Check result. */
        assertThat(count.get()).isEqualTo(2);
    }
}