package com.epam.brest.courses.rc.rest_app;

import com.epam.brest.courses.rc.dto.RentalOrderDTO;
import com.epam.brest.courses.rc.model.RentalOrder;
import com.epam.brest.courses.rc.service.RentalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest Controller for rental orders.
 */
@RestController
@RequestMapping(value = "/orders")
public class RentalOrderRestController implements RentalOrderService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderRestController.class);

    /**
     * Service.
     */
    @Autowired
    private RentalOrderService rentalOrderService;

    /**
     * Gets rental orders.
     * @return list of rental orders.
     *
     * curl -X GET -v http://localhost:8088/orders/all
     */
    @Override
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<RentalOrder> findAll() {
        LOGGER.debug("findAll()");
        return rentalOrderService.findAll();
    }

    /**
     * Gets DTO rental orders.
     * @return list of DTO rental orders.
     *
     * curl -X GET -v http://localhost:8088/orders/all-dto
     */
    @Override
    @RequestMapping(value = "/all-dto", method = RequestMethod.GET)
    public List<RentalOrderDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        return rentalOrderService.findAllDTOs();
    }

    /**
     * Gets rental order by ID.
     * @return rental order by ID.
     *
     * curl -X GET -v http://localhost:8088/orders/order/1
     */
    @Override
    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
    public RentalOrder findById(@PathVariable Integer orderId) {
        LOGGER.debug("findById({})", orderId);
        return rentalOrderService.findById(orderId);
    }

    /**
     * Gets DTO rental order by ID.
     * @return DTO rental order by ID.
     *
     * curl -X GET -v http://localhost:8088/orders/dto/1
     */
    @Override
    @RequestMapping(value = "/dto/{orderId}", method = RequestMethod.GET)
    public RentalOrderDTO findDTOById(@PathVariable Integer orderId) {
        LOGGER.debug("findDTOById({})", orderId);
        return rentalOrderService.findDTOById(orderId);
    }

    /**
     * Gets DTO rental order filtered by date.
     * @param startDate interval start date.
     * @param endDate interval end date.
     * @return DTO rental orders list filtered by date.
     *
     * curl -X GET -v http://localhost:8088/orders/dto/2019-01-18/2019-01-26
     */
    @Override
    @RequestMapping(value = "/dto/{startDate}/{endDate}", method = RequestMethod.GET)
    public List<RentalOrderDTO> findDTOsByDate(@PathVariable(value = "startDate") final String startDate,
                                               @PathVariable(value = "endDate") final String endDate) {
        LOGGER.debug("findDTOsByDate({}, {})", startDate, endDate);
        return rentalOrderService.findDTOsByDate(startDate, endDate);
    }

    /**
     * Adds new rental order.
     * @param order new rental order.
     *
     *  curl -H "Content-Type: application/json" -X POST -d '{"orderId":"5","clientId":"1","carId":"2",
     *              "rentalTime":"1","regDate":"2019-01-01"}' -v http://localhost:8088/orders
     */
    @Override
    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody RentalOrder order) {
        LOGGER.debug("add rental order({})", order);
        rentalOrderService.add(order);
    }

    /**
     * Update rental order.
     * @param order rental order for updating.
     *
     *  curl -H "Content-Type: application/json" -X PUT -d '{"orderId":"5","clientId":"2","carId":"3",
     *              "rentalTime":"3","regDate":"2019-01-01"}' -v http://localhost:8088/orders
     */
    @Override
    @RequestMapping(method = RequestMethod.PUT)
    public void update(@RequestBody RentalOrder order) {
        LOGGER.debug("update rental order({})", order);
        rentalOrderService.update(order);
    }

    /**
     * Delete rental order by ID.
     * @param orderId rental order ID for delete.
     *
     * curl -X DELETE -v http://localhost:8088/orders/order/5
     */
    @Override
    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int orderId) {
        LOGGER.debug("delete rental order({})", orderId);
        rentalOrderService.delete(orderId);
    }
}
