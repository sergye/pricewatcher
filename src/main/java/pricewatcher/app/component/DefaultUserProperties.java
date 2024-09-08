package pricewatcher.app.component;

import lombok.Getter;
import lombok.Setter;

import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class DefaultUserProperties {
    private String email = "admin@pricewatcher.com";
    private String password = "admin";
}
