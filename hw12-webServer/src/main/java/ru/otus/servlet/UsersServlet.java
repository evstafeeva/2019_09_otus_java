package ru.otus.servlet;

import ru.otus.database.dao.Dao;
import ru.otus.database.model.User;
import ru.otus.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class UsersServlet extends HttpServlet {

  private static final String USERS_PAGE_TEMPLATE = "users.html";
  //ключ для шаблона
  private static final String TEMPLATE_ATTR_RANDOM_USER = "randomUser";

  private final Dao<User> userDao;
  private final TemplateProcessor templateProcessor;

  public UsersServlet(TemplateProcessor templateProcessor, Dao<User> userDao) {
    this.templateProcessor = templateProcessor;
    this.userDao = userDao;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
    //параметры
    Map<String, Object> paramsMap = new HashMap<>();
    //ищем случанйного пользователя, и кладем в мапу под ключом для шаблона
    userDao.findRandom().ifPresent(randomUser -> paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, randomUser));

    response.setContentType("text/html");
    response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
  }

}
