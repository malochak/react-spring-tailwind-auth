package dev.mkon.server.api.dummy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

    @GetMapping("/api/dummy")
    public String dummy() {
        return "Hello from server!";
    }

    @GetMapping("/auth/register")
    public String register() {
        return "REGISTER";
    }
}
