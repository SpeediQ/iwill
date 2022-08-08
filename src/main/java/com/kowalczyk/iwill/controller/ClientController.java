package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.*;
import com.kowalczyk.iwill.repository.*;
import com.kowalczyk.iwill.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.VisitController.addAttributeForVisitForm;
import static com.kowalczyk.iwill.model.ConstanceNr.*;
import static com.kowalczyk.iwill.model.mapper.ClientDTOMapper.mapToClientDTOList;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientCardRepository clientCardRepository;
    @Autowired
    private NumeratorRepository numeratorRepository;
    @Autowired
    private ContactAddressRepository contactAddressRepository;
    @Autowired
    private StatusRepository statusRepository;

    @GetMapping("/main/cform")
    public String getClientsForm(Model model) {
        return showClientForm(model, 1, "name", "asc");
    }

    @GetMapping("/clientmanager")
    public String getClientManager(Model model) {

        return showClientManagerForm(model, 1, "name", "asc");
    }

    @GetMapping("/main/cform/{pageNumber}")
    public String showClientForm(Model model, @PathVariable("pageNumber") int currentPage,
                                 @RequestParam("sortField") String sortField,
                                 @RequestParam("sortDir") String sortDir) {
        return getChooseOrCreateClientForm(model, currentPage, sortField, sortDir);
    }

    @GetMapping("/clientmanager/{pageNumber}")
    public String showClientManagerForm(Model model, @PathVariable("pageNumber") int currentPage,
                                        @RequestParam("sortField") String sortField,
                                        @RequestParam("sortDir") String sortDir) {
        model.addAttribute("isManagerView", true);
        Page<Client> page = clientService.findAllSorteredClientsManagerPage(currentPage, sortField, sortDir);
        addAttributeForClientFormPage(model, page, currentPage, sortField, sortDir);
        return "choose_or_create_client_form";
    }

    private String getChooseOrCreateClientForm(Model model, @PathVariable("pageNumber") int currentPage, @RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir) {
        Page<Client> page = clientService.findAllSorteredClientsPage(currentPage, sortField, sortDir);
        addAttributeForClientFormPage(model, page, currentPage, sortField, sortDir);
        return "choose_or_create_client_form";
    }


    private void addAttributeForClientFormPage(Model model, Page<Client> page, int currentPage, String sortField, String sortDir) {
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("clients", page.getContent());
        model.addAttribute("client", new Client("Brak komentarza"));
        model.addAttribute("clientsDTO", mapToClientDTOList(clientRepository.findAll()));
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    }

    @PostMapping("/main/save/client")
    public String addItemByVisitFlow(Client client, Model model, HttpServletRequest request) {
        Boolean isManagerView = "true".equals(request.getParameter("isManagerView")) ? true : false;
        ClientCard clientCard = client.isIdValid() ? client.getClientCard() : new ClientCard();
        client.setActive(ACTIVE_CLIENT);
        client.setClientCard(clientCard);
        clientCard.setClient(client);
        if (client.getCode() == null ||  "".equals(client.getCode())) {
            setCodeForClient(client);
        }
        setAndSaveToDbContactAddressByRequest(client, request);
        clientRepository.save(client);
        if (isManagerView) {
            return showClientManagerForm(model, 1, "name", "asc");
        } else {
            return showClientForm(model, 1, "name", "asc");
        }
    }

    @PostMapping(value = "/main/save/client", params = "cancel")
    public String cancelAddClient(Client client, Model model, HttpServletRequest request) {
        Boolean isManagerView = "true".equals(request.getParameter(FLAG_IS_MANAGER_VIEW)) ? true : false;
        Boolean isEditView = "true".equals(request.getParameter(FLAG_IS_EDIT_VIEW)) ? true : false;
        if (isManagerView) {
            if (isEditView) {
                return showClientEditManager(client.getId(), model, request);
            }
            return getClientManager(model);
        } else {
            if (isEditView) {
                return showClientEdit(client.getId(), model);
            }
            return getClientsForm(model);
        }
    }
    @PostMapping(value = "/main/save/client", params = "delete")
    public String deleteAddClient(Client client, Model model, HttpServletRequest request) {
        Boolean isManagerView = "true".equals(request.getParameter(FLAG_IS_MANAGER_VIEW)) ? true : false;
        Boolean isEditView = "true".equals(request.getParameter(FLAG_IS_EDIT_VIEW)) ? true : false;
        client = clientRepository.getById(client.getId());
        client.setName(DEFAULT_CLIENT_NAME);
        client.setLastname(DEFAULT_CLIENT_LASTNAME);
        client.setComment(DEFAULT_CLIENT_COMMENT);
        client.setCode(DEFAULT_CLIENT_CODE);
        client.setActive(INACTIVE_CLIENT);
        client.setClientCard(null);
        client.setDate(null);
        client.getContactAddresses().stream().forEach(contactAddress -> contactAddressRepository.delete(contactAddress));
        client.setContactAddresses(null);
        clientRepository.save(client);
        if (isManagerView) {
            return getClientManager(model);
        } else {
            return getClientsForm(model);
        }
    }

    private void passBooleanAttribute(String attributeName, Model model, HttpServletRequest request) {
        model.addAttribute(attributeName, "true".equals(request.getParameter(attributeName)) ? true : false);
    }
