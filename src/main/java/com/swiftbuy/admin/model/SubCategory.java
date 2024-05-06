package com.swiftbuy.admin.model;

//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//
//@Entity
//public class SubCategory {
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long subcategory_id;
//	
//    public Long getSubcategory_id() {
//		return subcategory_id;
//	}
//
//	public void setSubcategory_id(Long subcategory_id) {
//		this.subcategory_id = subcategory_id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//
//	private String name;
//
//    @ManyToOne(cascade=CascadeType.REFRESH,fetch = FetchType.EAGER)
//    @JoinColumn(name = "category_id", nullable = false)
//    private Category category;
//
//	public Category getCategory() {
//		return category;
//	}
//
//	public void setCategory(Category category) {
//		this.category = category;
//	}
//}
import jakarta.persistence.*;

@Entity
@Table(name = "productsubcategory")
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    
    
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Constructors, getters, and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }
 
    public void setCategory(Category category) {
        this.category = category;
    }
}