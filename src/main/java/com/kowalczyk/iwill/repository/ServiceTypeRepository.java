package com.kowalczyk.iwill.repository;


import com.kowalczyk.iwill.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {
}
