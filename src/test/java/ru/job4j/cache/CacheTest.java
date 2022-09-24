package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @Test
    void whenAddToCache() {
        Cache cache = new Cache();
        Base model = new Base(1, 1);
        assertTrue(cache.add(model));
    }

    @Test
    void whenDeleteFromCache() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        Base model2 = new Base(2, 2);
        cache.add(model1);
        cache.add(model2);
        cache.delete(model1);
        assertTrue(cache.getAll().containsKey(model2.getId()));
        assertFalse(cache.getAll().containsKey(model1.getId()));
    }

    @Test
    void whenAddToCacheWithDifferentVersion() throws OptimisticException {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        Base model2 = new Base(1, 2);
    }

    @Test
    void whenUpdateBaseInCacheAndChangedVersion() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        model1.setName("Model 1");
        cache.add(model1);
        Base model2 = new Base(1, 1);
        model2.setName("Model 2");
        cache.update(model2);
        assertThat(cache.get(model2).getVersion()).isEqualTo(2);
    }

    @Test
    void whenUpdateBseInCacheWithChangedNameAndVersion() {
        Cache cache = new Cache();
        Base model1 = new Base(1, 1);
        model1.setName("Model 1");
        cache.add(model1);
        Base model2 = new Base(1, 1);
        model2.setName("Model 2");
        cache.update(model2);
        assertThat(cache.get(model2).getVersion()).isEqualTo(2);
        assertThat(cache.get(model2).getName()).isEqualTo("Model 2");
    }
}