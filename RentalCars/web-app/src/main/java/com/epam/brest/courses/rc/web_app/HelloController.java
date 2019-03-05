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

    /**
     * Goto edit hello page.
     *
     * @return view name
     */

    @GetMapping(value = "/hello")
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "Thymeleaf") String name,
                        Model model) {
        model.addAttribute("name", name);
        return "hello";
    }
}
