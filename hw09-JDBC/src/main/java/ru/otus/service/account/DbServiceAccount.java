package ru.otus.service.account;

import ru.otus.model.Account;

import java.util.Optional;

public interface DbServiceAccount {

    void saveAccount(Account account);
    void updateAccount(Account account);
    Optional<Account> getAccount(long id);
    void saveOrUpdateAccount(Account account);
}
