package com.epam.brest.courses.rc.web_app.consumers;

import com.epam.brest.courses.rc.dto.RentalOrderDTO;
import com.epam.brest.courses.rc.model.RentalOrder;
import com.epam.brest.courses.rc.service.RentalOrderService;
import com.epam.brest.courses.rc.web_app.RentalOrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Class RentalOrderRestConsumer implements RentalOrderService to fully compatible with rest service.
 */
public class RentalOrderRestConsumer implements RentalOrderService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderController.class);

    /**
     * Request url
     */
    private String url;

    /**
     * Rest template
     */
    private RestTemplate restTemplate;

    /**
     * RentalOrderRestConsumer constructor.
     * @param url request url
     * @param restTemplate  rest Template
     */
    public RentalOrderRestConsumer(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * findAll() method gets list of rental orders through rest service.
     * @return body of response entity rental orders records
     */
    @Override
    public List<RentalOrder> findAll() {
        LOGGER.debug("findAll");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/all", List.class);
        return (List<RentalOrder>) responseEntity.getBody();
    }

    /**
     * findAllDTOs() method gets list of rental orders DTO through rest service.
     * @return body of response entity rental orders DTO.
     */
    @Override
    public List<RentalOrderDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/all-dto", List.class);
        return  (List<RentalOrderDTO>) responseEntity.getBody();
    }

    /**
     * findById() method gets rental order by ID through rest service.
     * @param orderId rental order ID for getting.
     * @return body of response entity rental order by ID.
     */
    @Override
    public RentalOrder findById(Integer orderId) {
        LOGGER.debug("findById({})", orderId);
        ResponseEntity<RentalOrder> responseEntity = restTemplate.getForEntity(url + "/order/" + orderId,
                RentalOrder.class);
        return responseEntity.getBody();
    }

    /**
     * findDTOById() method gets rental order DTO by ID through rest service.
     * @param orderId rental order DTO ID for getting.
     * @return body of response entity rental order DTO by ID.
     */
    @Override
    public RentalOrderDTO findDTOById(Integer orderId) {
        LOGGER.debug("findById({})", orderId);
        ResponseEntity<RentalOrderDTO> responseEntity = restTemplate.getForEntity(url + "/order/" + orderId,
                RentalOrderDTO.class);
        return responseEntity.getBody();
    }

    /**
     * findDTOsByDate() method gets rental order DTO by date interval through rest service.
     * @param startDate interval start date.
     * @param endDate interval end date.
     * @return body of response entity rental order DTO by date interval.
     */
    @Override
    public List<RentalOrderDTO> findDTOsByDate(String startDate, String endDate) {
        LOGGER.debug("findDTOsByDate()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/dto/" + startDate
                + "/" + endDate, List.class);
        return  (List<RentalOrderDTO>) responseEntity.getBody();
    }

    /**
     * add() method create new rental order through rest service.
     * @param order new rental order.
     */
    @Override
    public void add(RentalOrder order) {
        LOGGER.debug("add({})", order);
        restTemplate.postForEntity(url, order, RentalOrder.class);
    }

    /**
     * update() method update rental order through rest service.
     * @param order rental order for updating.
     */
    @Override
    public void update(RentalOrder order) {
        LOGGER.debug("update({})", order);
        restTemplate.put(url, order);
    }

    /**
     * delete() method delete rental order through rest service.
     * @param orderId rental order ID for delete.
     */
    @Override
    public void delete(int orderId) {
        LOGGER.debug("delete({})", orderId);
        restTemplate.delete(url + "/order/" + orderId);
    }
}
