package com.kowalczyk.iwill.adapter;

import com.kowalczyk.iwill.model.JobInterview;
import com.kowalczyk.iwill.repositories.JobInterviewRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlJobInterviewRepository extends JobInterviewRepository, JpaRepository<JobInterview, Long> {


}
