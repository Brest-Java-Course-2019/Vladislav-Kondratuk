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

public class RentalOrderRestConsumer implements RentalOrderService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderController.class);

    private String url;

    private RestTemplate restTemplate;

    public RentalOrderRestConsumer(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<RentalOrder> findAll() {
        LOGGER.debug("findAll");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/all", List.class);
        return (List<RentalOrder>) responseEntity.getBody();
    }

    @Override
    public List<RentalOrderDTO> findAllDTOs() {
        LOGGER.debug("findAllDTOs()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "/all-dto", List.class);
        return  (List<RentalOrderDTO>) responseEntity.getBody();
    }

    @Override
    public RentalOrder findById(Integer orderId) {
        LOGGER.debug("findById({})", orderId);
        ResponseEntity<RentalOrder> responseEntity = restTemplate.getForEntity(url + "/order/" + orderId,
                RentalOrder.class);
        return responseEntity.getBody();
    }

    @Override
    public RentalOrderDTO findDTOById(Integer orderId) {
        LOGGER.debug("findById({})", orderId);
        ResponseEntity<RentalOrderDTO> responseEntity = restTemplate.getForEntity(url + "/order/" + orderId,
                RentalOrderDTO.class);
        return responseEntity.getBody();
    }

    @Override
    public List<RentalOrderDTO> findDTOsByDate(String startDate, String endDate) {
        return null;
    }

    @Override
    public void add(RentalOrder order) {
        LOGGER.debug("add({})", order);
        restTemplate.postForEntity(url, order, RentalOrder.class);
    }

    @Override
    public void update(RentalOrder order) {
        LOGGER.debug("update({})", order);
        restTemplate.put(url, order);

    }

    @Override
    public void delete(int orderId) {
        LOGGER.debug("update({})", orderId);
        restTemplate.delete(url + "/order/" + orderId);
    }
}
