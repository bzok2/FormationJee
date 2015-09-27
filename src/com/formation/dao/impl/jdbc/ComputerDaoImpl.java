/*
 * ComputerDAoImpl implements computer  
 * auteur:Bzo
 */
package com.formation.dao.impl.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.formation.dao.ComputerDao;

import com.formation.dao.utils.DaoUtils;
import com.formation.entity.Company;
import com.formation.entity.Computer;
import com.formation.exception.DAOException;

/*
 * database JDBC connection
 * auteur:Bzo
 */
public class ComputerDaoImpl implements ComputerDao {
	
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/computer-database-db?zeroDateTimeBehavior=convertToNull";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	
	private static ComputerDao INSTANCE = null;
	private int noOfRecords;
	
	private ComputerDaoImpl(){
	}
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			throw new RuntimeException("Can not load Driver", e);
		}
	}
	
	@Override
	public List<Computer> getAll(int startPageIndex, int noOfRecords, String terme) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ResultSet rs = null;
		String sql;
		if(terme != null && terme.length()> 0 )
			 sql = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id WHERE ( computer.name LIKE '"+terme+"' OR computer.introduced LIKE '"+terme+"' OR computer.discontinued LIKE '"+terme+"' OR company.name LIKE '"+terme+"' ) limit "+startPageIndex+","+noOfRecords;
		else
			 sql = "SELECT * FROM computer LEFT JOIN company ON computer.company_id=company.id limit "+startPageIndex+","+noOfRecords;
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery(sql);
			List<Computer> ComputerList = new ArrayList<Computer>();
			String nul = "N/A";
			while (resultSet.next()) 
			{
				Company company = new Company();
					company.setId(resultSet.getLong("company_id"));	
					
					 if (resultSet.getString("company.name") != null) 
						 company.setCompany_name(resultSet.getString(7));
					 else
						 company.setCompany_name(nul);					
				
				Computer computer = Computer.builder()
						.setId(resultSet.getLong("id"))
						.setName(resultSet.getString("name"))
						.setIntroduced(resultSet.getDate("introduced"))
						.setDiscontinued(resultSet.getDate("discontinued"))
						.setCompany(company)						
						.build();
				ComputerList.add(computer);
			}
			resultSet.close();
		
			
			resultSet = statement.executeQuery("SELECT count(*) FROM computer;");
			if(resultSet.next())
				this.noOfRecords = resultSet.getInt(1);
			
			return ComputerList;
		} 
		
		catch (SQLException e) {
			throw new DAOException("erreur requête", e);
		} finally {
			DaoUtils.closeAll(resultSet, statement, connection);
		}
	}
	
	//fonction retournant le nombre de pc
	public int getNoOfRecords() {
		return noOfRecords;
	}
	
	//fonction affichage liste
	@Override
	public List<Company> getAllCompany() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.createStatement();
			String sql = "SELECT * FROM company ORDER BY name ASC";
			resultSet = statement.executeQuery(sql);
			List<Company> CompanyList = new ArrayList<Company>();
			
			while (resultSet.next()) {
				Company company = new Company();
					company.setId(resultSet.getLong("id"));	
					company.setCompany_name(resultSet.getString("name"));	
								
				CompanyList.add(company);
			}
			return CompanyList;
		} catch (SQLException e) {
			throw new DAOException("TODO better message", e);
		} finally {
			DaoUtils.closeAll(resultSet, statement, connection);
		}
	}



	public static ComputerDao getInstance() {

		if(INSTANCE == null) {
			INSTANCE = new ComputerDaoImpl();
		}
		return INSTANCE;
	}
	
	//fonction insertion ordinateur
	@Override
	public void insert(Computer computer, int company_id) {
		
		Connection connection = null;
		PreparedStatement statement = null;
		//ResultSet resultSet = null;
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.prepareStatement("INSERT INTO computer (id,name, introduced, discontinued,company_id) VALUES (NULL,?,?,?,?)");
			statement.setString(1, computer.getName());
			statement.setDate(2, (Date) convertUtilToSql(computer.getIntroduced()));
			statement.setDate(3, (Date) convertUtilToSql(computer.getDiscontinued()));
			statement.setLong(4, company_id);
			statement.executeUpdate(); 	
			} catch (SQLException e) {
				throw new DAOException("erreur création utilisateur", e);
			}	 finally {
				DaoUtils.closeAll(null, statement, connection);
			}
	}
	
	//fonction insertion company
		@Override
		public void insertCompany(String nom_company) {
			
			Connection connection = null;
			PreparedStatement statement = null;
			//ResultSet resultSet = null;
			try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				statement = connection.prepareStatement("INSERT INTO company (id,name) VALUES (NULL,?)");
				statement.setString(1, nom_company);
				statement.executeUpdate(); 	
				} catch (SQLException e) {
					throw new DAOException("erreur création utilisateur", e);
				}	 finally {
					DaoUtils.closeAll(null, statement, connection);
				}
		}
		
	
	
	//convertion java.util.date to	java.sql.date 
	private static java.sql.Date convertUtilToSql(java.util.Date uDate) {		
		        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		        return sDate;
		    }
	
	//fonction suppresion
	public void delete ( int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			statement = connection.prepareStatement("DELETE FROM computer WHERE ID ="+id+";");
			statement.executeUpdate(); 	
			} catch (SQLException e) {
				throw new DAOException("erreur suppression ordinateur", e);
			}	 finally {
				DaoUtils.closeAll(null, statement, connection);
			}
		
	}
	
	//fonction modification ordinateur
	public void update ( Computer computer, int company_id, int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
				statement = connection.prepareStatement("UPDATE computer SET name=?, introduced=?, discontinued=?,company_id=? WHERE id="+id+";");
				statement.setString(1, computer.getName());
				statement.setDate(2, (Date) convertUtilToSql(computer.getIntroduced()));
				statement.setDate(3, (Date) convertUtilToSql(computer.getDiscontinued()));
				statement.setLong(4, company_id);
				statement.executeUpdate(); 				
			} catch (SQLException e) {
				throw new DAOException("erreur mise à jour ordinateur", e);
			}	 finally {
				DaoUtils.closeAll(null, statement, connection);
			}
	}
	


}
