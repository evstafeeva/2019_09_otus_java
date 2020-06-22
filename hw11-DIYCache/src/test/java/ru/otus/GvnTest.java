package ru.otus;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

public class GvnTest {

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

    @Test
    public void rublicTest(){
        Session session = sessionFactory.openSession();
    Transaction transaction = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users_phones").executeUpdate();
    //session.createSQLQuery("ALTER TABLE phones DROP CONSTRAINT FK41UJ8H5Y1SLFK9LKFA3783VCU").executeUpdate();
        session.createSQLQuery("DROP TABLE IF EXISTS phones").executeUpdate();
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
        session.createSQLQuery("DROP TABLE IF EXISTS addresses").executeUpdate();
        transaction.commit();
}
}
