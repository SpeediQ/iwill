package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.*;
import com.kowalczyk.iwill.repository.*;
import com.kowalczyk.iwill.service.ClientService;
import com.kowalczyk.iwill.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import static com.kowalczyk.iwill.model.ConstanceNr.*;
import static com.kowalczyk.iwill.model.ConstanceNr.STATISTICS_CLIENT_SERVICE_COUNTER;

@Controller
public class VisitController {

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private NumeratorRepository numeratorRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private ServiceTypeService serviceTypeService;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientServRepository clientServRepository;

    @GetMapping("/v/st/{idVisit}")
    public String getChooseOrCreateServiceForm(Model model, @PathVariable("idVisit") int idVisit) {
        return getChooseOrCreateServiceFormPage(model, idVisit, 1, "name", "asc");
    }

    @GetMapping("/v/st/{idVisit}/{pageNumber}")
    public String getChooseOrCreateServiceFormPage(Model model, @PathVariable("idVisit") int idVisit,
                                                   @PathVariable("pageNumber") int currentPage,
                                                   @RequestParam("sortField") String sortField,
                                                   @RequestParam("sortDir") String sortDir
    ) {
        Page<ServiceType> page = serviceTypeService.findAllActiveServiceTypePage(currentPage, sortField, sortDir);
        addAttributeForServiceTypePage(model, currentPage, page, sortField, sortDir);
        model.addAttribute("idVisit", idVisit);
        return "choose_or_create_serviceType_form";
    }

    private void addAttributeForServiceTypePage(Model model, int currentPage, Page<ServiceType> page, String sortField, String sortDir) {
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("serviceTypeList", page.getContent());
        model.addAttribute("serviceType", new ServiceType());
        model.addAttribute("serviceTypeSet", serviceTypeRepository.findAllActive());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    }


    @GetMapping(value = "/saveServiceTypeManager")
    public String saveServiceTypeManager(Model model, HttpServletRequest request) {

        String idVisit = request.getParameter("idVisit");
        if (idVisit == null || idVisit == "") {
            int currentPage = 1;
            Page<ServiceType> page = serviceTypeService.findAllSortedServiceTypePage(currentPage, "name", "asc");
            addAttributeForServiceTypePage(model, currentPage, page, "name", "asc");
            return "serviceType_manager_form";
        } else {
            return getChooseOrCreateServiceFormPage(model, Integer.parseInt(idVisit), 1, "name", "asc");
        }
    }

    @GetMapping(value = "/deleteServiceTypeManager")
    public String deleteServiceTypeManager(ServiceType serviceType, Model model, HttpServletRequest request) {
        serviceTypeRepository.deleteById(serviceType.getId());
        String idVisit = request.getParameter("idVisit");
        if (idVisit == null || idVisit == "") {
            int currentPage = 1;
            Page<ServiceType> page = serviceTypeService.findAllSortedServiceTypePage(currentPage, "name", "asc");
            addAttributeForServiceTypePage(model, currentPage, page, "name", "asc");
            return "serviceType_manager_form";
        } else {
            return getChooseOrCreateServiceFormPage(model, Integer.parseInt(idVisit), 1, "name", "asc");
        }
    }


    @PostMapping(value = "/visits/save", params = "addItem")
    public String saveVisit(Visit visit, Model model, HttpServletRequest request) {
        setCurrentStatus(visit);
        setNumberForClient(visit);
        visitRepository.save(visit);
        return getChooseOrCreateServiceForm(model, visit.getId());
    }

    @PostMapping(value = "/visits/save", params = "addPromotion")
    public String addPromotion(Visit visit, Model model) {
        Visit visitDB = prepareVisitForVisitFormScreen(visit);
        updatePromotion(visit, visitDB);
        addAttributeForVisitForm(model, visitDB, visitDB.getClientServSet());
        return "visit_form";
    }

    @PostMapping(value = "/visits/save", params = "generateTitleVisit")
    public String generateTitleVisit(Visit visit, Model model) {
        Visit visitDB = prepareVisitForVisitFormScreen(visit);
        visitDB.setTitle(generateTitleByVisit(visitDB));
        addAttributeForVisitForm(model, visitDB, visitDB.getClientServSet());
        return "visit_form";
    }

