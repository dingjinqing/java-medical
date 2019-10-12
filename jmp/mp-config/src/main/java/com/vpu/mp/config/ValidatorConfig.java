package com.vpu.mp.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author liufei
 * @date 10/12/2019
 */
@Configuration
public class ValidatorConfig {
    private static final String HIBERNATE_VALIDATOR_FAIL_FAST = "hibernate.validator.fail_fast";

    @Value(value = "${hibernate.validator.fail_fast:false}")
    private String isFailFast;

    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation
            .byProvider(HibernateValidator.class)
            .configure()
            .addProperty(HIBERNATE_VALIDATOR_FAIL_FAST, isFailFast)
            .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator());
        return methodValidationPostProcessor;
    }
}
