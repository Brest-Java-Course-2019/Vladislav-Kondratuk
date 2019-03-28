package com.epam.brest.courses.rc.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Client controller.
 */
@Controller
public class ClientController {

    /**
     * Goto clients list page.
     *
     * @return view name
     */
    @GetMapping(value = "/clients")
    public final String getClientsPage(Model model) {
        return "clients";
    }

    /**
     * Goto add-client list page.
     *
     * @return view name
     */
    @GetMapping(value = "/add-client")
    public final String getAddClientPage(Model model) {
        return "add-client";
    }

    /**
     * Goto edit-client list page.
     *
     * @return view name
     */
    @GetMapping(value = "/edit-client")
    public final String getEditClientPage(Model model) {
        return "edit-client";
    }
}
