package ru.otus.service.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dao.GenericDao;
import ru.otus.model.Account;
import ru.otus.service.DbServiceException;
import ru.otus.service.user.DbServiceUserImpl;
import ru.otus.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceAccountImpl implements DbServiceAccount{
    private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);
    private final GenericDao<Account> accountDao;

    public DbServiceAccountImpl(GenericDao<Account> accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void saveAccount(Account account) {
        try (SessionManager sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = accountDao.save(account);
                sessionManager.commitSession();
                logger.info("created user: {}", userId);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public void updateAccount(Account account) {
        try (SessionManager sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = accountDao.update(account);
                sessionManager.commitSession();
                logger.info("update user: {}", userId);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }


    @Override
    public Optional<Account> getAccount(long id) {
        try (SessionManager sessionManager = accountDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<Account> accountOptional = accountDao.findById(id, Account.class);
                logger.info("user: {}", accountOptional.orElse(null));
                return accountOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public void saveOrUpdateAccount(Account account) {
        if(getAccount(account.getNo()).isEmpty()){
            saveAccount(account);
            return;
        }
        else {
            updateAccount(account);
            return;
        }
    }
}
