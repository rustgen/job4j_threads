package ru.job4j.pool;

import java.util.Objects;

public class Job implements Runnable {

    private final int id;

    public Job(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Job job = (Job) o;
        return id == job.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Job{"
                + "id=" + id
                + '}';
    }

    @Override
    public void run() {
        System.out.println("I'm working...");
        System.out.println(Thread.currentThread().getName());
    }
}
