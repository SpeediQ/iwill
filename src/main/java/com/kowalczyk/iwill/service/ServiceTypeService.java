package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.model.ConstanceNr;
import com.kowalczyk.iwill.model.ServiceType;
import com.kowalczyk.iwill.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeService {
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    public Page<ServiceType> findAllServiceTypePage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, ConstanceNr.MAX_SERVICE_TYPE_LIST_SIZE_10);
        return serviceTypeRepository.findAll(pageable);
    }

    public Page<ServiceType> findAllActiveServiceTypePage(int pageNumber, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, ConstanceNr.MAX_SERVICE_TYPE_LIST_SIZE_5, sort);
        return serviceTypeRepository.findAllByStatusId(ConstanceNr.STATUS_SERVICE_TYPE, pageable);
    }

    public Page<ServiceType> findAllSortedServiceTypePage(int pageNumber, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, ConstanceNr.MAX_SERVICE_TYPE_LIST_SIZE_10, sort);
        return serviceTypeRepository.findAll(pageable);
    }

    public Page<ServiceType> findAllActiveSortedServiceTypePage(int pageNumber, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, ConstanceNr.MAX_SERVICE_TYPE_LIST_SIZE_5, sort);
        return serviceTypeRepository.findAllByStatusId(ConstanceNr.STATUS_SERVICE_TYPE, pageable);
    }


}
