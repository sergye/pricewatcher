package pricewatcher.app.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class WelcomeController {

    @GetMapping(path = "/welcome")
    public String welcome() {
        return "Welcome to Price Watcher!";
    }
}
