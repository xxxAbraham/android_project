package iut.nantes.serverMobile.application.config;

import iut.nantes.serverMobile.domain.UserService;
import iut.nantes.serverMobile.infra.UserDao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Djurdjevic
 */
@Configuration
public class Config {

    @Bean
    public UserService getUserService() {
        return new UserService();
    }

    @Bean
    public UserDao getUserDao() {
        return new UserDao();
    }

}
