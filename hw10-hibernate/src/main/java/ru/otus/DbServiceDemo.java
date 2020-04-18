package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dao.UserDaoHibernate;

import ru.otus.model.*;
import ru.otus.service.user.DBServiceUser;
import ru.otus.service.user.DbServiceUserImpl;
import ru.otus.sessionmanager.hibernate.HibernateUtils;
import ru.otus.sessionmanager.hibernate.SessionManagerHibernate;

import java.util.Arrays;
import java.util.Optional;


public class DbServiceDemo {
    private static Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class,
                PhoneDataSet.class,
                AddressDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDaoHibernate userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        User user = new User("Anna", 25, new AddressDataSet("улицв 1"),
                Arrays.asList(new PhoneDataSet("12345"), new PhoneDataSet("1234567")));

        long id = dbServiceUser.saveUser(user);
        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);
        System.out.println(mayBeCreatedUser);

        user.setAge(90);
        dbServiceUser.updateUser(user);
        mayBeCreatedUser = dbServiceUser.getUser(id);
        System.out.println(mayBeCreatedUser);

        dbServiceUser.saveUser(user);
        mayBeCreatedUser = dbServiceUser.getUser(id);
        System.out.println(mayBeCreatedUser);


        User user2 = new User("Anna", 25, new AddressDataSet("улицв 1"),
                Arrays.asList(new PhoneDataSet("12345"), new PhoneDataSet("1234567")));

        long id2 = dbServiceUser.saveUser(user2);
        Optional<User> mayBeCreatedUser2 = dbServiceUser.getUser(id2);
        System.out.println(mayBeCreatedUser2);
    }
}
