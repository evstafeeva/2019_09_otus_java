package ru.otus;


import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.model.AddressDataSet;
import ru.otus.hibernate.model.PhoneDataSet;
import ru.otus.hibernate.model.User;
import ru.otus.hibernate.service.user.DBServiceUser;
import ru.otus.hibernate.service.user.DbServiceUserImpl;
import ru.otus.hibernate.sessionmanager.HibernateUtils;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CacheTest {

    private SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManager;
    private UserDaoHibernate userDao;
    private DBServiceUser dbServiceUser;

    @BeforeEach
    public void settings() {
        sessionFactory = HibernateUtils.buildSessionFactory("hibernate-test.cfg.xml",
                PhoneDataSet.class,
                AddressDataSet.class,
                User.class);

        sessionManager = new SessionManagerHibernate(sessionFactory);
        userDao = new UserDaoHibernate(sessionManager);
        dbServiceUser = new DbServiceUserImpl(userDao);
    }

    @AfterEach
    public void clear() {
        sessionManager.close();
        sessionFactory.close();
    }

    @Test
    public void getTest() {
        User user = new User("Anna", 25, new AddressDataSet("улицв 1"),
                Arrays.asList(new PhoneDataSet("12345"), new PhoneDataSet("1234567")));

        //сохраняем впервые
        long id = dbServiceUser.saveUser(user);
        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);
        System.out.println(mayBeCreatedUser);

        //берем из кэша
        User newUser = dbServiceUser.getUser(id).get();
        System.out.println(newUser);
    }

    @Test
    public void gcTest() {
        List<Integer> numbers = new ArrayList<>();
        //добавляем 150 user
        for(int i = 0; i < 150; i ++){
            numbers.add((int) dbServiceUser.saveUser(new User(String.valueOf(i) , i, new AddressDataSet(String.valueOf(i)), Arrays.asList(new PhoneDataSet(String.valueOf(i))))));
        }
        System.out.println("делвем геты");
        for(Integer id : numbers){
            System.out.println(dbServiceUser.getUser(id).get());
        }
        System.out.println("чистим кэш");
        System.gc();
        System.out.println("делвем геты");
        for(Integer id : numbers){
            System.out.println(dbServiceUser.getUser(id).get());
        }
    }

    @Test
    public void updateTest() {
        List<Integer> numbers = new ArrayList<>();
        //добавляем 50 user
        for(int i = 0; i < 50; i ++){
            numbers.add((int) dbServiceUser.saveUser(new User(String.valueOf(i) , i, new AddressDataSet(String.valueOf(i)), Arrays.asList(new PhoneDataSet(String.valueOf(i))))));
        }
        System.out.println("делвем геты 1");
        for(Integer id : numbers){
            System.out.println(dbServiceUser.getUser(id).get());
        }
        System.out.println("делаем update");
        for(Integer id : numbers){
            User user = dbServiceUser.getUser(id).get();
            user.setAge(user.getAge()*10);
            dbServiceUser.updateUser(user);
        }
        System.out.println("делвем геты 2");
        for(Integer id : numbers){
            System.out.println(dbServiceUser.getUser(id).get());
        }
        System.out.println("чистим кэш");
        System.gc();
        System.out.println("делаем геты 3");
        for(Integer id : numbers){
            System.out.println(dbServiceUser.getUser(id).get());
        }
    }
}
