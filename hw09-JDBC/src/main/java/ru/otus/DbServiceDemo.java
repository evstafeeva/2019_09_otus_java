package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dao.GenericDao;
import ru.otus.executor.DbExecutor;
import ru.otus.model.Account;
import ru.otus.model.User;
import ru.otus.service.account.DbServiceAccount;
import ru.otus.service.account.DbServiceAccountImpl;
import ru.otus.service.user.DBServiceUser;
import ru.otus.service.user.DbServiceUserImpl;
import ru.otus.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        DataSource dataSource = new DataSourceH2();
        DbServiceDemo demo = new DbServiceDemo();

        demo.createTable(dataSource);

        SessionManagerJdbc sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutor<User> dbExecutor = new DbExecutor<>();
        GenericDao<User> userDao = new GenericDao<User>(sessionManager, dbExecutor);

        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);
        User user = new User(5, "Anna", 25);
        dbServiceUser.saveUser(user);
        user.setName("Nataly");
        dbServiceUser.updateUser(user);
        System.out.println(dbServiceUser.getUser(5));
        User user1 = new User(9, "Alen", 19);
        dbServiceUser.saveOrUpdateUser(user1);
        System.out.println(dbServiceUser.getUser(9));


        System.out.println("----------------------------");
        SessionManagerJdbc accountSessionManager = new SessionManagerJdbc(dataSource);
        DbExecutor<Account> accountDbExecutor = new DbExecutor<>();
        GenericDao<Account> accountDao = new GenericDao<Account>(sessionManager, accountDbExecutor);

        DbServiceAccount accountDbService = new DbServiceAccountImpl(accountDao);
        //создаю новый акк и сохраняю в бд
        Account account1 = new Account(6, "credit", 5000);
        accountDbService.saveAccount(account1);
        //запрашивю, чтобы проверить
        System.out.println(accountDbService.getAccount(6));
        //меняю и обновляю
        account1.setRest(6000);
        accountDbService.updateAccount(account1);
        System.out.println(accountDbService.getAccount(6));

        System.out.println("----------------------");
        //создаю новый акк
        Account account2 = new Account(8, "debit", 500);
        //проверяю на наличие и добавляю или обновляю
        accountDbService.saveOrUpdateAccount(account2);
        //проверяю, оба ли в базе
        System.out.println(accountDbService.getAccount(6));
        System.out.println(accountDbService.getAccount(8));

        //создаю или обновляю акк1
        account1.setRest(600);
        accountDbService.saveOrUpdateAccount(account1);
        System.out.println(accountDbService.getAccount(6));


    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(
                     "create table user(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement(
                     "create table account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("tables created");
    }
}
