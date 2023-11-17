package com.misijav.flipmemo.rest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/secured")
public class SecuredController {

    @GetMapping("admin")
    private String helloSecured() {
        return "Hello from admin secured endpoint!";
    }

    @GetMapping("any")
    private String anySecured()  { return "Hello from any secured endpoint!"; }

    @GetMapping("none")
    private String noneSecured()  { return "Hello from none secured endpoint!"; }
}
