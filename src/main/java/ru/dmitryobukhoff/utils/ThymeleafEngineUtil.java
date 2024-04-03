package ru.dmitryobukhoff.utils;

import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDateTime;

@Slf4j
@WebListener(value = "/*")
public class ThymeleafEngineUtil implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        TemplateEngine templateEngine = new TemplateEngine();

        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContextEvent.getServletContext());
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(templateResolver);
        servletContextEvent.getServletContext().setAttribute("templateEngine", templateEngine);

        log.info(LocalDateTime.now() + "Template Engine is created");
    }
}
