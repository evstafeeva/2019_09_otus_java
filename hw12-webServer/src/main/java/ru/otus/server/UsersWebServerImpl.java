package ru.otus.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.database.dao.Dao;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.database.model.User;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.UserAuthService;
import ru.otus.servlet.AuthorizationFilter;
import ru.otus.servlet.LoginServlet;
import ru.otus.servlet.UsersApiServlet;
import ru.otus.servlet.UsersServlet;

import java.util.Arrays;

public class UsersWebServerImpl implements UsersWebServer {
  private static final String START_PAGE_NAME = "index.html";
  private static final String COMMON_RESOURCES_DIR = "static";

  private final Dao<User> userDao;
  private final Gson gson;
  protected final TemplateProcessor templateProcessor;
  private final Server server;
  private final UserAuthService authService;

  public UsersWebServerImpl(int port, UserAuthService authService, Dao<User> userDao, Gson gson, TemplateProcessor templateProcessor) {
    this.userDao = userDao;
    this.gson = gson;
    this.templateProcessor = templateProcessor;
    //создаем объект server из JETTY, который расширяем с помощью своих сервлетов
    server = new Server(port);
    this.authService = authService;
  }

  @Override
  public void start() throws Exception {
    //если не было настройки, настраиваем
    if (server.getHandlers().length == 0) {
      initContext();
    }
    //иначе сразу пускаем
    server.start();
  }

  @Override
  public void join() throws Exception {
    server.join();
  }

  @Override
  public void stop() throws Exception {
    server.stop();
  }

  private Server initContext() {

    ResourceHandler resourceHandler = createResourceHandler();
    ServletContextHandler servletContextHandler = createServletContextHandler();

    //порядок имеет значение!!!
    HandlerList handlers = new HandlerList();
    //сначала ресурсный
    handlers.addHandler(resourceHandler);
    //тут контекстный, но на нем вызываем applySecurity чтобы защитить соответственные url
    handlers.addHandler(applySecurity(servletContextHandler, "/users", "/api/user/*"));

    //назначаем серверу.
    server.setHandler(handlers);
    return server;
  }

  protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
    //добавляем логин севрлет
    servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
    //+фильтр на все url, которые передают в параметры
    AuthorizationFilter authorizationFilter = new AuthorizationFilter();
    Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
    return servletContextHandler;
  }

  private ResourceHandler createResourceHandler() {
    ResourceHandler resourceHandler = new ResourceHandler();
    //не показываем содержимое папки при переходе в нее
    resourceHandler.setDirectoriesListed(false);
    //задали стартовую страницу
    resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
    //сказали, где будут лежать ресурсы
    resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
    return resourceHandler;
  }

  private ServletContextHandler createServletContextHandler() {
    ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    //так как у мервлетов есть зависимости, используем servletHolder
    //мапится на путь |users
    servletContextHandler.addServlet(new ServletHolder(new UsersServlet(templateProcessor, userDao)), "/users");
    //для RAST.
    //приходят все запросы, которые начинаются на api/user
    servletContextHandler.addServlet(new ServletHolder(new UsersApiServlet(userDao, gson)), "/api/user/*");
    return servletContextHandler;
  }
}
