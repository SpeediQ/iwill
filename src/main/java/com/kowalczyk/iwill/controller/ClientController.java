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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.VisitController.addAttributeForVisitForm;
import static com.kowalczyk.iwill.model.ConstanceNr.*;
import static com.kowalczyk.iwill.model.mapper.ClientDTOMapper.mapToClientDTOList;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private VisitRepository visitRepository;
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
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;


    public static Boolean DATE_SEARCH_AGREEMENT_VALUE = null;
    public static Boolean SERVICE_TYPE_SEARCH_AGREEMENT_VALUE = null;
    public static Boolean FIELD_SEARCH_AGREEMENT_VALUE = null;
    public static Boolean IS_VISIT_FORM = false;
    public static LocalDate START_DATE_VALUE = null;
    public static LocalDate END_DATE_VALUE = null;
    public static Integer SERVICE_TYPE_ID_VALUE = null;
    public static String FIELD_VALUE = null;
    public static String SEARCH_VALUE = null;


    @GetMapping("/main/cform")
    public String getClientsForm(Model model) {
        return showClientForm(model, 1, "name", "asc");
    }

    @GetMapping("/clientmanager")
    public String getClientManager(Model model) {

        return showClientManagerForm(model, 1, "name", "asc");
    }

    @GetMapping("/analyzer")
    public String getClientManager(Model model, HttpServletRequest request) throws ParseException {
        return getAnalyzerPage(model, 1, request);
    }

    @GetMapping("/analyzer/{pageNumber}")
    public String getAnalyzerPage(Model model, @PathVariable("pageNumber") int currentPage, HttpServletRequest request) throws ParseException {
        clearStiticFieldsForAnalyzer();
        return getAnalyzerSc(model, request);
    }

    @GetMapping("/clientanalyzer/{idClient}")
    public String getClientAnalyzer(Model model, @PathVariable("idClient") Integer idClient, HttpServletRequest request) throws ParseException {
        clearStiticFieldsForAnalyzer();
        return getClientAnalyzerSc(model, idClient, request);
    }

    @GetMapping(value = "/clientanalyzer/{idClient}", params = "editVisit")
    public String getEditVisitFromCAnalyzer(Model model, @PathVariable("idClient") Integer idClient, HttpServletRequest request) throws ParseException {
        Visit visit = visitRepository.getById(Integer.parseInt(request.getParameter("idVisit")));
        model.addAttribute("visit", visit);
        model.addAttribute("clientServSet", visit.getClientServSet());
        model.addAttribute("visitForm", true);

        model.addAttribute("idClient", idClient);
        return "visit_form";
    }

    @GetMapping(value = "/analyzer", params = "editVisit")
    public String getEditVisitFromAnalyzer(Model model, HttpServletRequest request) throws ParseException {
        Visit visit = visitRepository.getById(Integer.parseInt(request.getParameter("idVisit")));
        model.addAttribute("visit", visit);
        model.addAttribute("idClient", visit.getClientCard().getClient().getId());
        model.addAttribute("clientServSet", visit.getClientServSet());
        model.addAttribute("visitForm", true);
        model.addAttribute("isMainAnalyzer", true);
        return "visit_form";
    }

    private String getClientAnalyzerSc(Model model, Integer idClient, HttpServletRequest request) throws ParseException {
        addDefaultAttributeForSearch(model);
        String visitsCounterString = null;
        String totalValueString = null;
        String clientServCounterString = null;
        Client client = clientRepository.getById(idClient);
        model.addAttribute("client", client);
        model.addAttribute("idClient", client.getId());
        List<Visit> visitList = doSearchFilerList(client, request);
        if (visitList.isEmpty()) {
            model.addAttribute("visitList", new ArrayList<>());


        } else {
            model.addAttribute("visitList", visitList);
            int visitsCounter = visitList.size();
            int clientServCounter = 0;
            double totalValue = 0;

            for (Visit visit : visitList) {
                totalValue += visit.getClientServSet().stream().mapToDouble(ClientServ::getFinalPriceIncludingPromotion).sum();
                clientServCounter += visit.getClientServSet().size();
            }
            if (visitsCounter != 0) {
                visitsCounterString = "Łączna ilość wizyt: " + visitsCounter;
                clientServCounterString = "Łączna ilość usług: " + clientServCounter;
                totalValueString = "Łączna wartość: " + totalValue;
            }
            if (visitsCounter != 0) {
                model.addAttribute("visitsCounterString", visitsCounterString);

            }
            if (clientServCounter != 0) {
                model.addAttribute("totalValueString", totalValueString);

            }
            if (totalValue != 0) {
                model.addAttribute("clientServCounterString", clientServCounterString);

            }
        }

        handleSearchAttribute(model, request);

        return "client_analyzer_form";
    }

    private String getAnalyzerSc(Model model, HttpServletRequest request) throws ParseException {
        addDefaultAttributeForSearch(model);
        String visitsCounterString = null;
        String totalValueString = null;
        String clientServCounterString = null;

        List<Visit> visitList = doSearchFilerList(null, request);
        if (visitList.isEmpty()) {
            model.addAttribute("visitList", new ArrayList<>());


        } else {
            model.addAttribute("visitList", visitList);
            int visitsCounter = visitList.size();
            int clientServCounter = 0;
            double totalValue = 0;

            for (Visit visit : visitList) {
                totalValue += visit.getClientServSet().stream().mapToDouble(ClientServ::getFinalPriceIncludingPromotion).sum();
                clientServCounter += visit.getClientServSet().size();
            }
            if (visitsCounter != 0) {
                visitsCounterString = "Łączna ilość wizyt: " + visitsCounter;
                clientServCounterString = "Łączna ilość usług: " + clientServCounter;
                totalValueString = "Łączna wartość: " + totalValue;
            }
            if (visitsCounter != 0) {
                model.addAttribute("visitsCounterString", visitsCounterString);

            }
            if (clientServCounter != 0) {
                model.addAttribute("totalValueString", totalValueString);

            }
            if (totalValue != 0) {
                model.addAttribute("clientServCounterString", clientServCounterString);

            }
        }

        handleSearchAttribute(model, request);

        return "analyzer_form";
    }

    @GetMapping("/clientanalyzer/{idClient}/{idVisit}")
    public String getClientAnalyzerVisit(Model model, @PathVariable("idClient") Integer idClient, @PathVariable("idVisit") Integer idVisit, HttpServletRequest request) throws ParseException {
        Visit visit = visitRepository.getById(idVisit);
        model.addAttribute("visit", visit);
        model.addAttribute("clientServSet", visit.getClientServSet());
        model.addAttribute("visitForm", true);
        IS_VISIT_FORM = Boolean.TRUE;

        return getClientAnalyzerSc(model, idClient, request);
    }

    @GetMapping("/analyzer/{idVisit}/{pageNumber}")
    public String getClientAnalyzerVisit(Model model, @PathVariable("pageNumber") int currentPage, @PathVariable("idVisit") Integer idVisit, HttpServletRequest request) throws ParseException {
        Visit visit = visitRepository.getById(idVisit);
        model.addAttribute("visit", visit);
        model.addAttribute("clientServSet", visit.getClientServSet());
        model.addAttribute("visitForm", true);
        IS_VISIT_FORM = Boolean.TRUE;

        return getAnalyzerSc(model, request);
    }

    private void addDefaultAttributeForSearch(Model model) {
        model.addAttribute("startDate", VISIT_SEARCH_START_DATE);
        model.addAttribute("endDate", LocalDate.now());
        model.addAttribute(ATTRIBUTE_SERVICE_TYPE_LIST, geActiveAndInactiveServiceTypeList());
        model.addAttribute(ATTRIBUTE_FIELDS_LIST, SEARCH_DEFAULT_FIELDS_LIST);
    }

    private void handleSearchAttribute(Model model, HttpServletRequest request) throws ParseException {
        // agreement, startDate, endDate
        handleStartEndDateAttribute(model, request);

        // serviceType
        handleServiceTypeAttribute(model, request);

        // fields
        handleFieldsAttribute(model, request);

        // search criteria
        handleCriteriaAttribute(model, request);
    }

    private void handleCriteriaAttribute(Model model, HttpServletRequest request) {
        boolean dateSearchAgreement = "on".equals(request.getParameter("dateSearchAgreement")) ? true : false;
        boolean serviceTypeSearchAgreement = "on".equals(request.getParameter("serviceTypeSearchAgreement")) ? true : false;
        boolean fieldSearchAgreement = "on".equals(request.getParameter("fieldSearchAgreement")) ? true : false;

        StringBuilder stringBuilder = new StringBuilder();
        if (dateSearchAgreement) {
            stringBuilder.append("Data");
        }

        if (serviceTypeSearchAgreement) {
            if (stringBuilder.toString().isEmpty()) {
                stringBuilder.append("Typ usługi");
            } else {
                stringBuilder.append(", Typ usługi");
            }
        }

        if (fieldSearchAgreement) {
            if (stringBuilder.toString().isEmpty()) {
                stringBuilder.append("Pole");
            } else {
                stringBuilder.append(", Pole");
            }
        }
        model.addAttribute("criteria", String.valueOf(stringBuilder));
    }

    private void handleFieldsAttribute(Model model, HttpServletRequest request) {

        String searchValue = request.getParameter("searchValue");
        model.addAttribute("searchValue", searchValue);
        boolean fieldSearchAgreement = "on".equals(request.getParameter("fieldSearchAgreement")) ? true : false;
        model.addAttribute("fieldSearchAgreement", fieldSearchAgreement);
        String fieldString = request.getParameter("field");
        if (isStringNotEmpty(fieldString)) {
            model.addAttribute("field", fieldString);
        }

        if (fieldSearchAgreement) {
            FIELD_SEARCH_AGREEMENT_VALUE = fieldSearchAgreement;
            FIELD_VALUE = fieldString;
            SEARCH_VALUE = searchValue;
        }
        if (IS_VISIT_FORM) {
            model.addAttribute("fieldSearchAgreement", FIELD_SEARCH_AGREEMENT_VALUE);
            model.addAttribute("field", FIELD_VALUE);
            model.addAttribute("searchValue", SEARCH_VALUE);
        }
    }

    private boolean isStringNotEmpty(String fieldString) {
        return fieldString != null && !"".equals(fieldString);
    }

    private void handleServiceTypeAttribute(Model model, HttpServletRequest request) {
        String serviceTypeIdString = request.getParameter("serviceTypeId");
        Integer serviceTypeId = null;
        if (isStringNotEmpty(serviceTypeIdString)) {
            serviceTypeId = Integer.parseInt(serviceTypeIdString);
            model.addAttribute("serviceTypeId", serviceTypeId);
        } else {
//            model.addAttribute("serviceTypeId", 1);
        }
        if (isStringNotEmpty(serviceTypeIdString)) {
            serviceTypeId = Integer.parseInt(serviceTypeIdString);
            model.addAttribute("serviceTypeId", serviceTypeId);
            model.addAttribute("serviceType", serviceTypeRepository.getById(serviceTypeId));
        }

        boolean serviceTypeSearchAgreement = "on".equals(request.getParameter("serviceTypeSearchAgreement")) ? true : false;
        model.addAttribute("serviceTypeSearchAgreement", serviceTypeSearchAgreement);


        if (serviceTypeSearchAgreement) {
            SERVICE_TYPE_SEARCH_AGREEMENT_VALUE = serviceTypeSearchAgreement;
            SERVICE_TYPE_ID_VALUE = serviceTypeId;
        }
        if (IS_VISIT_FORM) {
            model.addAttribute("serviceTypeSearchAgreement", SERVICE_TYPE_SEARCH_AGREEMENT_VALUE);
            model.addAttribute("serviceTypeId", SERVICE_TYPE_ID_VALUE);

            if (SERVICE_TYPE_ID_VALUE != null) {
                model.addAttribute("serviceType", serviceTypeRepository.getById(SERVICE_TYPE_ID_VALUE));
            }
        }

    }

    private void handleStartEndDateAttribute(Model model, HttpServletRequest request) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = request.getParameter("startDate");
        final LocalDate startDate = startDateString != null ? LocalDate.parse(startDateString) : null;
        String endDateString = request.getParameter("endDate");
        final LocalDate endDate = startDateString != null ? LocalDate.parse(endDateString) : null;

        if (startDate == null) {
            model.addAttribute("startDate", VISIT_SEARCH_START_DATE);
        } else {
            model.addAttribute("startDate", startDate);
        }
        if (endDate == null) {
            model.addAttribute("endDate", LocalDate.now());
        } else {
            model.addAttribute("endDate", endDate);
        }
        boolean dateSearchAgreement = "on".equals(request.getParameter("dateSearchAgreement")) ? true : false;
        model.addAttribute("dateSearchAgreement", dateSearchAgreement);


        if (dateSearchAgreement) {
            DATE_SEARCH_AGREEMENT_VALUE = dateSearchAgreement;
            START_DATE_VALUE = startDate;
            END_DATE_VALUE = endDate;

        }
        if (IS_VISIT_FORM) {
            model.addAttribute("dateSearchAgreement", DATE_SEARCH_AGREEMENT_VALUE);
            model.addAttribute("startDate", START_DATE_VALUE != null ? START_DATE_VALUE : VISIT_SEARCH_START_DATE);
            model.addAttribute("endDate", END_DATE_VALUE != null ? END_DATE_VALUE : LocalDate.now());
        }


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

    @GetMapping("/main/cform/{pageNumber}")
    public String showClientForm(Model model, @PathVariable("pageNumber") int currentPage,
                                 @RequestParam("sortField") String sortField,
                                 @RequestParam("sortDir") String sortDir) {
        return getChooseOrCreateClientForm(model, currentPage, sortField, sortDir);
    }

    private List<Visit> doSearchFilerList(Client client, HttpServletRequest request) throws ParseException {

        boolean dateSearchAgreement = "on".equals(request.getParameter("dateSearchAgreement")) ? true : false;
        boolean serviceTypeSearchAgreement = "on".equals(request.getParameter("serviceTypeSearchAgreement")) ? true : false;
        boolean fieldSearchAgreement = "on".equals(request.getParameter("fieldSearchAgreement")) ? true : false;

        String startDateString = request.getParameter("startDate");
        String endDateString = request.getParameter("endDate");
        String fieldString = request.getParameter("field");
        String searchValueString = request.getParameter("searchValue");
        String serviceTypeIdString = request.getParameter("serviceTypeId");
        int serviceTypeId = 0;
        if (isStringNotEmpty(serviceTypeIdString)) {
            serviceTypeId = Integer.parseInt(serviceTypeIdString);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        final Date startDate = startDateString != null ? formatter.parse(startDateString) : null;
        final Date endDate = endDateString != null ? formatter.parse(endDateString) : null;

        List<Visit> currentVisitList = new ArrayList<>();
        if (client != null) {
            currentVisitList = client.getClientCard().getVisitSet().stream().sorted(Comparator
                    .comparing(Visit::getId).reversed())
                    .collect(Collectors.toList());
        } else {
            currentVisitList = visitRepository.findAll().stream().sorted(Comparator
                    .comparing(Visit::getId).reversed())
                    .collect(Collectors.toList());
        }


        // FIELD SEARCH
        if (fieldSearchAgreement) {
            currentVisitList = filerListByField(currentVisitList, fieldString, searchValueString);
        }

        // DATE SEARCH
        if (dateSearchAgreement) {
            currentVisitList = filerListByDate(currentVisitList, startDate, endDate);
        }

        // SERVICE TYPE SEARCH
        if (serviceTypeSearchAgreement) {
            currentVisitList = filerListByServiceTypeId(currentVisitList, serviceTypeId);
        }
        return currentVisitList.stream().sorted(Comparator
                .comparing(Visit::getDate).reversed())
                .collect(Collectors.toList());
    }

    private List<Visit> filerListByField(List<Visit> currentVisitList, String fieldString, String searchValueString) {
        if (!currentVisitList.isEmpty() && searchValueString != null && fieldString != null && !"".equals(fieldString) && !"".equals(searchValueString)) {
            if (DEFAULT_FIELD_VISIT_TITLE.equals(fieldString)) {
                currentVisitList = currentVisitList.stream()
                        .filter(visit -> visit.getTitle().toLowerCase().contains(searchValueString.toLowerCase()))
                        .collect(Collectors.toList());
            } else if (DEFAULT_FIELD_VISIT_DESC.equals(fieldString)) {
                currentVisitList = currentVisitList.stream()
                        .filter(visit -> visit.getDesc().toLowerCase().contains(searchValueString.toLowerCase()))
                        .collect(Collectors.toList());
            } else if (DEFAULT_FIELD_VISIT_CODE.equals(fieldString)) {
                currentVisitList = currentVisitList.stream()
                        .filter(visit -> visit.getCode().toLowerCase().contains(searchValueString.toLowerCase()))
                        .collect(Collectors.toList());
            } else if (DEFAULT_FIELD_DESC.equals(fieldString)) {
                return filerListByDesc(currentVisitList, fieldString, searchValueString);
            } else if (DEFAULT_FIELD_TITLE.equals(fieldString)) {
                return filerListByTitle(currentVisitList, fieldString, searchValueString);
            }
            currentVisitList = filerListByServiceTypeFields(currentVisitList, fieldString, searchValueString);
        }
        return currentVisitList;
    }

    private List<Visit> filerListByDate(List<Visit> currentVisitList, Date startDate, Date endDate) {
        if (!currentVisitList.isEmpty() && startDate != null && endDate != null) {
            currentVisitList = currentVisitList.stream()
                    .filter(visit -> visit.getDate() != null &&
                            visit.getDate().compareTo(startDate) >= 0 &&
                            visit.getDate().compareTo(endDate) <= 0)
                    .collect(Collectors.toList());
        }
        return currentVisitList;
    }

    private List<Visit> filerListByServiceTypeId(List<Visit> currentVisitList, int serviceTypeId) {
        List<Visit> visitsToRemoveFromList = new ArrayList<>();

        if (!currentVisitList.isEmpty() && serviceTypeId > 0) {
            for (Visit visit : currentVisitList) {
                Optional<ClientServ> first = visit.getClientServSet().stream()
                        .filter(clientServ -> clientServ.getServiceType().getId() == serviceTypeId)
                        .findFirst();
                if (first.isEmpty()) {
                    visitsToRemoveFromList.add(visit);
                }
            }
        }
        if (!visitsToRemoveFromList.isEmpty()) {
            return currentVisitList.stream()
                    .filter(visit -> !visitsToRemoveFromList.contains(visit))
                    .collect(Collectors.toList());
        }
        return currentVisitList;
    }

    private List<Visit> filerListByServiceTypeFields(List<Visit> currentVisitList, String fieldString, String searchValueString) {
        List<Visit> visitsToRemoveFromList = new ArrayList<>();
        if (!currentVisitList.isEmpty() && searchValueString != null && fieldString != null && !"".equals(fieldString) && !"".equals(searchValueString)) {
            if (DEFAULT_FIELD_CLIENT_SERVICE_DESC.equals(fieldString)) {
                for (Visit visit : currentVisitList) {
                    Optional<ClientServ> first = visit.getClientServSet().stream()
                            .filter(clientServ -> clientServ.getDesc().toLowerCase().contains(searchValueString.toLowerCase()))
                            .findFirst();
                    if (first.isEmpty()) {
                        visitsToRemoveFromList.add(visit);
                    }
                }
            }
            if (DEFAULT_FIELD_CLIENT_SERVICE_TITLE.equals(fieldString)) {
                for (Visit visit : currentVisitList) {
                    Optional<ClientServ> first = visit.getClientServSet().stream()
                            .filter(clientServ -> clientServ.getTitle().toLowerCase().contains(searchValueString.toLowerCase()))
                            .findFirst();
                    if (first.isEmpty()) {
                        visitsToRemoveFromList.add(visit);
                    }
                }
            }
        }
        if (!visitsToRemoveFromList.isEmpty()) {
            currentVisitList = currentVisitList.stream()
                    .filter(visit -> !visitsToRemoveFromList.contains(visit))
                    .collect(Collectors.toList());
        }
        return currentVisitList;
    }

    private List<Visit> filerListByDesc(List<Visit> currentVisitList, String fieldString, String searchValueString) {
        List<Visit> visitsToRemoveFromList = new ArrayList<>();
        List<Visit> visitListAfterClientServiceFilter;
        List<Visit> visitAfterVisitFilter = new ArrayList<>();
        Set<Visit> visitAfterDescFilterSet = new HashSet<>();

        if (!currentVisitList.isEmpty() && searchValueString != null && fieldString != null && !"".equals(fieldString) && !"".equals(searchValueString)) {
            if (DEFAULT_FIELD_DESC.equals(fieldString)) {
                // 1 action
                for (Visit visit : currentVisitList) {
                    Optional<ClientServ> first = visit.getClientServSet().stream()
                            .filter(clientServ -> clientServ.getDesc().toLowerCase().contains(searchValueString.toLowerCase()))
                            .findFirst();
                    if (first.isEmpty()) {
                        visitsToRemoveFromList.add(visit);
                    }
                }

                // 2 action
                visitAfterVisitFilter = currentVisitList.stream()
                        .filter(visit -> visit.getDesc().toLowerCase().contains(searchValueString.toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        // 1 action
        if (!visitsToRemoveFromList.isEmpty()) {
            visitListAfterClientServiceFilter = currentVisitList.stream()
                    .filter(visit -> !visitsToRemoveFromList.contains(visit))
                    .collect(Collectors.toList());
        } else {
            visitListAfterClientServiceFilter = currentVisitList.stream().collect(Collectors.toList());
        }

        if (!visitAfterVisitFilter.isEmpty()) {
            visitAfterVisitFilter.stream().forEach(visit -> visitAfterDescFilterSet.add(visit));
        }
        if (!visitListAfterClientServiceFilter.isEmpty()) {
            visitListAfterClientServiceFilter.stream().forEach(visit -> visitAfterDescFilterSet.add(visit));
        }
        return visitAfterDescFilterSet.stream().collect(Collectors.toList());
    }

    private List<Visit> filerListByTitle(List<Visit> currentVisitList, String fieldString, String searchValueString) {
        List<Visit> visitsToRemoveFromList = new ArrayList<>();
        List<Visit> visitListAfterClientServiceFilter;
        List<Visit> visitAfterVisitFilter = new ArrayList<>();
        Set<Visit> visitAfterTitleFilterSet = new HashSet<>();

        if (!currentVisitList.isEmpty() && searchValueString != null && fieldString != null && !"".equals(fieldString) && !"".equals(searchValueString)) {
            if (DEFAULT_FIELD_TITLE.equals(fieldString)) {
                // 1 action
                for (Visit visit : currentVisitList) {
                    Optional<ClientServ> first = visit.getClientServSet().stream()
                            .filter(clientServ -> clientServ.getTitle().toLowerCase().contains(searchValueString.toLowerCase()))
                            .findFirst();
                    if (first.isEmpty()) {
                        visitsToRemoveFromList.add(visit);
                    }
                }

                // 2 action
                visitAfterVisitFilter = currentVisitList.stream()
                        .filter(visit -> visit.getTitle().toLowerCase().contains(searchValueString.toLowerCase()))
                        .collect(Collectors.toList());
            }
        }
        // 1 action
        if (!visitsToRemoveFromList.isEmpty()) {
            visitListAfterClientServiceFilter = currentVisitList.stream()
                    .filter(visit -> !visitsToRemoveFromList.contains(visit))
                    .collect(Collectors.toList());
        } else {
            visitListAfterClientServiceFilter = currentVisitList.stream().collect(Collectors.toList());
        }

        if (!visitAfterVisitFilter.isEmpty()) {
            visitAfterVisitFilter.stream().forEach(visit -> visitAfterTitleFilterSet.add(visit));
        }
        if (!visitListAfterClientServiceFilter.isEmpty()) {
            visitListAfterClientServiceFilter.stream().forEach(visit -> visitAfterTitleFilterSet.add(visit));
        }
        return visitAfterTitleFilterSet.stream().collect(Collectors.toList());
    }


    private List<ServiceType> geActiveAndInactiveServiceTypeList() {
        List<ServiceType> allActiveServiceTypeList = serviceTypeRepository.findAllActiveAndInactive();
        return allActiveServiceTypeList;
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
        model.addAttribute("client", new Client(DEFAULT_CLIENT_DESC));
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
        if (client.getCode() == null || "".equals(client.getCode())) {
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
        model.addAttribute("client", new Client(DEFAULT_CLIENT_DESC));
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
        LocalDate date = LocalDate.parse(request.getParameter(LAST_VISIT_DATE).substring(0, 10));

        Period period = Period.between(date, LocalDate.now());
        if (period.getYears() < 5) {
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

    private void clearStiticFieldsForAnalyzer() {
        IS_VISIT_FORM = Boolean.FALSE;
        DATE_SEARCH_AGREEMENT_VALUE = null;
        SERVICE_TYPE_SEARCH_AGREEMENT_VALUE = null;
        FIELD_SEARCH_AGREEMENT_VALUE = null;
        START_DATE_VALUE = null;
        END_DATE_VALUE = null;
        SERVICE_TYPE_ID_VALUE = null;
    }

}