    private String generateTitleByVisit(Visit visitDB) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String generatedTitle = "Wizyta " + dateFormat.format(visitDB.getDate()) + " " + visitDB.getTime();
        return generatedTitle;
    }

    private Visit prepareVisitForVisitFormScreen(Visit visit) {
        Visit visitDB = getVisitFromDbByVisit(visit);
        copyDateTimeTitleDescFromVisitToVisitPromotion(visit, visitDB);
        return visitDB;
    }

    private Visit getVisitFromDbByVisit(Visit visit) {
        Visit visitDB = null;
        if (visit.getId() > 0) {
            visitDB = visitRepository.getById(visit.getId());
        } else {
            visitDB = visit;
        }
        return visitDB;
    }

    private void copyDateTimeTitleDescFromVisitToVisitPromotion(Visit source, Visit destiny) {
        if (source.getDate() != null) {
            destiny.setDate(source.getDate());
        }
        if (source.getTime() != null) {
            destiny.setTime(source.getTime());
        }
        if (source.getTitle() != null) {
            destiny.setTitle(source.getTitle());
        }
        if (source.getDesc() != null) {
            destiny.setDesc(source.getDesc());
        }
    }

    private void updatePromotion(Visit source, Visit destiny) {
        if (source.getPromotion() <= ConstanceNr.PROMOTION_MIN_VALUE) {
            destiny.setPromotion(ConstanceNr.PROMOTION_MIN_VALUE);
        } else if (source.getPromotion() >= ConstanceNr.PROMOTION_MAX_VALUE) {
            destiny.setPromotion(ConstanceNr.PROMOTION_MAX_VALUE);
        } else {
            destiny.setPromotion(source.getPromotion());
        }
    }

    @PostMapping(value = "/visits/save", params = "doReservation")
    public String setReservationStatus(Visit visit, Model model, HttpServletRequest request) {
        setReservationStatus(visit);
        setNumberForClient(visit);
        visitRepository.save(visit);
        return getIndexForm(model);
    }
    private String getIndexForm(Model model) {
        List<Visit> visitList = visitRepository.findAll();
        int visitsCounter = visitList.size();
        int clientServCounter = clientServRepository.findAll().size();
        int clientCounter = clientRepository.findAll().size();

        double totalValue = 0;

        for (Visit visit : visitList) {
            totalValue += visit.getClientServSet().stream().mapToDouble(ClientServ::getFinalPriceIncludingPromotion).sum();
        }

        if (clientCounter != 0) {
            model.addAttribute("clientCounterString", STATISTICS_CLIENT_COUNTER + clientCounter);
        }
        if (visitsCounter != 0) {
            model.addAttribute("visitsCounterString", STATISTICS_VISIT_COUNTER + visitsCounter);
        }
        if (totalValue != 0) {
            model.addAttribute("totalValueString", STATISTICS_SUMMARY_AMOUNT + totalValue);
        }
        if (clientServCounter != 0) {
            model.addAttribute("clientServCounterString", STATISTICS_CLIENT_SERVICE_COUNTER + clientServCounter);
        }

        return "index";
    }

    @PostMapping(value = "/visits/save", params = "submit")
    public String addVisit(Visit visit, Model model, HttpServletRequest request) {
        if (visit.getStatus() == null || visit.getStatus().getId() == ConstanceNr.STATUS_RESERVATION) {
            Status status = statusRepository.getById(ConstanceNr.STATUS_VISIT);
            visit.setStatus(status);
        }
        visitRepository.save(visit);
        ClientCard clientCard = visit.getClientCard();
        Client client = clientCard.getClient();
        List<Visit> visitSet = clientCard.getSortedVisitListByVisitSet();
        addAttribiuteForClientCardForm(model, client, visitSet);
        return "ccardview_form";
    }

    private void addAttribiuteForClientCardForm(Model model, Client client, List<Visit> visitSet) {
        model.addAttribute("visitSet", visitSet);
        model.addAttribute("client", client);
    }


    @GetMapping("/visits/edit/{id}")
    public String showClientServEditForm(@PathVariable("id") Integer id, Model model) {
        Visit visit = visitRepository.getById(id);
        Set<ClientServ> clientServSet = visit.getClientServSet();
        addAttributeForVisitForm(model, visit, clientServSet);
        return "visit_form";

    }

    @GetMapping("/reservations")
    public String showReservationForm(Model model) {
        List<Visit> allReservationsByStatus = visitRepository.getAllReservationsByStatus(ConstanceNr.STATUS_RESERVATION);
        addAttributeForReservationForm(model, allReservationsByStatus);
        return "reservation_form";

    }

    private void addAttributeForReservationForm(Model model, List<Visit> allReservationsByStatus) {
        model.addAttribute("allReservationsByStatus", allReservationsByStatus);
    }


    static public void addAttributeForVisitForm(Model model, Visit visit, Set<ClientServ> clientServSet) {
        model.addAttribute("visit", visit);
        model.addAttribute("clientServSet", clientServSet);
    }

    private void setNumberForClient(Visit visit) {
        if (visit.getCode() == null || visit.getCode().equals("")) {
            Numerator visitNumerator = numeratorRepository.getById(ConstanceNr.NUMERATOR_VISIT);
            int freeVisitNumber = visitNumerator.getValue();
            String visitCode = preparingVisitCode(visit, visitNumerator, freeVisitNumber);
            visit.setCode(visitCode);
            visitNumerator.setValue(freeVisitNumber + 1);
            numeratorRepository.save(visitNumerator);
        }
    }

    private String preparingVisitCode(Visit visit, Numerator visitNumerator, int freeVisitNumber) {
        String symbol = visitNumerator.getSymbol();
        String clientCode = visit.getClientCard().getClient().getCode();
        String visitCode = clientCode + "/" + symbol + "/" + freeVisitNumber;
        return visitCode;
    }

    private void setCurrentStatus(Visit visit) {
        Status currentStatus = statusRepository.getById(ConstanceNr.STATUS_VISIT);
        visit.setStatus(currentStatus);
    }

    private void setReservationStatus(Visit visit) {
        Status currentStatus = statusRepository.getById(ConstanceNr.STATUS_RESERVATION);
        visit.setStatus(currentStatus);
    }


}
