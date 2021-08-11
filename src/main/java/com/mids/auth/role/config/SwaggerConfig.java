package com.mids.auth.role.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String TERMS_OF_SERVICE_URL = "https://auth.com/terms-of-use/";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_DESCRIPTION = "Authorization Token";
    private static final String HEADER = "header";
    private static final String STRING = "string";

    @Bean
    public Docket productApi(TypeResolver typeResolver) {
        Contact contact = new Contact(
                "Auth roles and permission",
                "https://auth.com",
                "developers@auth.com"
        );

        ApiInfo apiInfo = new ApiInfo(
                "Auth roles and permission",
                "The Auth roles and permission api provides end points " +
                        "to communicate to the Auth roles and permission backend system." +
                        " The Auth api has the secured access and must be used with" +
                        " explicit permissions of authorized person.",
                "1.0.0",
                TERMS_OF_SERVICE_URL,
                contact,
                "Auth roles and permission",
                "https://auth.com/about-us",
                Collections.emptyList()
        );

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mids.auth.role"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo)
                .globalOperationParameters(Lists.newArrayList(
                        new ParameterBuilder()
                                .name(AUTHORIZATION_HEADER)
                                .description(AUTHORIZATION_HEADER_DESCRIPTION)
                                .modelRef(new ModelRef(STRING))
                                .parameterType(HEADER)
                                .required(true)
                                .build()))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, defaultGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, defaultPostPutDeleteResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, defaultPostPutDeleteResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, defaultPostPutDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Error.class));
    }

    private List<ResponseMessage> defaultGetResponseMessages() {
        return Lists.newArrayList(
                new ResponseMessageBuilder().code(401).message("Token Invalid").responseModel(errorModelRef()).build(),
                new ResponseMessageBuilder().code(500).message("Internal Server Error").responseModel(errorModelRef()).build()
        );
    }

    private List<ResponseMessage> defaultPostPutDeleteResponseMessages() {
        return Lists.newArrayList(
                new ResponseMessageBuilder().code(400).message("Input invalid or Bad Request").responseModel(errorModelRef()).build(),
                new ResponseMessageBuilder().code(401).message("Token Invalid").responseModel(errorModelRef()).build(),
                new ResponseMessageBuilder().code(500).message("Internal Server Error").responseModel(errorModelRef()).build()
        );
    }

    private ModelRef errorModelRef() {
        return new ModelRef("Error");
    }
}
