package pricewatcher.app.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import pricewatcher.app.util.CurrencyDeserializer;
import pricewatcher.app.util.CurrencySerializer;

import java.time.format.DateTimeFormatter;
import java.util.Currency;

@Configuration
public class JacksonConfig {
    @Bean
    Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

        builder.serializationInclusion(JsonInclude.Include.NON_NULL)
                .modulesToInstall(new JsonNullableModule());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        builder.serializers(new LocalDateTimeSerializer(formatter));
        builder.deserializers(new LocalDateTimeDeserializer(formatter));

        return builder;
    }

    @Bean
    public SimpleModule currencyModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Currency.class, new CurrencySerializer());
        module.addDeserializer(Currency.class, new CurrencyDeserializer());
        return module;
    }
}
