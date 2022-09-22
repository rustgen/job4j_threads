package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.getId(), account) == account;
    }

    public synchronized boolean update(Account account) {
        return accounts.computeIfPresent(account.getId(), (integer, acc) -> account) == account;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id, accounts.get(id));
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        Account accountFrom = getById(fromId)
                .orElseThrow(() -> new NullPointerException("There is no account wth such id. Put correct id."));
        Account accountTo = getById(toId)
                .orElseThrow(() -> new NullPointerException("There is no account wth such id. Put correct id."));
        int amountFrom = accountFrom.amount();
        int amountTo = accountTo.amount();
        if (amountFrom < amount) {
            return false;
        }
        accountFrom.setAmount(amountFrom - amount);
        accountTo.setAmount(amountTo + amount);
        return true;
    }
}
