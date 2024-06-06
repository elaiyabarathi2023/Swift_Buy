
 
 
package com.swiftbuy.admin.model.CustomerServiceCategory;
 
 
import jakarta.persistence.*;
 
import java.util.ArrayList;

import java.util.List;
 
import com.swiftbuy.admin.model.CustomerServiceSubCategory.CustomerServiceSubCategory;
 
 
//postman Query:

//{

//    "name": "New SubCategory Nssmsm,ame fm,eiemxmx,mc,m",

//    "description": "Description dkldljkdxsm,sd,mds,m of the new SubCategory",

//}

@Entity

@Table(name = "customer_service_categories_partilly")

public class CustomerServiceCategory {

 
	@Id

	@Column(name = "category_id")

	@GeneratedValue(strategy=GenerationType.AUTO)

	private Long cscategoryid;

	public Long getCscategoryid() {

		return cscategoryid;

	}
 
	public void setCscategoryid(Long cscategoryid) {

		this.cscategoryid = cscategoryid;

	}
 
	public String getName() {

		return name;

	}
 
	public void setName(String name) {

		this.name = name;

	}
 
	public String getDescription() {

		return description;

	}
 
	public void setDescription(String description) {

		this.description = description;

	}
 
	@Column(nullable = false)

	private String name;

  @Column(nullable = false)

  private String description;
 
	

}
 
 
 
 