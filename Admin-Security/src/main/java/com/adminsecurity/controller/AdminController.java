package com.adminsecurity.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.adminsecurity.model.Admin;
import com.adminsecurity.model.Coupon;
import com.adminsecurity.model.Deal;
import com.adminsecurity.service.AdminServiceImpl;


@RestController
@RequestMapping("/admin")
@CrossOrigin(origins="*")
public class AdminController {
    
    private final AdminServiceImpl service;
    private final RestTemplate restTemplate;

    @Autowired
    public AdminController(AdminServiceImpl service, RestTemplate restTemplate) {
        this.service = service;
        this.restTemplate = restTemplate;
    }

    // Admin service endpoints
    @PostMapping("/add")
    public ResponseEntity<Admin> addAdmin(@RequestBody @Valid Admin admin) {
        Admin addedAdmin = service.addAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedAdmin);
    }
    
    

    @GetMapping("/getAdmin/{adminId}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("adminId") String adminId) {
        Admin admin = service.findById(adminId);
        return ResponseEntity.ok(admin);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<Admin> updateById(@PathVariable("id") String id, @RequestBody @Valid Admin updatedAdmin) {
        Admin admin = service.updateById(id, updatedAdmin);
        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/deleteAdmin/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable("id") String id) {
        service.deleteById(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }

 // Coupon service endpoints
 // Coupon service endpoints
    @GetMapping("/getAllCoupons")
    public ResponseEntity<String> getAllCoupons() {
        try {
            String coupons = restTemplate.getForObject("http://localhost:8081/coupons/findAll", String.class);
            return ResponseEntity.ok(coupons);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getCouponById/{couponId}")
    public ResponseEntity<String> getCouponById(@PathVariable("couponId") String couponId) {
        try {
            String coupon = restTemplate.getForObject("http://localhost:8081/coupons/findById/{couponId}", String.class, couponId);
            if (coupon != null) {
                return ResponseEntity.ok(coupon);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/addCoupon")
    public ResponseEntity<String> addCoupon(@RequestBody @Valid Coupon coupon) {
        try {
            restTemplate.postForObject("http://localhost:8081/coupons/add", coupon, String.class);
            return ResponseEntity.status(HttpStatus.CREATED).body("Coupon added successfully");
        } catch (HttpServerErrorException.InternalServerError e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add coupon: Internal server error");
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add coupon: " + e.getMessage());
        }
    }

    @PutMapping("/updateCoupon/{couponId}")
    public ResponseEntity<String> updateCoupon(@PathVariable("couponId") String couponId, @RequestBody @Valid Coupon updatedCoupon) {
        try {
            restTemplate.put("http://localhost:8081/coupons/updateById/{couponId}", updatedCoupon, couponId);
            return ResponseEntity.ok("Coupon updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update coupon");
        }
    }

    @DeleteMapping("/deleteCoupon/{couponId}")
    public ResponseEntity<String> deleteCoupon(@PathVariable("couponId") String couponId) {
        try {
            restTemplate.delete("http://localhost:8081/coupon/deleteByCouponId/{couponId}", couponId);
            return ResponseEntity.ok("Coupon deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete coupon");
        }
    }

    // Deal service endpoints
    @GetMapping("/getAllDeals")
    public ResponseEntity<String> getAllDeals() {
        try {
            String deals = restTemplate.getForObject("http://localhost:8082/deals/findAll", String.class);
            return ResponseEntity.ok(deals);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getDealById/{dealId}")
    public ResponseEntity<String> getDealById(@PathVariable("dealId") String dealId) {
        try {
            String deal = restTemplate.getForObject("http://localhost:8082/deals/findById/{dealId}", String.class, dealId);
            if (deal != null) {
                return ResponseEntity.ok(deal);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/addDeal")
    public ResponseEntity<String> addDeal(@RequestBody @Valid Deal deal) {
        try {
            restTemplate.postForObject("http://localhost:8082/deals/add", deal, String.class);
            return ResponseEntity.status(HttpStatus.CREATED).body("Deal added successfully");
        } catch (HttpServerErrorException.InternalServerError e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add deal: Internal server error");
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add deal: " + e.getMessage());
        }
    }

    @PutMapping("/updateDeal/{dealId}")
    public ResponseEntity<String> updateDeal(@PathVariable("dealId") String dealId, @RequestBody @Valid Deal updatedDeal) {
        try {
            restTemplate.put("http://localhost:8082/deals/updateById/{dealId}", updatedDeal, dealId);
            return ResponseEntity.ok("Deal updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update deal");
        }
    }

    @DeleteMapping("/deleteDeal/{dealId}")
    public ResponseEntity<String> deleteDeal(@PathVariable("dealId") String dealId) {
        try {
            restTemplate.delete("http://localhost:8082/deals/deleteById/{dealId}", dealId);
            return ResponseEntity.ok("Deal deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete deal");
        }
    }
}
