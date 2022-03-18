package com.kowalczyk.iwill.repositories;

import com.kowalczyk.iwill.model.JobInterview;
import com.kowalczyk.iwill.model.Person;

import java.util.List;
import java.util.Optional;

public interface JobInterviewRepository {

    List<JobInterview> findAll();
    JobInterview save(JobInterview entity);
    Optional<JobInterview> findById(Long id);

    boolean existsById(Long id);
}
