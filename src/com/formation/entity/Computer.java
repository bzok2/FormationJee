/*
 * entity computer 
 * auteur:Bzo
 */
package com.formation.entity;

import java.util.Date;


public class Computer {

	private Long id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;

	public Computer() {
	}

	public Computer(Long id, String name, Date introduced, Date discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	
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

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date date) {
		this.discontinued = date;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public static Builder builder(){
		return new Builder();
	}
	

	public static class Builder {

		private Computer computer;
		

		private Builder() {
			computer = new Computer();
		}

		public Builder setId(Long id) {
			computer.id = id;
			return this;
		}

		public Builder setName(String name) {
			computer.name = name;
			return this;
		}

		public Builder setIntroduced(Date introduced) {
			computer.introduced = introduced;
			return this;
		}

		public Builder setDiscontinued(Date discontinued) {
			computer.discontinued = discontinued;
			return this;
		}

		public Builder setCompany(Company company) {
			computer.company = company;
			return this;
		}

		public Computer build() {
			return computer;
		}


	}

	/*@Override
	public String toString() {
		return "COmputer [id=" + id + ", name=" + name + ", date entrée=" + introduced + ", date sortie ="
				+ discontinued + ", company =" + company.getName() + " ]";
	}*/

}
