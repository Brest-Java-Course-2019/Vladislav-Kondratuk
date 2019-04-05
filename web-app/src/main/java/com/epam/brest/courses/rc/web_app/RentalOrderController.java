package com.epam.brest.courses.rc.web_app;

import com.epam.brest.courses.rc.filter.RentalOrderDateInterval;
import com.epam.brest.courses.rc.model.RentalOrder;
import com.epam.brest.courses.rc.service.RentalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @GetMapping(value = "/orders")
    public final String getOrdersPage(Model model) {
        LOGGER.debug("findAll({})", model);
        RentalOrderDateInterval interval = new RentalOrderDateInterval();
        model.addAttribute("ordersDTO", orderService.findAllDTOs());
        model.addAttribute("interval", interval);
        return "orders";
    }

    /**
     * Goto add-order list page.
     *
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @GetMapping(value = "/add-order")
    public final String getAddOrderPage(Model model) {
        LOGGER.debug("getAddOrderPage({})", model);
        RentalOrder order = new RentalOrder();
        model.addAttribute("order", order);
        return "add-order";
    }

    /**
     * Persist new rental order into persistence storage.
     *
     * @param order new rental order.
     * @return view redirect back to orders page.
     */
    @PostMapping(value = "/add-order")
    public String addRentalOrder(RentalOrder order) {
        LOGGER.debug("addRentalOrder({})", order);
        this.orderService.add(order);
        return "redirect:/orders";
    }

    /**
     * Goto edit-order list page.
     *
     * @param orderId rental order ID for redirect to edit-page.
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @GetMapping(value = "/edit-order/{orderId}")
    public final String getEditOrderPage(@PathVariable Integer orderId, Model model) {
        LOGGER.debug("getEditOrderPage({},{})", orderId, model);
        RentalOrder order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "edit-order";
    }

    /**
     * Update rental order into persistence storage.
     *
     * @param order rental order for updating.
     * @return view redirect back to orders page.
     */
    @PostMapping(value = "/edit-order/{orderId}")
    public String updateRentalOrder(RentalOrder order) {
        LOGGER.debug("updateRentalOrder({})", order);
        this.orderService.update(order);
        return "redirect:/orders";
    }

    /**
     * Delete rental order.
     *
     * @param orderId rental order ID for deleting.
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @GetMapping(value = "/order/{orderId}/delete")
    public final String deleteDepartmentById(@PathVariable Integer orderId, Model model) {
        LOGGER.debug("delete({},{})", orderId, model);
        this.orderService.delete(orderId);
        return "redirect:/orders";
    }
}
