package virtus.lab.workshops.KotlinWorkshops.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

@Configuration
public class AppConfiguration {

    private final MessageSource messageSource;

    @Autowired
    public AppConfiguration(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
        return new MessageSourceAccessor(messageSource);
    }
}
