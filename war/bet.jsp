<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="javax.jdo.PersistenceManager" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="betbikegame.actions.*" %>
<%@ page import="betbikegame.beans.Pari" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>The Bet Bike Game</title>
</head>
<body>

<%
UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();

    if (user != null) {
%>
<p>(You can
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<%
    } else {
%>

<p>(You can
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a> .)
</p>
<%
    }
%>

<h1>Parier sur une ville</h1>
<%

if (session.getAttribute("deja_parie")== "false"){
%>
<p> Vous n'avez pas encore parié aujourd'hui, c'est le moment ! </p>
<form action="/bet" method="get">
    <div><input type="text" name="villePari"></input></div>
    <div><input type="submit" value="Parier!" /></div>
    </form>
<%
} else if (session.getAttribute("deja_parie")== "true") {
%>
<p> Vous avez parié sur <%= request.getAttribute("villePari") %> </p>

 <% } %>
 
</body>
</html>