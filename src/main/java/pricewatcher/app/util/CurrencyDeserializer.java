package pricewatcher.app.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.Currency;

public class CurrencyDeserializer extends JsonDeserializer<Currency> {
    @Override
    public Currency deserialize(JsonParser jsonParser,
                                DeserializationContext deserializationContext) throws IOException {
        return Currency.getInstance(jsonParser.getText().toUpperCase());
    }
}
