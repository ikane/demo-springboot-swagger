package org.ikane.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Lists.*;
import static springfox.documentation.schema.AlternateTypeRules.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Autowired
	private TypeResolver typeResolver;
	
	 private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("Demo springboot API")
	                .description("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum " +
	                        "has been the industry's standard dummy text ever since the 1500s, when an unknown printer "
	                        + "took a " +
	                        "galley of type and scrambled it to make a type specimen book. It has survived not only five " +
	                        "centuries, but also the leap into electronic typesetting, remaining essentially unchanged. " +
	                        "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum " +
	                        "passages, and more recently with desktop publishing software like Aldus PageMaker including " +
	                        "versions of Lorem Ipsum.")
	                .termsOfServiceUrl("http://springfox.io")
	                .contact(new Contact("springfox", "url", "email"))
	                .license("Apache License Version 2.0")
	                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
	                .version("2.0")
	                .build();
	    }
	 
	@Bean
	public Docket myApi() {
		/*
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("full-test-api")
				.apiInfo(apiInfo())
				.select()
                .paths(myPAths())
                .build()
				;
				*/
		/*
		return new Docket(DocumentationType.SWAGGER_2)
		        .select()
		          //.apis(RequestHandlerSelectors.any())
		          .apis(RequestHandlerSelectors.basePackage("org.ikane.api"))
		          .paths(PathSelectors.any())
		          .build()
		        .pathMapping("/")
		        .directModelSubstitute(LocalDate.class,
		            String.class)
		        .genericModelSubstitutes(ResponseEntity.class)
		        .alternateTypeRules(
		            newRule(typeResolver.resolve(DeferredResult.class,
		                    typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
		                typeResolver.resolve(WildcardType.class)))
		        .useDefaultResponseMessages(false)
		        .globalResponseMessage(RequestMethod.GET,
		            newArrayList(new ResponseMessageBuilder()
		                .code(500)
		                .message("500 message")
		                .responseModel(new ModelRef("Error"))
		                .build()))
		        .securitySchemes(newArrayList(apiKey()))
		        .securityContexts(newArrayList(securityContext()))
		        .enableUrlTemplating(true)
		        .globalOperationParameters(
		            newArrayList(new ParameterBuilder()
		                .name("someGlobalParameter")
		                .description("Description of someGlobalParameter")
		                .modelRef(new ModelRef("string"))
		                .parameterType("query")
		                .required(true)
		                .build()))
		        .tags(new Tag("Pet Service", "All apis relating to pets")) 
		        //.additionalModels(typeResolver.resolve(AdditionalModel.class)) 
		        ;
		        */
		 return new Docket(DocumentationType.SWAGGER_2)
	                .select()
		                .apis(RequestHandlerSelectors.basePackage("org.ikane.api"))
		                .paths(PathSelectors.any())
		                .build()
	                .pathMapping("/");
	}

	private Predicate<String> myPAths() {
		// TODO Auto-generated method stub
		return PathSelectors.any(); // regex("/category.*");
	}
	
	 private ApiKey apiKey() {
		    return new ApiKey("mykey", "api_key", "header");
		  }

		  private SecurityContext securityContext() {
		    return SecurityContext.builder()
		        .securityReferences(defaultAuth())
		        .forPaths(PathSelectors.regex("/anyPath.*"))
		        .build();
		  }

		  List<SecurityReference> defaultAuth() {
		    AuthorizationScope authorizationScope
		        = new AuthorizationScope("global", "accessEverything");
		    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		    authorizationScopes[0] = authorizationScope;
		    return newArrayList(
		        new SecurityReference("mykey", authorizationScopes));
		  }

		  @Bean
		  SecurityConfiguration security() {
		    return new SecurityConfiguration(
		        "test-app-client-id",
		        "test-app-client-secret",
		        "test-app-realm",
		        "test-app",
		        "apiKey",
		        ApiKeyVehicle.HEADER, 
		        "api_key", 
		        "," /*scope separator*/);
		  }

		  @Bean
		  UiConfiguration uiConfig() {
		    return new UiConfiguration(
		        "validatorUrl",// url
		        "none",       // docExpansion          => none | list
		        "alpha",      // apiSorter             => alpha
		        "schema",     // defaultModelRendering => schema
		        UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
		        false,        // enableJsonEditor      => true | false
		        true         // showRequestHeaders    => true | false
		        /*60000L*/
		        );      // requestTimeout => in milliseconds, defaults to null (uses jquery xh timeout)
		  }
}
