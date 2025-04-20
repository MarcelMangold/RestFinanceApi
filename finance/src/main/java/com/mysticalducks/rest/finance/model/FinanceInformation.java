package com.mysticalducks.rest.finance.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "finance_information")
public class FinanceInformation {

	@Id
	@Column(unique = true, nullable = false)
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "budget", nullable = false)
    private Double budget = 1000.00;

    public FinanceInformation() {
    }
    
    public FinanceInformation(int id, Double budget) {
    	this.id = id;
        this.budget = budget;
    }

    public FinanceInformation(Double budget) {
        this.budget = budget;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }
}
