package pl.swapmed.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /*registry.addViewController("/login").setViewName("authentication/login");
        registry.addViewController("/logout").setViewName("authentication/logout");
        registry.addViewController("/403").setViewName("authentication/403");
    }

         */
    }
}
