package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), ((integer, base) -> {
            if (model.getVersion() != base.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
                    Base updated = new Base(integer, base.getVersion() + 1);
                    if (!model.getName().equals(base.getName())) {
                        updated.setName(model.getName());
                    }
            return updated;
        })
        ) == model;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public Map<Integer, Base> getAll() {
        return new ConcurrentHashMap<>(memory);
    }

    public Base get(Base model) {
        return memory.get(model.getId());
    }
}
