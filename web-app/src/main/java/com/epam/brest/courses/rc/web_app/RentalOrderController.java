package com.epam.brest.courses.rc.web_app;

import com.epam.brest.courses.rc.filter.RentalOrderDateInterval;
import com.epam.brest.courses.rc.model.RentalOrder;
import com.epam.brest.courses.rc.service.RentalOrderService;
import com.epam.brest.courses.rc.web_app.validators.RentalOrderDateIntervalValidator;
import com.epam.brest.courses.rc.web_app.validators.RentalOrderValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
     * Validator.
     */
    @Autowired
    private RentalOrderValidator orderValidator;

    /**
     * Date validator.
     */
    @Autowired
    private RentalOrderDateIntervalValidator intervalValidator;

    /**
     * Goto orders list page.
     *
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @GetMapping(value = "/orders")
    public final String getOrdersPage(Model model) {
        LOGGER.debug("getOrdersPage({})", model);
        RentalOrderDateInterval interval = new RentalOrderDateInterval();
        model.addAttribute("orders", orderService.findAllDTOs());
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
     * @param result binding result.
     * @param order new rental order.
     * @return view redirect back to orders page.
     */
    @PostMapping(value = "/add-order")
    public String addRentalOrder(@Valid @ModelAttribute("order") RentalOrder order,
                                 BindingResult result) {
        LOGGER.debug("addRentalOrder({}, {})", order, result);
        orderValidator.validate(order, result);
        if (result.hasErrors()) {
            return "add-order";
        } else {
            this.orderService.add(order);
            return "redirect:/orders";
        }
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
     * @param result binding result.
     * @param order rental order for updating.
     * @return view redirect back to orders page.
     */
    @PostMapping(value = "/edit-order/{orderId}")
    public String updateRentalOrder(@Valid @ModelAttribute("order") RentalOrder order,
                                    BindingResult result) {
        LOGGER.debug("updateRentalOrder({}, {})", order, result);
        orderValidator.validate(order, result);
        if (result.hasErrors()) {
            return "edit-order";
        } else {
            this.orderService.update(order);
            return "redirect:/orders";
        }
    }

    /**
     * Delete rental order.
     *
     * @param orderId rental order ID for deleting.
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @GetMapping(value = "/order/{orderId}/delete")
    public final String deleteRentalOrderById(@PathVariable Integer orderId, Model model) {
        LOGGER.debug("deleteRentalOrderById({},{})", orderId, model);
        this.orderService.delete(orderId);
        return "redirect:/orders";
    }

    /**
     * Goto filtered rental orders by date.
     *
     * @param interval date range for compare.
     * @param result binding result.
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @PostMapping(value = "/filter-orders")
    public final String filterOrders(@Valid @ModelAttribute("interval") RentalOrderDateInterval interval,
                                     BindingResult result,
                                     Model model) {
        String startDate = interval.getRegStartInterval().toString();
        String endDate = interval.getRegEndInterval().toString();
        LOGGER.debug("filterOrders({})", interval);
        intervalValidator.validate(interval, result);
        model.addAttribute("interval", interval);
        if (result.hasErrors()) {
            model.addAttribute("orders", orderService.findAllDTOs());
        } else {
            model.addAttribute("orders", orderService.findDTOsByDate(startDate, endDate));
        }
        return "orders";
    }
}
