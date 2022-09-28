package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParallelFindIndexTest {

    @Test
    public void whenObjectOfIntegersFindIndexOf4ResultIs6() {
        Integer[] nums = new Integer[]{1, 2, 3, 56, 76, 25, 4, 98, 324, 453, 23, 111, 47, 24, 53};
        int numSearch = 4;
        assertEquals(6, ParallelFindIndex.find(nums, numSearch));
    }

    @Test
    public void whenObjectOfIntegersFindIndexOf53ResultIs14() {
        Integer[] nums = {1, 2, 3, 56, 76, 25, 4, 98, 324, 453, 23, 111, 47, 24, 53};
        int numSearch = 53;
        assertEquals(14, ParallelFindIndex.find(nums, numSearch));
    }

    @Test
    public void whenObjectOfIntegersFindIndexOf2ResultIs1() {
        Integer[] nums = {1, 2, 3, 56, 76, 25, 4, 98, 324, 453, 23, 111, 47, 24, 53};
        int numSearch = 2;
        assertEquals(1, ParallelFindIndex.find(nums, numSearch));
    }

    @Test
    public void whenObjectOfIntegersFindIndexOf1ResultIs0() {
        Integer[] nums = {1, 2, 3, 56, 76, 25, 4, 98, 324, 453, 23, 111, 47, 24, 53};
        int numSearch = 1;
        assertEquals(0, ParallelFindIndex.find(nums, numSearch));
    }

    @Test
    public void whenObjectOfSymbolsFindIndexOfCharBResultIs13() {
        Character[] chars = {'a', 'h', 'c', 'd', 's', 'i', 'f', 'e', 'j', 'z', 'g', 'v', 'm', 'b'};
        Character numSearch = 'b';
        assertEquals(13, ParallelFindIndex.find(chars, numSearch));
    }
}
