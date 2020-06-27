package ru.otus.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class TemplateProcessorImpl implements TemplateProcessor {

  private final Configuration configuration;

  public TemplateProcessorImpl(String templatesDir) throws IOException {
    configuration = new Configuration(Configuration.VERSION_2_3_28);
    //configuration.setDirectoryForTemplateLoading(new File(templatesDir));  // for directory
    //шаблоны берем из ресурсов, где их и задаем
    configuration.setClassForTemplateLoading(this.getClass(), templatesDir); // for resource
    configuration.setDefaultEncoding("UTF-8");
  }

  @Override
  public String getPage(String filename, Map<String, Object> data) throws IOException {
    try (Writer stream = new StringWriter();) {
      Template template = configuration.getTemplate(filename);
      //связали шаблон с данными
      template.process(data, stream);
      return stream.toString();
    } catch (TemplateException e) {
      throw new IOException(e);
    }
  }
}
