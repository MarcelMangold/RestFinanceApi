package com.mysticalducks.rest.finance.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "party")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false, length = 128)
    private String name;

    @ManyToOne
    @JoinColumn(name = "finance_information_id", nullable = false)
    private FinanceInformation financeInformation;

    public Party() {}

    public Party(int id, String name, FinanceInformation financeInformation) {
        this.id = id;
        this.name = name;
        this.financeInformation = financeInformation;
    }

    public Party(String name, FinanceInformation financeInformation) {
        this.name = name;
        this.financeInformation = financeInformation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FinanceInformation getfinanceInformation() {
        return financeInformation;
    }

    public void setfinanceInformation(FinanceInformation financeInformation) {
        this.financeInformation = financeInformation;
    }
}
