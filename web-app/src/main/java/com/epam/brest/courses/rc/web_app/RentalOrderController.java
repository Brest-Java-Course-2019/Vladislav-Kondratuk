package com.epam.brest.courses.rc.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * RentalOrder controller.
 */
@Controller
public class RentalOrderController {

    /**
     * Goto orders list page.
     *
     * @return view name
     */
    @GetMapping(value = "/orders")
    public final String getOrdersPage(Model model) {
        return "orders";
    }

    /**
     * Goto add-order list page.
     *
     * @return view name
     */
    @GetMapping(value = "/add-order")
    public final String getAddOrderPage(Model model) {
        return "add-order";
    }

    /**
     * Goto edit-order list page.
     *
     * @return view name
     */
    @GetMapping(value = "/edit-order")
    public final String getEditOrderPage(Model model) {
        return "edit-order";
    }
}
