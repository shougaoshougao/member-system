package cn.bmaster.member;

import com.fasterxml.classmate.TypeResolver;
import com.renaissance.core.web.resolver.Secure;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static springfox.documentation.schema.AlternateTypeRules.newRule;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.ryhr.admin.web.controller";

    @Autowired
    private TypeResolver typeResolver;

    @Value("${springfox.documentation.swagger.host:}")
    private String host;

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("会员系统接口文档")
                .description("虽然我丑，但是实用")
                .version("1.0.0")
                .build();
    }

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .build()
                .alternateTypeRules(newRule(typeResolver.resolve(Pageable.class), typeResolver.resolve(pageableMixin())))
                .ignoredParameterTypes(Secure.class)
                .useDefaultResponseMessages(false)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(YearMonth.class, String.class)
                .produces(produces())
                .globalOperationParameters(globalOperationParameters())
                .host(StringUtils.isBlank(host) ? null : host)
                .apiInfo(apiInfo());
    }

    private List<Parameter> globalOperationParameters() {
        List<Parameter> params = new ArrayList<>();
        params.add(new ParameterBuilder()
                .name("Authorization")
                .description("Token认证")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("Bearer dGVzdDE=") // test1的token
                .build());
        return params;
    }

    private Set<String> produces() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json");
        return produces;
    }

    private Type pageableMixin() {
        List<AlternateTypePropertyBuilder> properties = new ArrayList<>();
        properties.add(property(Integer.class, "page"));
        properties.add(property(Integer.class, "size"));
        properties.add(property(String.class, "sort"));
        return new AlternateTypeBuilder()
                .fullyQualifiedClassName(String.format("%s.generated.%s", Pageable.class.getPackage().getName(), Pageable.class.getSimpleName()))
                .withProperties(properties)
                .build();
    }

    private AlternateTypePropertyBuilder property(Class<?> type, String name) {
        return new AlternateTypePropertyBuilder()
                .withName(name)
                .withType(type)
                .withCanRead(true)
                .withCanWrite(true);
    }
}
