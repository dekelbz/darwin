package com.dekel.darwin.users;

import com.dekel.darwin.users.domain.UserDTO;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.BytesJsonMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.thavam.util.concurrent.blockingMap.BlockingHashMap;
import org.thavam.util.concurrent.blockingMap.BlockingMap;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public LocalValidatorFactoryBean getValidator(MessageSource messageSource) {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setValidationMessageSource(messageSource);
        return factoryBean;
    }

    @Bean
    public BlockingMap<String, UserDTO> usersToConsume() {
        return new BlockingHashMap<>();
    }

    @Bean
    public BytesJsonMessageConverter jsonMessageConverter() {
        return new BytesJsonMessageConverter();
    }
}
