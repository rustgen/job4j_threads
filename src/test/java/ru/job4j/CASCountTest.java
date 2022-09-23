package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {

    @Test
    void whenIncreaseIn2ThreadsWillSumCountsOfThemWithThreadSafe() {
        CASCount casCount = new CASCount();
        Thread countOne = new Thread(
                () -> {
                    for (int i = 0; i < 2; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread countTwo = new Thread(
                () -> {
                    for (int i = 0; i < 2; i++) {
                        casCount.increment();
                    }
                }
        );
        countOne.start();
        countTwo.start();
        countOne.interrupt();
        countTwo.interrupt();
        assertThat(4).isEqualTo(casCount.get());
        casCount.increment();
        casCount.increment();
        assertThat(6).isEqualTo(casCount.get());
    }

}