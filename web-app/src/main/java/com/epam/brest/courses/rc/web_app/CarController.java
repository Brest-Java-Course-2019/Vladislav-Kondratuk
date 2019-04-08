package com.epam.brest.courses.rc.web_app;

import com.epam.brest.courses.rc.filter.CarCostInterval;
import com.epam.brest.courses.rc.model.Car;
import com.epam.brest.courses.rc.service.CarService;
import com.epam.brest.courses.rc.web_app.validators.CarCostIntervalValidator;
import com.epam.brest.courses.rc.web_app.validators.CarValidator;
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
import java.math.BigDecimal;

/**
 * Car controller.
 */
@Controller
public class CarController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderController.class);

    /**
     * Service.
     */
    @Autowired
    private CarService carService;

    /**
     * Validator.
     */
    @Autowired
    private CarValidator carValidator;

    /**
     * Date validator.
     */
    @Autowired
    private CarCostIntervalValidator intervalValidator;

    /**
     * Goto cars list page.
     *
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @GetMapping(value = "/cars")
    public final String getCarsPage(Model model) {
        LOGGER.debug("getCarsPage({})", model);
        CarCostInterval interval = new CarCostInterval();
        model.addAttribute("cars", carService.findAllDTOs());
        model.addAttribute("interval", interval);
        return "cars";
    }

    /**
     * Goto add-car list page.
     *
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @GetMapping(value = "/add-car")
    public final String getAddCartPage(Model model) {
        LOGGER.debug("getAddOrderPage({})", model);
        Car car = new Car();
        model.addAttribute("car", car);
        return "add-car";
    }

    /**
     * Persist new car into persistence storage.
     *
     * @param result binding result.
     * @param car new car.
     * @return view redirect back to cars page.
     */
    @PostMapping(value = "/add-car")
    public String addCar(@Valid @ModelAttribute("car") Car car,
                            BindingResult result) {
        LOGGER.debug("addCar({}, {})", car, result);
        carValidator.validate(car, result);
        if (result.hasErrors()) {
            return "add-car";
        } else {
            this.carService.add(car);
            return "redirect:/cars";
        }
    }

    /**
     * Goto edit-car list page.
     *
     * @param carId car ID for redirect to edit-page.
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @GetMapping(value = "/edit-car/{carId}")
    public final String getEditCarPage(@PathVariable Integer carId, Model model) {
        LOGGER.debug("getEditCarPage({},{})", carId, model);
        Car car = carService.findById(carId);
        model.addAttribute("car", car);
        return "edit-car";
    }

    /**
     * Update car into persistence storage.
     *
     * @param result binding result.
     * @param car car for updating.
     * @return view redirect back to cars page.
     */
    @PostMapping(value = "/edit-car/{carId}")
    public String updateCar(@Valid @ModelAttribute("car") Car car,
                               BindingResult result) {
        LOGGER.debug("updateCar({}, {})", car, result);
        carValidator.validate(car, result);
        if (result.hasErrors()) {
            return "edit-car";
        } else {
            this.carService.update(car);
            return "redirect:/cars";
        }
    }

    /**
     * Delete car.
     *
     * @param carId car ID for deleting.
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @GetMapping(value = "/car/{carId}/delete")
    public final String deleteCarById(@PathVariable Integer carId, Model model) {
        LOGGER.debug("deleteCarById({},{})", carId, model);
        this.carService.delete(carId);
        return "redirect:/cars";
    }

    /**
     * Goto filtered cars by date.
     *
     * @param interval date range for compare.
     * @param result binding result.
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @PostMapping(value = "/filter-cars")
    public final String filterClients(@Valid @ModelAttribute("interval") CarCostInterval interval,
                                      BindingResult result,
                                      Model model) {
        BigDecimal startCost = interval.getCostStartInterval();
        BigDecimal endCost = interval.getCostEndInterval();
        LOGGER.debug("filterClients({})", interval);
        intervalValidator.validate(interval, result);
        model.addAttribute("interval", interval);
        if (result.hasErrors()) {
            model.addAttribute("cars", carService.findAllDTOs());
        } else {
            model.addAttribute("cars", carService.findDTOsByCost(startCost, endCost));
        }
        return "cars";
    }
}
