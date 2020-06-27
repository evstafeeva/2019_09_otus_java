package ru.otus.servlet;

import ru.otus.services.TemplateProcessor;
import ru.otus.services.UserAuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;

import static javax.servlet.http.HttpServletResponse.*;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class LoginServlet extends HttpServlet {

  private static final String PARAM_LOGIN = "login";
  private static final String PARAM_PASSWORD = "password";
  private static final int MAX_INACTIVE_INTERVAL = 30;
  private static final String LOGIN_PAGE_TEMPLATE = "login.html";


  private final TemplateProcessor templateProcessor;
  private final UserAuthService userAuthService;

  public LoginServlet(TemplateProcessor templateProcessor, UserAuthService userAuthService) {
    this.userAuthService = userAuthService;
    this.templateProcessor = templateProcessor;
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //просто из шаблона дает странцу логинов
    response.setContentType("text/html");
    response.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, Collections.emptyMap()));
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //просто отвечаем на утентификация
    String name = request.getParameter(PARAM_LOGIN);
    String password = request.getParameter(PARAM_PASSWORD);

    if (userAuthService.authenticate(name, password)) {
      //если сесси нет, создай
      HttpSession session = request.getSession();
      //сколько живет
      session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
      //отправляем на страницу пользователя
      response.sendRedirect("/users");
    } else {
      //или 401
      response.setStatus(SC_UNAUTHORIZED);
    }

  }

}
