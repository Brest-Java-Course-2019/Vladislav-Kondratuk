package com.epam.brest.courses.rc.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Hello MVC controller.
 */
@Controller
public class HelloController {

    @GetMapping(value = "/")
    public String defaultPageRedirect() {
        return "redirect:hello";
    }

    @GetMapping(value = "/hello")
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "Jetty") String name,
                        Model model) {
        model.addAttribute("name", name);
        return "hello";
    }
}
