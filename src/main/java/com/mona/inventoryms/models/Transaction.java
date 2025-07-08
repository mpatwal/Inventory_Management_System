package com.mona.inventoryms.models;


import com.mona.inventoryms.security.Auditable;
import lombok.Data;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
@Data
public class Transaction extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orderid", insertable = false, updatable = false)
    private Order order;
    public Long orderid;

    private String transactionCode;

    @ManyToOne
    @JoinColumn(name = "transactiontypeid", insertable = false, updatable = false)
    private CommonObject transactionType;
    private Long transactiontypeid;

    @ManyToOne
    @JoinColumn(name = "transactionmodeid", insertable = false, updatable = false)
    private CommonObject transactionMode;
    private Long transactionmodeid;

    @ManyToOne
    @JoinColumn(name = "transactionstatusid", insertable = false, updatable = false)
    private CommonObject transactionStatus;
    private Long transactionstatusid;

    @Column(columnDefinition = "TEXT")
    private String content;
}