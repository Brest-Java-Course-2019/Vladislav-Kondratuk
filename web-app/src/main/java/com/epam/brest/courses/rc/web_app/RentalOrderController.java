package com.epam.brest.courses.rc.web_app;

import com.epam.brest.courses.rc.service.RentalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * RentalOrder controller.
 */
@Controller
public class RentalOrderController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderController.class);

    /**
     * Service.
     */
    @Autowired
    private RentalOrderService orderService;

    /**
     * Goto orders list page.
     *
     * @param model rental orders DTO list
     * @return view name
     */
    @GetMapping(value = "/orders")
    public final String getOrdersPage(Model model) {
        LOGGER.debug("findAll({})", model);
        model.addAttribute("ordersDTO", orderService.findAllDTOs());
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
