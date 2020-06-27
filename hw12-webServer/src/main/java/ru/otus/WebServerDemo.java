package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.otus.database.dao.Dao;
import ru.otus.database.dao.UserDaoHibernate;
import ru.otus.database.sessionmanager.SessionManager;
import ru.otus.database.sessionmanager.hibernate.HibernateUtils;
import ru.otus.database.sessionmanager.hibernate.SessionManagerHibernate;
import ru.otus.server.UsersWebServer;
import ru.otus.server.UsersWebServerImpl;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.services.UserAuthService;
import ru.otus.services.UserAuthServiceImpl;
import ru.otus.database.model.*;

import java.util.Arrays;
import java.util.Optional;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml",
                User.class,
                PhoneDataSet.class,
                AddressDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        Dao<User> userDao = new UserDaoHibernate(sessionManager);

        sessionManager.beginSession();
        for(int i = 0; i < 5; i ++){
            userDao.save(new User(String.valueOf(i) , i, "1"+i, "1"+i,
                    new AddressDataSet(String.valueOf(i)), Arrays.asList(new PhoneDataSet(String.valueOf(i)))));
        }
        sessionManager.commitSession();

        /*SessionManager sM = userDao.getSessionManager();
        sM.beginSession();
        Session currentSession = sessionManager.getCurrentSession().getHibernateSession();
        try {
            System.out.println(currentSession.find(User.class, "login_1"));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userDao);

        UsersWebServer usersWebServer = new UsersWebServerImpl(WEB_SERVER_PORT,
                authService, userDao, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }
}
