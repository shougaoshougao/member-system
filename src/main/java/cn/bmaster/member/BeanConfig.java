package cn.bmaster.member;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.renaissance.core.handler.FileParseHandler;
import com.renaissance.core.handler.QiniuHandler;
import com.renaissance.core.handler.ValidationCodeHandler;
import com.renaissance.core.handler.parser.impl.XlsFileParser;
import com.renaissance.core.handler.parser.impl.XlsxFileParser;
import com.renaissance.core.handler.sms.DahanSmsHandler;
import okhttp3.OkHttpClient;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.concurrent.TimeUnit;

@Configuration
public class BeanConfig {

    @Bean
    public Validator validator (final AutowireCapableBeanFactory autowireCapableBeanFactory) {

        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure().constraintValidatorFactory(new SpringConstraintValidatorFactory(autowireCapableBeanFactory))
                .buildValidatorFactory();

        return validatorFactory.getValidator();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.followRedirects(true);
        return builder.build();
    }

    @Bean
    public QiniuHandler qiniuHandler() {
        return new QiniuHandler();
    }

    @Bean
    public ValidationCodeHandler validationCodeHandler() {
        return new ValidationCodeHandler();
    }

    @Bean
    public DahanSmsHandler dahanSmsHandler() {
        return new DahanSmsHandler();
    }

    @Bean
    public XlsFileParser xlsFileParser() {
        return new XlsFileParser();
    }

    @Bean
    public XlsxFileParser xlsxFileParser() {
        return new XlsxFileParser();
    }

    @Bean
    public FileParseHandler fileParseHandler() {
        return new FileParseHandler();
    }

}
