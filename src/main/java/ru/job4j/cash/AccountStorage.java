package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.getId(), account) == account;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.getId(), accounts.get(account.getId()), account);
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id, accounts.get(id));
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        Account accountFrom = getById(fromId).orElseThrow();
        Account accountTo = getById(toId).orElseThrow();
        int amountFrom = accountFrom.amount();
        int amountTo = accountTo.amount();
        accountFrom.setAmount(amountFrom - amount);
        accountTo.setAmount(amountTo + amount);
        return true;
    }
}
