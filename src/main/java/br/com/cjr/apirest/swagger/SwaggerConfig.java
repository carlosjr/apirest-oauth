package br.com.cjr.apirest.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import br.com.cjr.apirest.model.Usuario;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
	
	private static final String BASE_PACKAGE = "br.com.luizalabs.foodsolution";
	private static final String BASE_URL = "food-solutions.softbox.com";
	private static final String TITLE = "Food Solutions Unilever";
	private static final String DESCRIPTION = "Food Solutions Unilever - REST API";
	private static final String VERSION = "1.0.0";

  @Bean
  public Docket foodsApi() {
    return new Docket(DocumentationType.SWAGGER_2)
    	.host(BASE_URL)
    	.pathProvider(new ExtendRelativePathProvider())
        .select()
        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))        
        .build()
        .ignoredParameterTypes(Usuario.class)
        .globalOperationParameters(
                Arrays.asList(
                        new ParameterBuilder()
                            .name("Authorization")
                            .description("Header para Token JWT")
                            .modelRef(new ModelRef("string"))
                            .parameterType("header")
                            .required(false)
                            .build()))
        .apiInfo(metaData());
  }  
  
  private ApiInfo metaData() {
    return new ApiInfoBuilder()
        .title(TITLE)
        .description(DESCRIPTION)
        .version(VERSION)
        .build();
  }

  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
  
  public class ExtendRelativePathProvider extends AbstractPathProvider{ 
	  
	  public static final String ROOT = "/";  
	  private static final String BASE_PATH = "/foods";
	  
	  @Override
	  protected String applicationPath() {
		  String contextPath = BASE_PATH; 
	      return contextPath == null ? ROOT : contextPath;
	  } 
	  @Override
	  protected String getDocumentationPath() { 
	      return BASE_PATH;
	  }
}
  
}