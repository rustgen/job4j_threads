package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelFindIndexTest {

    @Test
    public void whenObjectOfNumsFindMaxIndexOf4Result12() {
        Object[] nums = new Object[]{1, 2, 3, 56, 76, 2, 4, 98, 324, 453, 23, 1, 4, 24, 53};
        int numSearch = 4;
        ParallelFindIndex<Object> parallelFindIndex = new ParallelFindIndex<>(nums, numSearch, 0, nums.length);
        assertEquals(12, parallelFindIndex.find(nums, numSearch));
    }

}