package az.company.accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/test")
public class TestConnectionController {
    @GetMapping
    public String test() {
        return "Connection is working...";
    }
}
