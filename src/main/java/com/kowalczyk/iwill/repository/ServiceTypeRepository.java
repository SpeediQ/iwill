package com.kowalczyk.iwill.repository;


import com.kowalczyk.iwill.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {

    @Query("select distinct st from ServiceType st where st.status.id = 6")
    List<ServiceType> findAllActive();
}
