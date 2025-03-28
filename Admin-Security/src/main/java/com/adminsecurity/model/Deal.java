package com.adminsecurity.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

// Represents a Deal entity in a SQL-based database (e.g., MySQL)
@Entity
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate the dealId
    private Long dealId;

    @NotBlank(message = "Name is required")
    @Column(name = "name", nullable = false)
    private String name;

    @Positive(message = "Price must be a positive number")
    @Column(name = "price", nullable = false)
    private int price;

    @NotBlank(message = "Company name is required")
    @Column(name = "company_name", nullable = false)
    private String companyName;

    // Default constructor
    public Deal() {
        super();
    }

    // Constructor with parameters
    public Deal(String name, int price, String companyName) {
        this.name = name;
        this.price = price;
        this.companyName = companyName;
    }

    // Getters and setters
    public Long getDealId() {
        return dealId;
    }

    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    // ToString method to represent the object as a string
    @Override
    public String toString() {
        return String.format("Deal[dealId='%s', name='%s', price='%d', companyName='%s']",
                dealId, name, price, companyName);
    }
}
