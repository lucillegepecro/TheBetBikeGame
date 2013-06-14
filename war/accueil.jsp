<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,800"
	rel="stylesheet" type="text/css" />
<link href="http://fonts.googleapis.com/css?family=Oleo+Script:400"
	rel="stylesheet" type="text/css" />
<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyBPRmcpdoocTm2UQfrI64Jc-qKk_yOadKs&sensor=true"
	type="text/javascript"></script>
<script src="javascript/mymap.js"></script>
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
						<span><img src="images/baniereEnd.png" />
						</span>
					</div>

					<!-- Nav --> <nav id="nav">
					<ul>
						<li class="current_page_item"><a href="index.html">Bienvenue</a>
						</li>
						<li><a href="left-sidebar.html">Historique</a>
						</li>
						<li><a href="right-sidebar.html">Livre d'or</a>
						</li>
					</ul>
					</nav> </header>

				</div>
			</div>
		</div>
	</div>

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
										<!-- Sign in message -->
	<%
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user != null) {
	%>
	<div>
	<p>
		Bienvenue <%=user.getNickname()%> !
		
	</p>
	
		(You can <a
			href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign
			out</a>.)
			
	</div>
	<%
		} else {
	%>
	<div>
<p>Bienvenue !</p>
	
		(You can <a
			href="<%=userService.createLoginURL(request.getRequestURI())%>">Sign
			in</a> .)
			
	</div>
	<%
		}
	%>
								</div>
								<div class="5u">
									<ul>
																	

										<li>
										<form action="/accueil" method="get">
										<a href="/accueil"
											class="button button-big button-icon button-icon-rarrow">Jouez!</a>
										</form>
										</li>
									</ul>
								</div>
							</div>
						</div>

					</div>

				</div>
			</div>
		</div>
	</div>

	<!-- Features Wrapper -->
	<div id="features-wrapper">
		<div class="container">
			<div class="row">
				<div class="4u">

					<!-- Box -->
					<section class="box box-feature"> <a href="#"
						class="image image-full"><img src="images/nantesBicloo.jpg"
						alt="" />
					</a>
					<div class="inner">
						<header>
						<h2>La ville de Nantes</h2>
						<span class="byline">La grande gagnante des bicloos</span> </header>
						<p>Les meilleurs des vélos</p>
					</div>
					</section>

				</div>
				<div class="4u">

					<!-- Box -->
					<section class="box box-feature"> <a href="#"
						class="image image-full"><img src="images/velibParis.jpg"
						alt="" />
					</a>
					<div class="inner">
						<header>
						<h2>La ville de Paris</h2>
						<span class="byline">Ils sont moins beaux que les vélos de
							Nantes</span> </header>
						<p>J'aime pas le métro !</p>
					</div>
					</section>

				</div>
				<div class="4u">

					<!-- Box -->
					<section class="box box-feature last"> <a href="#"
						class="image image-full"><img src="images/veloLyon.jpg" alt="" />
					</a>
					<div class="inner">
						<header>
						<h2>La ville de Lyon</h2>
						<span class="byline">Avec ses vélos tous rouges !</span> </header>
						<p>Plein de vélos</p>
					</div>
					</section>

				</div>
			</div>
		</div>
	</div>







</body>
</html>