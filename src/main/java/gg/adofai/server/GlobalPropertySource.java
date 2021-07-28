package gg.adofai.server;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource(value = "file:./config/config.properties", ignoreResourceNotFound = true), // developer pc
        @PropertySource(value = "file:${user.home}/env/config.properties", ignoreResourceNotFound = true), // server
})
@Getter
public class GlobalPropertySource {

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

}
