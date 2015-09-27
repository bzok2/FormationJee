/*
 * DAo interface for computer 
 * auteur:Bzo
 */
package com.formation.dao;

import java.util.List;

import com.formation.entity.Company;
import com.formation.entity.Computer;


public interface ComputerDao {
	
	List<Computer> getAll(int startPageIndex, int recordsPerPage,String terme);
	
	List<Company> getAllCompany();
	
	void delete (int computer_id);
	
	void update (Computer computer, int company_id, int computer_id);
	
	void insert (Computer computer, int company_id);
	
	void insertCompany (String nom_company);
	
	int getNoOfRecords();

}
