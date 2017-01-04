<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="it.asfalti.javabean.MagazzinoBean" %>
<%
	MagazzinoBean user=(MagazzinoBean)session.getAttribute("user");
	/**if(user==null || !user.getTipo().equals("mag")){
		session.removeAttribute("user");
		session.invalidate();
		response.sendRedirect("pagecomposer?responsepage=login");
		return;	
	}
*/
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Magazzino</title>
</head>
<body>
	<div class="main">
		<nav id="navbar">
			<h1>Operazioni</h1>
			<ul id="navbarelements">
				<li><a href="./pagecomposer?responsepage=magpage">DatiMagazzino</a>
				<li><a href="./pagecomposer?responsepage=disp">Disponibilità</a>
				<li><a href="./pagecomposer?responsepage=opconcl">Operazioni concluse</a>
				<li><a href="./pagecomposer?responsepage=opsosp">Operazioni sospese</a>
				<li><a href="./pagecomposer?responsepage=carico">Carico merci</a>
				<li><a href="./pagecomposer?responsepage=scarico">Scarico merci</a>
				<li><a href="./pagecomposer?responsepage=ordscarico">Oridini di scarico</a>
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