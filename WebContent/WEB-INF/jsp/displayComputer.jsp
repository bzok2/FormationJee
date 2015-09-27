<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ page import="com.formation.entity.*"%>
    
      <link type="text/css" rel="stylesheet" href="<c:url value="/CSS/popup.css" />" />  
   		<link type="text/css" rel="stylesheet" href="<c:url value="/fonts/font-awesome/css/font-awesome.css" />" />
   		<link type="text/css" rel="stylesheet" href="<c:url value="/CSS/bootstrap.min.css" />" />
   	
   		
   		 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Affichage Ordinateur</title>
	</head>
	<body>
		<!--  navbar  --> 
		  <div class="navbar navbar-default navbar-fixed-top">
		      <div class="container">
			        <div class="navbar-header">
			          <a href="#" class="navbar-brand">Gestion des ordinateurs</a>
			          <button class="navbar-toggle" type="button" data-toggle="collapse" data-target="#navbar-main">
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
			            <span class="icon-bar"></span>
			          </button>
			        </div>	    
			         <div class="navbar-collapse collapse" id="navbar-main">
			          <ul class="nav navbar-nav">
			          <li>
			             <a href="#popupReadme">A propos</a>
			          </li>
			          </ul>
			        </div>
		        </div>
		   </div>
	   <!--  End of the navbar   --> 
	   
	   <!-- SearchBar   --> 
		
		<form id="searchBar" action="ComputerServlet" method="post" class="navbar-form navbar-left" role="search">
	        <div class="form-group">
	          <input type="text" name="terme" class="form-control" placeholder="Search">
	        </div>
	        <button type="submit" action="normalSearch" class="btn btn-default">Submit</button>
	     </form>
	     
	    <!--  End of the SearchBar   --> 
	    
	    <!--  Button add company and computer   --> 
	     
		    <div style="clear:right;"><a href="#popupAddCompany" class="popup-link Add"> Ajouter un fabricant <i class="fa fa-plus"></i></a></div>
		
			<div><a href="#popupAdd" class="popup-link Add"> Ajouter un ordinateur <i class="fa fa-plus"></i></a></div>
		
		 <!--  End   --> 
		 
		  <!--  Début du tableau des ordinateurs   --> 
			
			<table id="computerTableContainer" class="table table-striped table-hover ">
				<tr class="danger">
					<th>#</th>
					<th>computer name</th>
					<th>introduced</th>
					<th>discontinued</th>
					<th>company name</th>
					<th>Action</th>
				</tr>
				<c:forEach items="${requestScope.computers}" var="computer">
				<tr>
					<td>${computer.id}</td>
					<td>${computer.name}</td>
					<td>${computer.introduced}</td>
					<td>${computer.discontinued}</td>
					<td>${computer.company}</td>
					 <!--  recupération id ordinateur pour affichage popup   --> 
					<td id="myid">
						<a  href="#popupSup" class="popup-link"><i id="${computer.id}" onclick='document.getElementById("resultSup").innerHTML =this.id;  document.getElementById("myFieldSup").value = this.id;' class="fa fa-trash-o"></i></a>
						<a  href="#popupEdit" class="popup-link"><i id="${computer.id}" onclick='document.getElementById("resultUpd").innerHTML =this.id;  document.getElementById("myFieldUpd").value = this.id;' class="fa fa-pencil"></i></a>
					</td> 
				</tr>
				</c:forEach>
			</table>
			
			 <!--  End   --> 
			
		
	
		<!-- Tableau Pagination -->
		<table style="border:none;"  border="1" cellpadding="5" cellspacing="5">
			<tr>
			
				<%--Pour afficher le lien previous sauf à lapremière page --%>
				<c:if test="${currentPage != 1}">
					<td><a class="btn btn-warning" href="computer.do?page=${currentPage - 1}">Previous</a></td>
				</c:if>
				
				<c:forEach begin="1" end="${noOfPages}" var="i">
					<c:choose>
						<c:when test="${currentPage eq i}">
							<td >${i}</td>
						</c:when>
						<c:otherwise>
							<td class="btn btn-link"><a class="btn btn-default" href="computer.do?page=${i}">${i}</a></td>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				
				<!--Pour afficher le lien next -->
				<c:if test="${currentPage lt noOfPages}">
					<td><a class="btn btn-warning" href="computer.do?page=${currentPage + 1}">Next</a></td>
				</c:if>
			</tr>
		</table>
		
		<!-- fin de la pagination -->
		
			
		<!-- ***** Début du code des différents Popup *****  --> 
		
		
		
		<!-- popup  pour supprimer un element de la liste -->
		
				<div class="popup-wrapper" id="popupSup">
					<div class="popup-container"><!-- Popup Contents, just modify with your own -->
						<form action="ComputerServlet" method="post" class="popup-form">
						 <h2>Voulez vous vraiment supprimer l'ordinateur <label id='resultSup'></p> ? </h2>
						<div class="input-group">
						    <input type="hidden" id="myFieldSup" value="" name="id"/>		
							<input type="submit" value="Submit" name="supprimer" action="supprimer">
						</div>
						</form>
						<!-- Popup Content is untill here--><a class="popup-close" href="#closed">X</a>
					</div>
				</div>
				
		<!-- fin popup suppression-->		
		
		<!-- popup pour la modif' d'un element de la liste -->
		
			<div class="popup-wrapper" id="popupEdit">
					<div class="popup-container"><!-- Popup Contents, just modify with your own -->
						<form action="ComputerServlet" method="post" class="popup-form">
							<h2>Editer l'ordinateur <label id='resultUpd'></p></h2>
							<div class="input-group">
								<p><input type="text" name="name" placeholder="name"></p>
								<p><input type="date" name="introduced" placeholder="introduced date"></p>
								<p><input type="date" name="discontinued" placeholder="discontinued date"></p>
								<input type="hidden" id="myFieldUpd" value="" name="id"/>	
								<p><b>nom de l'entreprise<select name="company_id" id="input">
									<c:forEach items="${requestScope.companys}" var="company">					
									 <option id="input" value="${company.id}">${company.company_name}</option>
							 		</c:forEach> 
								 </select></b></p>
								 
								<p><input type="submit" value="Submit" name="modifier" action="modifier"></p>
							</div>
						</form>
						<!-- Popup Content is untill here--><a class="popup-close" href="#closed">X</a>
					</div>
				</div>
				
		<!-- fin popup modification -->
				
		<!-- popup pour l'ajout d'un element de la liste -->
		
		  	 <div class="popup-wrapper" id="popupAdd">
					<div class="popup-container"><!-- Popup Contents, just modify with your own -->
						<form action="ComputerServlet" method="post" class="popup-form">
							<h2>Ajouter un ordinateur</h2>
							<div class="input-group">
								<p><input type="text" name="name" placeholder="name"></p>
								<p><input type="date" name="introduced" placeholder="introduced date"></p>
								<p><input type="date" name="discontinued" placeholder="discontinued date"></p>
								<p><b>nom de l'entreprise<select name="company_id" id="input">
							
									<c:forEach items="${requestScope.companys}" var="company">
							
									 <option id="input" value="${company.id}">${company.company_name}</option>
							 
							 		</c:forEach> 
								 </select></b></p>
								<p><input type="submit" value="Submit" name="ajouter" action="ajouter"></p>
							</div>
						</form>
					<!-- Popup Content is untill here--><a class="popup-close" href="#closed">X</a>
					</div>
				</div>
				
		<!-- fin popup ajout element -->
				
		<!-- popup pour l'ajout d'une company dans la liste -->
		
		  	 <div class="popup-wrapper" id="popupAddCompany">
					<div class="popup-container"><!-- Popup Contents, just modify with your own -->
						<form action="ComputerServlet" method="post" class="popup-form">
							<h2>Ajouter un fabricant</h2>
							<div class="input-group">
								<p><input type="text" name="name" placeholder="name"></p>
								<p><input type="submit" value="Submit" name="ajouterCompany" action="ajouterCompany"></p>
							</div>
						</form>
					<!-- Popup Content is untill here--><a class="popup-close" href="#closed">X</a>
					</div>
				</div>
				
		<!-- fin popup ajout d'élément -->
		
		<!-- Popup Readme A propos !!! -->
				
				 <div class="popup-wrapper" id="popupReadme">
					<div class="popup-container"><!-- Popup Contents, just modify with your own -->
						<form action="ComputerServlet" method="post" class="popup-form">
							<p>Services implementes dans le devoir</p>
							<ul>
								<li>Connection a la base de donnees methodes JDBC (Operationnel)
										<div class="progress progress-striped active">
		 								 <div class="progress-bar" style="width: 99%"></div>
										</div>
								</li>
								<li>Connection a la base de donnees methodes JPA HIbernate(non Operationnel)
									<div class="progress progress-striped active">
		 							<div class="progress-bar" style="width: 45%"></div>
									</div>
								</li>
								<li>Listage, Ajout, Suppresion et modification ordinateur (Operationnel)
									<div class="progress progress-striped active">
		 							<div class="progress-bar" style="width: 99%"></div>
									</div>
								</li>
								<li>Listage et Ajout Fabricant (Operationnel)
									<div class="progress progress-striped active">
		 							<div class="progress-bar" style="width: 99%"></div>
									</div>
								</li>
								<li>Pagination (Operationnel)
									<div class="progress progress-striped active">
		 							<div class="progress-bar" style="width: 99%"></div>
									</div>
								</li>
								<li>Barre de Recherche (Operationnel)
									<div class="progress progress-striped active">
		 							<div class="progress-bar" style="width: 99%"></div>
									</div>
								</li>
								<li>Design simple et epure 
									<div class="progress progress-striped active">
		 							<div class="progress-bar" style="width: 97%"></div>
									</div>
								</li>
								
								<li>Code bien organise et commente 
									<div class="progress progress-striped active">
		 							<div class="progress-bar" style="width: 80%"></div>
									</div>
								</li>
							</ul>
						</form>
					<!-- Popup Content is untill here--><a class="popup-close" href="#closed">X</a>
					</div>
				</div>
	 	<!--  End of popup Readme   --> 
	 
	  <!-- ***** Fin des popup *****   --> 	
	</body>
</html>

 <!-- JSP Page display computeur
  auteur Bzo  --> 
