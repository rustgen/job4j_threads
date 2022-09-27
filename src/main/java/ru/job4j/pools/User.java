package ru.job4j.pools;

import java.util.Objects;

public class User {
    private static int num;
    private final String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public User[] crateRandomUsers(int num) {
        User[] users = new User[num];
        for (int i = 0; i < num; i++) {
            users[i] = new User("name");
        }
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
