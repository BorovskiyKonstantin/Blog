package main.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
class ApplicationConfiguration implements WebMvcConfigurer {
    @Value("${upload.dir}")
    private String uploadRootDirPath;

    // Возвращать index.html при любых запросах с ответом 404 вместо whitelabel
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/notFound").setViewName("forward:/");
    }

    @Bean
    WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> servletContainerCustomizer() {
        return factory -> factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/notFound"));
    }

}
