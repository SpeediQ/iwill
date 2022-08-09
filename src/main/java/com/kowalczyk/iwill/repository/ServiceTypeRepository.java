package com.kowalczyk.iwill.repository;


import com.kowalczyk.iwill.model.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {

    @Query("select distinct st from ServiceType st where st.status.id = 6")
    List<ServiceType> findAllActive();

    @Query("select distinct st from ServiceType st where st.status.id = 6 or st.status.id = 8")
    List<ServiceType> findAllActiveAndInactive();

    Page<ServiceType> findAllByStatusId(int statusId, Pageable pageable);
}
