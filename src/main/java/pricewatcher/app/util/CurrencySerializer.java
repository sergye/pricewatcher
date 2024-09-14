package pricewatcher.app.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.JsonSerializer;
import java.io.IOException;
import java.util.Currency;

public class CurrencySerializer extends JsonSerializer<Currency> {
    @Override
    public void serialize(Currency currency,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(currency.getCurrencyCode());
    }
}
