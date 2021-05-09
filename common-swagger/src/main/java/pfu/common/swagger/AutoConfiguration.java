package pfu.common.swagger;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pfu.common.swagger.property.Swagger;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(Swagger.class)
@EnableSwagger2WebMvc
public class AutoConfiguration {

    private final Swagger properties;
    private final OpenApiExtensionResolver openApiExtensionResolver;

    public AutoConfiguration(Swagger properties,
                             OpenApiExtensionResolver openApiExtensionResolver) {
        this.properties = properties;
        this.openApiExtensionResolver = openApiExtensionResolver;
    }


    @Bean
    public Docket createRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title(this.properties.getTitle())
                .contact(new Contact(this.properties.getContact(), this.properties.getContactUrl(), this.properties.getContactEmail()))
                .version(this.properties.getVersion())
                .description(this.properties.getDescription())
                .build();

        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        if (null != this.properties.getParameters()) {
            this.properties.getParameters().forEach(param -> {
                Parameter parameter = parameterBuilder
                        .name(param.getName())
                        .description(param.getDescription())
                        .modelRef(new ModelRef(param.getDataType()))
                        .parameterType(param.getParamType())
                        .required(param.isRequired())
                        .build();
                parameters.add(parameter);
            });
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.properties.getBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters)
                .extensions(openApiExtensionResolver.buildExtensions(this.properties.getGroupName()));
    }

}
