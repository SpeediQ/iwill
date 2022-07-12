package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.*;
import com.kowalczyk.iwill.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.VisitController.addAttributeForVisitForm;
import static com.kowalczyk.iwill.model.mapper.ClientDTOMapper.mapToClientDTOList;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientCardRepository clientCardRepository;
    @Autowired
    private NumeratorRepository numeratorRepository;
    @Autowired
    private ContactAddressRepository contactAddressRepository;
    @Autowired
    private StatusRepository statusRepository;



    @GetMapping("/main/cform")
    public String showClientForm(Model model) {
        addAttributeForClientForm(model);
        return "choose_or_create_client_form";
    }

    @PostMapping("/main/save/client")
    public String addItemByVisitFlow(Client client, Model model) {
        ClientCard clientCard = new ClientCard();
        client.setClientCard(clientCard);
        clientCard.setClient(client);
        setNumberForClient(client);
        clientRepository.save(client);
        addAttributeForClientForm(model);
        return "choose_or_create_client_form";
    }

    private void addAttributeForClientForm(Model model) {
        model.addAttribute("client", new Client("Brak komentarza"));
        model.addAttribute("clients", mapToClientDTOList(clientRepository.findAll()));
    }

    private void setNumberForClient(Client client) {
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


    @GetMapping(value = "/c/add/{idClient}")
    public String showClientCard(@PathVariable("idClient") Integer idClient, Model model) {
        Client client = clientRepository.getById(idClient);
        addAttributeForClientCardForm(model, client);
        return "ccard_form";
    }

    @GetMapping(value = "/client/edit/{idClient}")
    public String showClientManager(@PathVariable("idClient") Integer idClient, Model model) {
        Client client = clientRepository.getById(idClient);
        ContactAddress contactAddressPhone = null;
        ContactAddress contactAddressEmail = null;
        List<ContactAddress> contactAddressPhoneList = client.getContactAddresses().stream().filter(contactAddress -> contactAddress.getStatus().getId() == ConstanceNr.STATUS_PHONE).collect(Collectors.toList());
        List<ContactAddress> contactAddressEmailList = client.getContactAddresses().stream().filter(contactAddress -> contactAddress.getStatus().getId() == ConstanceNr.STATUS_EMAIL).collect(Collectors.toList());
        if (!contactAddressPhoneList.isEmpty()) {
            contactAddressPhone = contactAddressPhoneList.get(0);
        }
        if (!contactAddressEmailList.isEmpty()) {
            contactAddressEmail = contactAddressEmailList.get(0);
        }
        model.addAttribute("contactAddressPhone", contactAddressPhone);
        model.addAttribute("contactAddressEmail", contactAddressEmail);
        model.addAttribute("client", client);
        return "client_manager_form";
    }

    @PostMapping(value = "/client/edit/save", params = "saveUpdatedContact")
    public String showClientManager(Client client, Model model, HttpServletRequest request) {
        String phoneValue = request.getParameter("phoneValue");
        Boolean phoneAgreement = "on".equals(request.getParameter("phoneAgreement")) ? true : false;
        String emailValue = request.getParameter("emailValue");
        Boolean emailAgreement = "on".equals(request.getParameter("emailAgreement")) ? true : false;
        List<ContactAddress> oldContactAddressPhoneList = client.getContactAddresses().stream().filter(contactAddress -> contactAddress.getStatus().getId() == ConstanceNr.STATUS_PHONE).collect(Collectors.toList());
        List<ContactAddress> oldContactAddressEmailList = client.getContactAddresses().stream().filter(contactAddress -> contactAddress.getStatus().getId() == ConstanceNr.STATUS_EMAIL).collect(Collectors.toList());
        if (!oldContactAddressPhoneList.isEmpty()) {
            ContactAddress contactAddressPhone = oldContactAddressPhoneList.get(0);
            setAgreementValue(phoneValue, phoneAgreement, contactAddressPhone);
            contactAddressRepository.save(contactAddressPhone);
        } else {
            ContactAddress contactAddressPhone = getContactAddressWithUpdatedValueAgreementClient(client, phoneValue, phoneAgreement);
            contactAddressPhone.setStatus(statusRepository.getById(ConstanceNr.STATUS_PHONE));
            contactAddressRepository.save(contactAddressPhone);
            client.getContactAddresses().add(contactAddressPhone);

        }
        if (!oldContactAddressEmailList.isEmpty()) {
            ContactAddress contactAddressEmail = oldContactAddressEmailList.get(0);
            setAgreementValue(emailValue, emailAgreement, contactAddressEmail);
            contactAddressRepository.save(contactAddressEmail);
        }else {
            ContactAddress contactAddressEmail = getContactAddressWithUpdatedValueAgreementClient(client, emailValue, emailAgreement);
            contactAddressEmail.setStatus(statusRepository.getById(ConstanceNr.STATUS_EMAIL));
            contactAddressRepository.save(contactAddressEmail);
            client.getContactAddresses().add(contactAddressEmail);
        }
        clientRepository.save(client);
        return "index.html";
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

    private void addAttributeForClientCardForm(Model model, Client client) {
        model.addAttribute("client", client);
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
