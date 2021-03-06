package com.wangbz.examples.sn.dao;

import com.wangbz.examples.sn.model.CompanySequence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanySequenceDao extends JpaRepository<CompanySequence, String> {

    CompanySequence findByTypeAndCompany(String type, String company);
}
