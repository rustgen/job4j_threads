package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelFindIndexTest {

    @Test
    public void whenObjectOfNumsFindMaxIndexOf4Result12() {
        Integer[] nums = new Integer[]{1, 2, 3, 56, 76, 2, 4, 98, 324, 453, 23, 1, 4, 24, 53};
        int numSearch = 4;
        ParallelFindIndex<Integer> parallelFindIndex =
                new ParallelFindIndex<>(nums, numSearch, 0, nums.length - 1);
        assertEquals(12, parallelFindIndex.find(nums, numSearch));
    }

    @Test
    public void whenObjectOfNumsFindMaxIndexOf11Result12() {
        Object[] nums = new Object[]{1, 2, 11, 3, 56, 76, 2, 4, 98, 324, 453, 23, 11, 1, 4, 24, 53};
        Object numSearch = 11;
        ParallelFindIndex<Object> parallelFindIndex =
                new ParallelFindIndex<>(nums, numSearch, 0, nums.length - 1);
        assertEquals(12, parallelFindIndex.find(nums, numSearch));
    }

    @Test
    public void whenObjectOfNumsLessThan10FindMaxIndexOf2ResultIs1() {
        Object[] nums = new Object[]{1, 2, 1, 2, 1, 1, 1, 1, 1};
        Object numSearch = 2;
        ParallelFindIndex<Object> parallelFindIndex =
                new ParallelFindIndex<>(nums, numSearch, 0, nums.length - 1);
        parallelFindIndex.find(nums, numSearch);
        assertEquals(1, parallelFindIndex.find(nums, numSearch));
    }

    @Test
    public void whenObjectOfNumsMoreThan10FindLinenMaxIndexOf1ResultIs11() {
        Object[] nums = new Object[]{1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 2};
        Object numSearch = 1;
        ParallelFindIndex<Object> parallelFindIndex =
                new ParallelFindIndex<>(nums, numSearch, 0, nums.length - 1);
        assertEquals(11, parallelFindIndex.find(nums, numSearch));
    }

    @Test
    public void whenObjectOfSymbolsMore10FindLinenFirtsIndexOfCharBResultIs13() {
        Object[] chars = new Object[]{'a', 'h', 'c', 'd', 's', 'i', 'f', 'e', 'j', 'z', 'g', 'v', 'c', 'b'};
        Object numSearch = 'b';
        ParallelFindIndex<Object> parallelFindIndex =
                new ParallelFindIndex<>(chars, numSearch, 0, chars.length - 1);
        assertEquals(13, parallelFindIndex.find(chars, numSearch));
    }
}