/*
 * Computer servlet, servlet qui gère les entrées dans l'application web 
 * auteur:BZO.
 */
package com.formation.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.formation.entity.Computer;
import com.formation.service.ComputerService;
import com.formation.service.impl.ComputerServiceImpl;


@WebServlet("/ComputerServlet")
public class ComputerServlet extends HttpServlet {
	
	private ComputerService computerService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ComputerServlet() {
		super();
		computerService = ComputerServiceImpl.getInstance();
	}
	
	/*
	 * Initialisation des variables pour le compte de page, page est le numero du premier indice et recordsPerPage
	 * le nombre à afficher sur chaque Page.
	 */
	int page = 1;
	int recordsPerPage = 40;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	/*
	 * Fonction doGet lancé aux demarrage.
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
	
		
		if(request.getParameter("page") != null)
			page = Integer.parseInt(request.getParameter("page"));
		
		request.setAttribute("computers", computerService.getAll((page - 1 )*recordsPerPage,recordsPerPage,request.getParameter("terme")));
		request.setAttribute("companys", computerService.getAllCompany());
		
		int noOfRecords = computerService.getNoOfRecords();
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		
		/*
		 * mise à jour des pages.
		 */
		request.setAttribute("noOfPages", noOfPages);
		request.setAttribute("currentPage", page);
		
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				response.encodeURL("/WEB-INF/jsp/displayComputer.jsp"));
		rd.forward(request, response);
	}
	
	/*
	 * fonction post
	 */
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		
		
		/*
		 * En cas d'un post venant de l'ajout d'ordinateur
		 */
		if (request.getParameter("ajouter") != null) {
			
			String name = (String) request.getParameter("name");
			String introduced = (String) request.getParameter("introduced");
			String discontinued = (String) request.getParameter("discontinued");
			String company_name = (String) request.getParameter("company_id");
			
			
			Computer computer = new Computer();
			
			computer.setName(name);
			computer.setIntroduced(ConvertToDate(introduced)); 
			computer.setDiscontinued(ConvertToDate(discontinued)); 
			
			
			computerService.insert(computer, Integer.parseInt(company_name)); 
		}
		
		/*
		 * En cas d'un post venant de l'ajout d'une companie
		 */
		else if (request.getParameter("ajouterCompany") != null) {
			
			String name = (String) request.getParameter("name");
				
			computerService.insertCompany(name); 
		}
		
		/*
		 * En cas d'un post venant de la modification d'ordinateur
		 */
		
		else if (request.getParameter("modifier") != null) {
			
			String id = (String) request.getParameter("id");
			String name = (String) request.getParameter("name");
			String introduced = (String) request.getParameter("introduced");
			String discontinued = (String) request.getParameter("discontinued");
			String company_name = (String) request.getParameter("company_id");
			
			
			Computer computer = new Computer();
			
			computer.setName(name);
			computer.setIntroduced(ConvertToDate(introduced)); 
			computer.setDiscontinued(ConvertToDate(discontinued)); 
			
			
			computerService.update(computer, Integer.parseInt(company_name), Integer.parseInt(id));     
			
		}
		
		/*
		 * En cas d'un post venant de la suppresion d'un ordinateur
		 */
		else if (request.getParameter("supprimer") != null) {
		 
			String id = (String) request.getParameter("id");
			
			computerService.delete(Integer.parseInt(id)); 
		}
				
		/*
		 * rappel de la fonction get pour rafraichir la page.
		 * 		 */
		
		if(request.getParameter("page") != null || request.getParameter("normalSearch") != null)
			page = Integer.parseInt(request.getParameter("page"));
		
		request.setAttribute("computers", computerService.getAll((page - 1 )*recordsPerPage,recordsPerPage,request.getParameter("terme")));
		request.setAttribute("companys", computerService.getAllCompany());
		
		int noOfRecords = computerService.getNoOfRecords();
		int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
		
		//request.setAttribute("employeeList", list);
		request.setAttribute("noOfPages", noOfPages);
		request.setAttribute("currentPage", page);
		
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				response.encodeURL("/WEB-INF/jsp/displayComputer.jsp"));
		rd.forward(request, response);
	}
	
	/*
	 * Petite fonction pour convertir les dates au bon format
	 */
	
	public Date ConvertToDate(String dateString){
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    Date convertedDate = new Date();
	    try {
	        convertedDate = dateFormat.parse(dateString);
	    } catch (ParseException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return convertedDate;
	}
	
	
}
