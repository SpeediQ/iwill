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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.VisitController.addAttributeForVisitForm;
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


    @GetMapping("/main/cform/{pageNumber}")
    public String showClientForm(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<Client> page = clientService.findAllClientsPage(currentPage);
        addAttributeForClientFormPage(model, page, currentPage);
        return "choose_or_create_client_form";
    }
    @GetMapping("/c/{idClient}")
    public String getVisit(Model model, @PathVariable("idClient") int idClient, HttpServletRequest request) {
        Client client = clientRepository.getById(idClient);
        model.addAttribute("client", client);
        return "client";
    }

    private void addAttributeForClientFormPage(Model model, Page<Client> page, int currentPage) {
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("clients", page.getContent());
        model.addAttribute("client", new Client("Brak komentarza"));
        model.addAttribute("clientsDTO", mapToClientDTOList(clientRepository.findAll()));
    }

    @PostMapping("/main/save/client")
    public String addItemByVisitFlow(Client client, Model model, HttpServletRequest request) {
        ClientCard clientCard = new ClientCard();
        client.setClientCard(clientCard);
        clientCard.setClient(client);
        setCodeForClient(client);
        setAndSaveToDbContactAddressByRequest(client, request);
        clientRepository.save(client);
        return showClientForm(model, 1);
    }

    private void addAttributeForClientForm(Model model) {
        model.addAttribute("client", new Client("Brak komentarza"));
        model.addAttribute("clients", mapToClientDTOList(clientRepository.findAll()));
    }

    private void setCodeForClient(Client client) {
        if (client.getCode() == null || client.getCode().equals("")) {
            Numerator clientNumerator = numeratorRepository.getById(ConstanceNr.NUMERATOR_CLIENT);
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
        page.setPage(currentPage -1);
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
    public String showClientManager(@PathVariable("idClient") Integer idClient, Model model) {
        Client client = clientRepository.getById(idClient);
        addAttributeForClientManagerForm(model, client);
        return "client_manager_form";
    }

    private void addAttributeForClientManagerForm(Model model, Client client) {
        addContactAddressAttribute(model, client);
        model.addAttribute("client", client);
    }

    private void addContactAddressAttribute(Model model, Client client) {
        ContactAddress contactAddressPhone = null;
        ContactAddress contactAddressEmail = null;
        List<ContactAddress> contactAddressPhoneList = getContactAddressesByStatus(client, ConstanceNr.STATUS_PHONE);
        List<ContactAddress> contactAddressEmailList = getContactAddressesByStatus(client, ConstanceNr.STATUS_EMAIL);
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
        setAndSaveToDbContactAddressByRequest(client, request);
        clientRepository.save(client);
        return showClientForm(model, 1);
    }

    private void setAndSaveToDbContactAddressByRequest(Client client, HttpServletRequest request) {
        String phoneValue = request.getParameter("phoneValue");
        String emailValue = request.getParameter("emailValue");
        Boolean phoneAgreement = "on".equals(request.getParameter("phoneAgreement")) ? true : false;
        Boolean emailAgreement = "on".equals(request.getParameter("emailAgreement")) ? true : false;
        List<ContactAddress> phoneContactAddressListBeforeUpdate = getContactAddressesByStatus(client, ConstanceNr.STATUS_PHONE);
        List<ContactAddress> emailContactAddressListBeforeUpdate = getContactAddressesByStatus(client, ConstanceNr.STATUS_EMAIL);
        handleContactAddress(client, phoneValue, phoneAgreement, phoneContactAddressListBeforeUpdate, ConstanceNr.STATUS_PHONE);
        handleContactAddress(client, emailValue, emailAgreement, emailContactAddressListBeforeUpdate, ConstanceNr.STATUS_EMAIL);
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