//    private void passAttribute(String attributeName, Model model, HttpServletRequest request) {
//        model.addAttribute(attributeName, request.getParameter(attributeName));
//    }

    @GetMapping("/c")
    public String getClient(Model model, HttpServletRequest request, Client client) {
        passBooleanAttribute(FLAG_IS_MANAGER_VIEW, model, request);
        passBooleanAttribute(FLAG_IS_EDIT_VIEW, model, request);
        String phoneValue = request.getParameter("phoneValue");
        String emailValue = request.getParameter("emailValue");
        Boolean phoneAgreement = "on".equals(request.getParameter("phoneAgreement")) ? true : false;
        Boolean emailAgreement = "on".equals(request.getParameter("emailAgreement")) ? true : false;

        model.addAttribute("client", client);
        model.addAttribute("phoneValue", phoneValue);
        model.addAttribute("emailValue", emailValue);
        model.addAttribute("phoneAgreement", phoneAgreement);
        model.addAttribute("emailAgreement", emailAgreement);
        return "client";
    }

    private void addAttributeForClientForm(Model model) {
        model.addAttribute("client", new Client("Brak komentarza"));
        model.addAttribute("clients", mapToClientDTOList(clientRepository.findAll()));
    }

    private void setCodeForClient(Client client) {
        if (client.getCode() == null || client.getCode().equals("")) {
            Numerator clientNumerator = numeratorRepository.getById(NUMERATOR_CLIENT);
            int freeClientNumber = clientNumerator.getValue();
            String symbol = clientNumerator.getSymbol();
            String clientCode = symbol + "/" + freeClientNumber;
            client.setCode(clientCode);
            clientNumerator.setValue(freeClientNumber + 1);
            numeratorRepository.save(clientNumerator);
        }
    }


    @GetMapping(value = "/c/add/{idClient}/{pageNumber}")
    public String showClientCard(@PathVariable("idClient") Integer idClient, @PathVariable("pageNumber") int currentPage, Model model) {
        Client client = clientRepository.getById(idClient);
        List<Visit> sortedVisitListByVisitSet = client.getClientCard().getSortedVisitListByVisitSet();
        return getCCardFormPage(idClient, currentPage, model, client, sortedVisitListByVisitSet);
    }

    private String getCCardFormPage(Integer idClient, int currentPage, Model model, Client client, List<Visit> sortedVisitListByVisitSet) {
        model.addAttribute("client", client);
        PagedListHolder page = new PagedListHolder(sortedVisitListByVisitSet);
        page.setPageSize(5);
        page.setPage(currentPage - 1);
        page.getPageCount();
        page.getPageList();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getPageCount());
        model.addAttribute("totalElements", sortedVisitListByVisitSet.size());
        model.addAttribute("visitList", page.getPageList());
        model.addAttribute("idClient", idClient);
        return "ccard_form";
    }

    @GetMapping(value = "/client/edit/{idClient}")
    public String showClientEdit(@PathVariable("idClient") Integer idClient, Model model) {
        Client client = clientRepository.getById(idClient);
        model.addAttribute(FLAG_IS_EDIT_VIEW, true);
        addAttributeForClientManagerForm(model, client);
        return "client_manager_form";
    }

    @GetMapping(value = "/clientmanager/edit/{idClient}")
    public String showClientEditManager(@PathVariable("idClient") Integer idClient, Model model, HttpServletRequest request) {
        Client client = clientRepository.getById(idClient);
        addAttributeForClientManagerForm(model, client);
        model.addAttribute(FLAG_IS_EDIT_VIEW, true);
        model.addAttribute(FLAG_IS_MANAGER_VIEW, true);
        return "client_manager_form";
    }

    private void addAttributeForClientManagerForm(Model model, Client client) {
        Date lastVisitDate = client.getClientCard().getVisitSet().stream()
                .map(visit -> visit.getDate())
                .sorted(Collections.reverseOrder())
                .findFirst()
                .orElse(null);
        addContactAddressAttribute(model, client);
        model.addAttribute("client", client);
        model.addAttribute("lastVisitDate", lastVisitDate);
    }

    private void addContactAddressAttribute(Model model, Client client) {
        ContactAddress contactAddressPhone = null;
        ContactAddress contactAddressEmail = null;
        List<ContactAddress> contactAddressPhoneList = getContactAddressesByStatus(client, STATUS_PHONE);
        List<ContactAddress> contactAddressEmailList = getContactAddressesByStatus(client, STATUS_EMAIL);
        if (!contactAddressPhoneList.isEmpty()) {
            contactAddressPhone = contactAddressPhoneList.get(0);
        }
        if (!contactAddressEmailList.isEmpty()) {
            contactAddressEmail = contactAddressEmailList.get(0);
        }
        model.addAttribute("contactAddressPhone", contactAddressPhone);
        model.addAttribute("contactAddressEmail", contactAddressEmail);
    }

    @PostMapping(value = "/client/edit/save", params = "saveUpdatedContact")
    public String showClientManager(Client client, Model model, HttpServletRequest request) {
        return getClient(model, request, client);
    }

    @PostMapping(value = "/client/edit/save", params = "returnUpdatedContact")
    public String returnClientManager(Client client, Model model, HttpServletRequest request) {
        Boolean isManagerView = "true".equals(request.getParameter(FLAG_IS_MANAGER_VIEW)) ? true : false;
        if (isManagerView) {
            return getClientManager(model);
        } else {
            return getClientsForm(model);
        }
    }

    @PostMapping(value = "/client/edit/save", params = "deleteUpdatedContact")
    public String deleteClientManager(Client client, Model model, HttpServletRequest request) {
        model.addAttribute(FLAG_IS_DELETE_ACTION, true);
        LocalDate date = LocalDate.parse(request.getParameter(LAST_VISIT_DATE).substring(0,10));

        Period period = Period.between(date, LocalDate.now());
        if (period.getYears() < 5){
            model.addAttribute(ALERT, MESSAGE_5_YEARS_BEFORE_DELETE);
        }
        model.addAttribute(LAST_VISIT_DATE, date);
        return getClient(model, request, client);
    }

    private void setAndSaveToDbContactAddressByRequest(Client client, HttpServletRequest request) {
        String phoneValue = request.getParameter(PHONE_VALUE);
        String emailValue = request.getParameter(EMAIL_VALUE);
        Boolean phoneAgreement = "true".equals(request.getParameter(PHONE_AGREEMENT)) ? true : false;
        Boolean emailAgreement = "true".equals(request.getParameter(EMAIL_AGREEMENT)) ? true : false;
        List<ContactAddress> phoneContactAddressListBeforeUpdate = getContactAddressesByStatus(client, STATUS_PHONE);
        List<ContactAddress> emailContactAddressListBeforeUpdate = getContactAddressesByStatus(client, STATUS_EMAIL);
        handleContactAddress(client, phoneValue, phoneAgreement, phoneContactAddressListBeforeUpdate, STATUS_PHONE);
        handleContactAddress(client, emailValue, emailAgreement, emailContactAddressListBeforeUpdate, STATUS_EMAIL);
    }

    private List<ContactAddress> getContactAddressesByStatus(Client client, int statusPhone) {
        return client.getContactAddresses().stream().filter(contactAddress -> contactAddress.getStatus().getId() == statusPhone).collect(Collectors.toList());
    }

    private void handleContactAddress(Client client, String phoneValue, Boolean phoneAgreement, List<ContactAddress> oldContactAddressPhoneList, int statusPhone) {
        if (oldContactAddressPhoneList.isEmpty()) {
            createNewContactAddressAddToClientAndSaveToDb(client, phoneValue, phoneAgreement, statusRepository.getById(statusPhone));
        } else {
            setContactAddressAndSaveToDb(phoneValue, phoneAgreement, oldContactAddressPhoneList);
        }
    }

    private void setContactAddressAndSaveToDb(String phoneValue, Boolean phoneAgreement, List<ContactAddress> oldContactAddressPhoneList) {
        ContactAddress contactAddressPhone = oldContactAddressPhoneList.get(0);
        setAgreementValue(phoneValue, phoneAgreement, contactAddressPhone);
        contactAddressRepository.save(contactAddressPhone);
    }

    private void createNewContactAddressAddToClientAndSaveToDb(Client client, String phoneValue, Boolean phoneAgreement, Status status) {
        ContactAddress contactAddressPhone = getContactAddressWithUpdatedValueAgreementClient(client, phoneValue, phoneAgreement);
        contactAddressPhone.setStatus(status);
        if (client.isIdValid()) {
            contactAddressRepository.save(contactAddressPhone);
            client.getContactAddresses().add(contactAddressPhone);
        } else {
            client.getContactAddresses().add(contactAddressPhone);
            clientRepository.save(client);
            contactAddressRepository.save(contactAddressPhone);
        }
    }

    private ContactAddress getContactAddressWithUpdatedValueAgreementClient(Client client, String phoneValue, Boolean phoneAgreement) {
        ContactAddress contactAddressPhone = new ContactAddress();
        contactAddressPhone.setValue(phoneValue);
        contactAddressPhone.setAgreement(phoneAgreement);
        contactAddressPhone.setClient(client);
        return contactAddressPhone;
    }

    private void setAgreementValue(String value, Boolean agreement, ContactAddress contactAddress) {
        contactAddress.setAgreement(agreement);
        contactAddress.setValue(value);
    }


    @PostMapping(value = "c/v/add")
    public String addVisitToClient(Client client, Model model) {
        Visit visit = new Visit();
        Client newClient = clientRepository.getById(client.getId());
        ClientCard clientCard = newClient.getClientCard();
        clientCard.getVisitSet().add(visit);
        visit.setClientCard(clientCard);
        prepareDefaultVisitTitleDesc(visit);
        clientCardRepository.save(clientCard);
        addAttributeForVisitForm(model, visit, visit.getClientServSet());
        return "visit_form";
    }

    private void prepareDefaultVisitTitleDesc(Visit visit) {
        visit.setTitle("Wizyta ");
        visit.setDesc("Szczegóły dot. wizyty: ");
    }

}
