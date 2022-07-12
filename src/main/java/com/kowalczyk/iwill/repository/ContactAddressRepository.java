package com.kowalczyk.iwill.repository;


import com.kowalczyk.iwill.model.ContactAddress;
import com.kowalczyk.iwill.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactAddressRepository extends JpaRepository<ContactAddress, Integer> {
}
