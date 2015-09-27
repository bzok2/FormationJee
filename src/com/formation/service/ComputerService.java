package com.formation.service;

import java.util.List;

import com.formation.entity.Company;
import com.formation.entity.Computer;


public interface ComputerService {
	
	List<Computer> getAll(int startPageIndex, int recordsPerPage, String terme);
	
	List<Company> getAllCompany();
	
	void insert(Computer computer, int company_id);
	
	void insertCompany(String nom_company);
	
	void delete( int computer_id);
	
	void update( Computer computer, int company_id, int computer_id);

    int getNoOfRecords();
}
