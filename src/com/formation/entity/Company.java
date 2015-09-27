/*
 * entity company 
 * auteur:Bzo
 */
package com.formation.entity;


public class Company {
	
	private Long id;
	private String company_name;
	
	public Company () {	
	}
	
	//classe tostring pour surcharger l'objet
			public String toString()
				{
					return this.company_name; 
				}
			
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public Company(Long id, String company_name) {
		this.id = id;
		this.company_name = company_name;
	}

}
