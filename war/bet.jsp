<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page
	import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.Key"%>
<%@ page import="com.google.appengine.api.datastore.PreparedQuery"%>
<%@ page import="com.google.appengine.api.datastore.Query"%>

<%@ page import="java.util.Date"%>
<%@ page import="javax.jdo.PersistenceManager"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="betbikegame.actions.*"%>
<%@ page import="betbikegame.beans.Pari"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />

<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/datalist.js"></script>
<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,800"
	rel="stylesheet" type="text/css" />
<link href="http://fonts.googleapis.com/css?family=Oleo+Script:400"
	rel="stylesheet" type="text/css" />
<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyBPRmcpdoocTm2UQfrI64Jc-qKk_yOadKs&sensor=true"
	type="text/javascript"></script>
<script src="javascript/mymap.js"></script>
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
						<li class="current_page_item"><a href="accueil.jsp">Accueil</a>
						</li>
						<li><a href="classement.jsp">Classement</a></li>
						<li><a href="/moi">Mes Paris</a></li>
						<li><a href="guestbook.jsp">Guestbook</a></li>
					</ul>
					</nav> </header>

				</div>
			</div>
		</div>
	</div>



	<%
		if (session.getAttribute("deja_parie") == "false") {
	%>
	<!-- Banner Wrapper if not paried yet-->
	<div id="banner-wrapper">
		<div class="container">
			<div class="row">
				<div class="12u">

					<!-- Banner -->
					<div id="banner" class="box">

						<div>
							<div class="row">
								<div class="7u">
									<p>Pariez sur une ville</p>
									<label class="messagePari"> Vous n'avez pas encore
										parié aujourd'hui, c'est le moment! </label> <label>Choisissez
										la ville sur laquelle vous voulez parier :</label>
									<form action="/bet" method="get">
										<select id="mesvilles" name="villePari">

										</select> <label> Choisissez votre mise : </label> <label>5<input
											type="radio" name="misePari" value="5"></input> </label> <label>10<input
											type="radio" name="misePari" , value="10"></input> </label> <label>15<input
											type="radio" name="misePari" , value="15"></input> </label> <input
											class="betsubmit" id="bouttonParier" type="submit"
											value="Parier!" />
									</form>
								</div>
								<div class="5u">

									<div id="map-canvas"></div>

								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>


	<%
		} else if (session.getAttribute("deja_parie") == "true") {
	%>

	<!-- Banner Wrapper 2 if already paried -->
	<!-- Banner Wrapper -->
	<div id="banner-wrapper">
		<div class="container">
			<div class="row">
				<div class="12u">

					<!-- Banner -->
					<div id="banner" class="box">

						<div>
							<div class="row">
								<div class="7u">
									<p>
										Dernier pari :
										<%
										if (session.getAttribute("villePari") == null) {
									%>
										Vous n'avez pas encore parié !<a href="/accueil"> Pariez
											Maintenant !</a>
										<%
											} else {
										%>
										Ville de
										<%=session.getAttribute("villePari")%>, Résultats prévus à
										19H00
										<%
											}
										%>
									</p>


								</div>
								<div class="5u">
									<h3>Mes paris précédents :</h3>

									<%
										UserService userService = UserServiceFactory.getUserService();
											User user = userService.getCurrentUser();

											if (true) {

												Pari pari = new Pari();
												pari.setUser(user);

												List<Pari> listparis = new ArrayList<Pari>();

												DatastoreService datastore = DatastoreServiceFactory
														.getDatastoreService();
												// Récupère le classement final de la journée
												Query q = new Query("Pari");

												PreparedQuery pq = datastore.prepare(q);
									%>

									<div class="CSSTableGenerator">
										<table class="mesparis">

											<tr>

												<th>Ville</th>
												<th>Résultats</th>
												<th>Date</th>
											</tr>

											<%
												for (Entity result : pq.asIterable()) {
													if ( ( (String) result.getProperty("user") ).equalsIgnoreCase( user.toString() ) ){
														pari.setDate((String) result.getProperty("date"));
														pari.setVille((String) result.getProperty("ville"));
														pari.setResultat((String) result.getProperty("resultat"));
														listparis.add(pari);
												}
											%>
											<tr>
												<td><%=pari.getVille()%></td>

												<%
													if (pari.getResultat() == "") {
												%>
												<td>en attente</td>
												<%
													} else {
												%>
												<td><%=pari.getResultat()%></td>
												<%
													}
												%>
												<td><%=result.getProperty("date")%></td>
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
	</div>
	<%
		}
	%>
	<%
		}
	%>

</body>
</html>