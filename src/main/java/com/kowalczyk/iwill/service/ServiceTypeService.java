package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.model.ConstanceNr;
import com.kowalczyk.iwill.model.ServiceType;
import com.kowalczyk.iwill.repository.ServiceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeService {
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    public Page<ServiceType> findAllServiceTypePage(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber -1 , ConstanceNr.MAX_SERVICE_TYPE_LIST_SIZE_10);
        return serviceTypeRepository.findAll(pageable);
    }
    public Page<ServiceType> findAllActiveServiceTypePage(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber -1 , ConstanceNr.MAX_SERVICE_TYPE_LIST_SIZE_5);
        return serviceTypeRepository.findAllByStatusId(6,pageable);
    }


}
