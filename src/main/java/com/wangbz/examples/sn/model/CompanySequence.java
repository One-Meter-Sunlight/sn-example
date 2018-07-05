package com.wangbz.examples.sn.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "company_sequence")
public class CompanySequence {

    @Id
    @Column(name = "id", length = 36, columnDefinition = "char(36)")
    private String id;

    @Column(name = "type", length = 10)
    private String type;

    @Column(name = "company", length = 36)
    private String company;

    @Column(name = "current_date", length = 36)
    private String currentDate;

    @Column(name = "current_sequence")
    private Long currentSequence;

    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
}
