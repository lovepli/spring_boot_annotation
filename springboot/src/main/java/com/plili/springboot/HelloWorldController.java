package com.plili.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lipan
 * @date: 2019-06-13
 * @description:
 */
@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }
}
