package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.getId(), account) == account;
    }

    public synchronized boolean update(Account account) {
        if (getById(account.getId()).isEmpty()) {
            throw new IllegalArgumentException(String.format(
                    "There is no such account - \"%s\" with same id among accounts.", account));
        }
        return accounts.replace(account.getId(), accounts.get(account.getId()), account);
    }

    public synchronized boolean delete(int id) {
        if (getById(id).isEmpty()) {
            throw new IllegalArgumentException(String.format("There is no such id = %s among accounts.", id));
        }
        return accounts.remove(id, accounts.get(id));
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        if (getById(fromId).isEmpty()) {
            throw new IllegalArgumentException(String.format("There is no account with such id = %s number.", fromId));
        }
        if (getById(toId).isEmpty()) {
            throw new IllegalArgumentException(String.format("There is no account with such id = %s number.", toId));
        }
        if (accounts.get(fromId).amount() < amount) {
            throw new IllegalArgumentException(
                    String.format(
                            "The amount on this account id = %s can't be less than the amount you want to transfer.",
                            fromId));
        }
        if (accounts.get(fromId).amount() < amount) {
            throw new IllegalArgumentException(
                    String.format(
                            "The amount on this account id = %s can't be less than the amount you want to transfer.",
                            toId));
        }
        int moneyFrom = getById(fromId).get().amount();
        int moneyTo = getById(toId).get().amount();
        if (moneyFrom < amount) {
            throw new IllegalArgumentException(String.format(
                    "Amount of the account for transfer can't be less, than transfer sum (%s < transfer sum).", moneyFrom
            ));
        }
        Account accountFrom = getById(fromId).get();
        Account accountTo = getById(toId).get();
        accountFrom.setAmount(accountFrom.amount() - amount);
        accountTo.setAmount(accountTo.amount() + amount);
        return true;
    }
}
