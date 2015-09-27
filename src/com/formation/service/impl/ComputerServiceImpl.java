package com.formation.service.impl;

import java.util.List;
import com.formation.dao.ComputerDao;
import com.formation.dao.impl.jdbc.ComputerDaoImpl;
import com.formation.entity.Company;
import com.formation.entity.Computer;
import com.formation.service.ComputerService;



public class ComputerServiceImpl implements ComputerService{
	
	private ComputerDao computerDao;
	
	private static ComputerService INSTANCE = null;
	
	private ComputerServiceImpl() {
		this.computerDao = ComputerDaoImpl.getInstance();
	}
	
	@Override
	public List<Computer> getAll(int startPageIndex, int recordsPerPage, String terme) {
		return computerDao.getAll(startPageIndex, recordsPerPage, terme);
	}
	
	@Override
	public List<Company> getAllCompany() {
		return computerDao.getAllCompany();
	}
	
	public static ComputerService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ComputerServiceImpl();
		}
		return INSTANCE;
	}
	
	@Override
	public void insert(Computer computer, int company_id) {
		
		computerDao.insert(computer, company_id);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void insertCompany(String nom_company) {
		
		computerDao.insertCompany(nom_company);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete( int id) {
		
		computerDao.delete(id);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update( Computer computer, int company_id, int computer_id) {
		
		computerDao.update(computer, company_id, computer_id);
		// TODO Auto-generated method stub
		
	}
	
	//fonction retournant le nombre de pc
		public int getNoOfRecords() {
			return computerDao.getNoOfRecords();
		}
}
