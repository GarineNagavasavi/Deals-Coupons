package com.adminsecurity.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

// Represents a Coupon entity in a SQL-based database (e.g., MySQL)
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generate the couponId
    private String couponId; // Changed from Long to String

    @NotBlank(message = "Category is required")
    @Column(name = "category", nullable = false)
    private String category;

//    @Positive(message = "Count must be a positive number")
//    @Column(name = "count", nullable = false)
//    private int count;

    @Positive(message = "Price must be a positive number")
    @Column(name = "price", nullable = false)
    private int price;

    @NotBlank(message = "Offer is required")
    @Column(name = "offer", nullable = false)
    private String offer;

    @NotBlank(message = "Company name is required")
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @NotBlank(message = "Expiry date is required")
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    // Default constructor
    public Coupon() {
        super();
    }

    // Constructor with parameters
    public Coupon(String category/*, int count*/, int price, String offer, String companyName, LocalDate expiryDate) {
        this.category = category;
//        this.count = count;
        this.price = price;
        this.offer = offer;
        this.companyName = companyName;
        this.expiryDate = expiryDate;
    }

    // Getters and setters
    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

//    public int getCount() {
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    // ToString method to represent object as string
    @Override
    public String toString() {
        return String.format("Coupon[couponId='%s', category='%s', price='%d', offer='%s', companyName='%s', expiryDate='%s']",
                couponId, category,/* count,*/ price, offer, companyName, expiryDate);
    }
}
