package ru.job4j.producerconsumer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    void whenAddValuesToQueueTillSize() {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(
                () -> {
                    queue.offer(1);
                    queue.offer(2);
                    queue.offer(3);
                }
        );
        Thread consumer = new Thread(
                () -> {
                    assertThat(queue.poll()).isEqualTo(1);
                    queue.poll();
                    assertThat(queue.poll()).isEqualTo(3);
                }

        );
        assertThat(producer.getState()).isEqualTo(Thread.State.NEW);
        producer.start();
        producer.interrupt();

        assertThat(consumer.getState()).isEqualTo(Thread.State.NEW);
        consumer.start();
        assertThat(consumer.getState()).isEqualTo(Thread.State.RUNNABLE);
        consumer.interrupt();
        try {
            producer.join(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertThat(producer.getState()).isEqualTo(Thread.State.TERMINATED);
        assertThat(consumer.getState()).isEqualTo(Thread.State.RUNNABLE);
        try {
            consumer.join(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertThat(consumer.getState()).isEqualTo(Thread.State.TERMINATED);
    }

    @Test
    void whenAddValuesToQueueMoreThanSize() {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(
                () -> {
                    queue.offer(1);
                    queue.offer(2);
                    queue.offer(3);
                    queue.offer(4);
                    queue.offer(5);
                    queue.offer(6);
                }
        );
        Thread consumer = new Thread(
                () -> {
                    assertThat(queue.poll()).isEqualTo(1);
                    assertThat(queue.poll()).isEqualTo(2);
                    assertThat(producer.getState()).isEqualTo(Thread.State.WAITING);
                    assertThat(queue.poll()).isEqualTo(3);
                    assertThat(producer.getState()).isEqualTo(Thread.State.BLOCKED);
                    assertThat(queue.poll()).isEqualTo(4);
                }

        );
        producer.start();
        consumer.start();
        assertThat(consumer.getState()).isEqualTo(Thread.State.RUNNABLE);
        producer.interrupt();
        consumer.interrupt();
        try {
            producer.join(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        try {
            consumer.join(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertThat(consumer.getState()).isEqualTo(Thread.State.BLOCKED);
    }
}