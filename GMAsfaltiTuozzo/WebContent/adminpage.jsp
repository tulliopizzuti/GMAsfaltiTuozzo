<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="it.asfalti.javabean.MagazzinoBean" %>
<%
	MagazzinoBean user=(MagazzinoBean)session.getAttribute("user");
	if(user==null || !user.getTipo().equals("admin")){
		session.removeAttribute("user");
		session.invalidate();
		response.sendRedirect("pagecomposer?responsepage=login");
		return;	
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="style\ammpage.css" rel="stylesheet" type="text/css">
<title>Amministratore</title>
</head>
<body>
	<div class="main">
		<nav id="navbar">
			<h1>Operazioni</h1>
			<ul id="navbarelements">
				<li><a href="./pagecomposer?responsepage=adminpage">Dati Amministratore</a></li>
				<li><a href="./pagecomposer?responsepage=alldisp">Disponibilità</a></li>
				<li><a href="./pagecomposer?responsepage=allopconcl">Operazioni concluse</a></li>

				<li><a href="./pagecomposer?responsepage=ordcarico">Ordini di carico</a></li>
				<li><a href="./pagecomposer?responsepage=ordforn">Ordini fornitori</a></li>
					
				<li><a href="./pagecomposer?responsepage=instprod">Inserisci prodotto</a></li>
				<li><a href="./pagecomposer?responsepage=modprod">Modifica prodotto</a></li>
				<li><a href="./pagecomposer?responsepage=delprod">Elimina prodotto</a></li>
				
				<li><a href="./pagecomposer?responsepage=instmag">Inserisci magazzino</a></li>
				<li><a href="./pagecomposer?responsepage=modmag">Modifica magazzino</a></li>
				<li><a href="./pagecomposer?responsepage=delmag">Elimina magazzino</a></li>
				<li><a href="logout">LogOut</a></li>
			</ul>		
		</nav>
		<div id="dynamiccontent">
			<div id="magname">
				<%=user.getIdM()%>
			</div>
			<div id="magdesc">
				<%=user.getDescrizione() %>
			</div>
			<div id="magcitta">
				<%=user.getCitta() %>
			</div>
			<div id="magcap">
				<%=user.getCap()%>
			</div>
			<div id="magvia">
				<%=user.getVia()%>
			</div>
			<div id="magncivico">
				<%=user.getnCivico() %>
			</div>
		</div>
	</div>
</body>
</html>