<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="betbikegame.beans.Ville" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.PreparedQuery" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Query.SortDirection" %>


<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />

	<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,800"
	rel="stylesheet" type="text/css" />
	<link href="http://fonts.googleapis.com/css?family=Oleo+Script:400"
	rel="stylesheet" type="text/css" />

	<script src="js/jquery.min.js"></script>
	<script src="js/config.js"></script>
	<script src="js/skel.min.js"></script>
	<script src="js/skel-panels.min.js"></script>
	<noscript>
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/style-desktop.css" />
	</noscript>

	<title>The Bet Bike Game</title>
</head>
<body class="homepage">



	<!-- Header Wrapper -->
	<div id="header-wrapper">
		<div class="container">
			<div class="row">
				<div class="12u">

					<!-- Header -->
					<header id="header"> <!-- Logo -->
						<div id="logo">
							<span><img src="images/baniereEnd.png" /> </span>
						</div>

						<!-- Nav --> <nav id="nav">
						<ul>
							<li><a href="accueil.jsp">Accueil</a>
							</li>
							<li class="current_page_item"><a href="classement.jsp">Classement</a></li>
							<li><a href="/moi">Mes Paris</a></li>
							<li><a href="guestbook.jsp">Guestbook</a></li>
						</ul>
					</nav> </header>

				</div>
			</div>
		</div>
	</div>

	<div id="banner-wrapper">
		<div class="container">
			<div class="row">
				<div class="12u">

					<!-- Banner -->
					<div id="banner" class="box">

						<div>
							<div class="row">
								<div class="row">
							    <p>Voici le classement en temps réel des villes !</p>
							    <b>(Actualisé toutes les heures)</b>
							  
										
									
									
									<% 

									UserService userService = UserServiceFactory.getUserService();
									User user = userService.getCurrentUser();

									List<Ville> listvilles = new ArrayList<Ville>();
									Ville ville = new Ville("", 0.0);

// Get the Datastore Service
									DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

 // Récupère le classement final de la journée
									Query villetri = new Query("Ville");		 
									villetri.addFilter("km", Query.FilterOperator.GREATER_THAN_OR_EQUAL, (Double) 0.0);
									villetri.addSort("km", SortDirection.DESCENDING);

									PreparedQuery PQvilletri = datastore.prepare(villetri);	
									%>
									<div class="CSSTableGenerator">
										<table class="mesparis">
											<tr>
												<th>Classement</th>
												<th>Ville</th>
												<th>Km</th>

											</tr>
											<%
											int i =0;
											for (Entity result : PQvilletri.asIterable()) {
											i++;
											ville.setKilometers((Double) result.getProperty("km"));			
											ville.setName((String) result.getProperty("contract"));					
											%>
											<tr>
											    <td><%=i %></td>
												<td><%=ville.getName()%></td>
												<td><%=ville.getKilometers()%></td>
											</tr>
											<%
										}
										%>
									</table>
								</div>

							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>