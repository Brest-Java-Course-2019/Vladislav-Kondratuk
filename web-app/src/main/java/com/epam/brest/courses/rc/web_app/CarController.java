package com.epam.brest.courses.rc.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Car controller.
 */
@Controller
public class CarController {

    /**
     * Goto cars list page.
     *
     * @return view name
     */
    @GetMapping(value = "/cars")
    public final String getCarsPage(Model model) {
        return "cars";
    }

    /**
     * Goto add-car list page.
     *
     * @return view name
     */
    @GetMapping(value = "/add-car")
    public final String getAddCarPage(Model model) {
        return "add-car";
    }

    /**
     * Goto edit-car list page.
     *
     * @return view name
     */
    @GetMapping(value = "/edit-car")
    public final String getEditCarPage(Model model) {
        return "edit-car";
    }
}
