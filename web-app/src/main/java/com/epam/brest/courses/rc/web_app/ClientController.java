package com.epam.brest.courses.rc.web_app;

import com.epam.brest.courses.rc.filter.ClientDateInterval;
import com.epam.brest.courses.rc.model.Client;
import com.epam.brest.courses.rc.service.ClientService;
import com.epam.brest.courses.rc.web_app.validators.ClientValidator;
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
 * Client controller.
 */
@Controller
public class ClientController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalOrderController.class);

    /**
     * Service.
     */
    @Autowired
    private ClientService clientService;

    /**
     * Validator.
     */
    @Autowired
    private ClientValidator clientValidator;

    /**
     * Goto clients list page.
     *
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @GetMapping(value = "/clients")
    public final String getClientsPage(Model model) {
        LOGGER.debug("findAll({})", model);
        ClientDateInterval interval = new ClientDateInterval();
        model.addAttribute("clientsDTO", clientService.findAllDTOs());
        model.addAttribute("interval", interval);
        return "clients";
    }

    /**
     * Goto add-client list page.
     *
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @GetMapping(value = "/add-client")
    public final String getAddClientPage(Model model) {
        LOGGER.debug("getAddOrderPage({})", model);
        Client client = new Client();
        model.addAttribute("client", client);
        return "add-client";
    }

    /**
     * Persist new client into persistence storage.
     *
     * @param result binding result.
     * @param client new client.
     * @return view redirect back to clients page.
     */
    @PostMapping(value = "/add-client")
    public String addClient(@Valid @ModelAttribute("client") Client client,
                                 BindingResult result) {
        LOGGER.debug("addClient({}, {})", client, result);
        clientValidator.validate(client, result);
        if (result.hasErrors()) {
            return "add-client";
        } else {
            this.clientService.add(client);
            return "redirect:/clients";
        }
    }

    /**
     * Goto edit-client list page.
     *
     * @param clientId client ID for redirect to edit-page.
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @GetMapping(value = "/edit-client/{clientId}")
    public final String getEditClientPage(@PathVariable Integer clientId, Model model) {
        LOGGER.debug("getEditClientPage({},{})", clientId, model);
        Client client = clientService.findById(clientId);
        model.addAttribute("client", client);
        return "edit-client";
    }

    /**
     * Update client into persistence storage.
     *
     * @param result binding result.
     * @param client client for updating.
     * @return view redirect back to clients page.
     */
    @PostMapping(value = "/edit-client/{clientId}")
    public String updateClient(@Valid @ModelAttribute("client") Client client,
                                    BindingResult result) {
        LOGGER.debug("updateRentalOrder({}, {})", client, result);
        clientValidator.validate(client, result);
        if (result.hasErrors()) {
            return "edit-client";
        } else {
            this.clientService.update(client);
            return "redirect:/clients";
        }
    }

    /**
     * Delete client.
     *
     * @param clientId client ID for deleting.
     * @param model model add attributes used for rendering view.
     * @return view with use model attributes.
     */
    @GetMapping(value = "/client/{clientId}/delete")
    public final String deleteClientById(@PathVariable Integer clientId, Model model) {
        LOGGER.debug("delete({},{})", clientId, model);
        this.clientService.delete(clientId);
        return "redirect:/clients";
    }
}
