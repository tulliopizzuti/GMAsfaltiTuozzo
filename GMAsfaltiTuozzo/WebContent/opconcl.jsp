<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="it.asfalti.javabean.MagazzinoBean,java.util.ArrayList,
it.asfalti.javabean.OperazioneCompletataBean,it.asfalti.javabean.ComposizioneBean
,it.asfalti.javabean.ProdottoBean"
 %>
<%
	MagazzinoBean user=(MagazzinoBean)session.getAttribute("user");
	if(user==null || !user.getTipo().equals("mag")){
		response.sendRedirect("pagecomposer");
		return;	
	}
	ArrayList<OperazioneCompletataBean> op=(ArrayList<OperazioneCompletataBean>)request.getAttribute("op"); 

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="style\opconcl.css" rel="stylesheet" type="text/css">
<title>Operazioni concluse Magazzino <%=user.getIdM() %></title>
</head>
<body>
	<div class="main">
		<nav id="navbar">
			<h1>Operazioni</h1>
			<ul id="navbarelements">
				<li><a href="./pagecomposer?responsepage=magpage">DatiMagazzino</a></li>
				<li><a href="./pagecomposer?responsepage=disp">Disponibilità</a></li>
				<li><a href="./pagecomposer?responsepage=opconcl">Operazioni concluse</a></li>
				<li><a href="./pagecomposer?responsepage=opsosp">Operazioni sospese</a></li>
				<li><a href="./pagecomposer?responsepage=carico">Carico merci</a></li>
				<li><a href="./pagecomposer?responsepage=scarico">Scarico merci</a></li>
				<li><a href="./pagecomposer?responsepage=ordscarico">Oridini di scarico</a></li>
				<li><a href="logout">LogOut</a></li>
			</ul>		
		</nav>
		<div id="dynamiccontent">
			<%if(op!=null && op.size()>0){ %>
				<h2>Operazioni concluse magazzino</h2>
				<%for(OperazioneCompletataBean operation:op){ %>
					<table>
						<tr> 
							<th>Codice Operazione </th>
							<th>Data/Ora </th>
							<th>Tipo </th>
							<th>Mittente/Destinazione </th>
						</tr>
						<tr> 
							<td><%=operation.getIdOp() %> </td>
							<td><%=operation.getData() %> </td>
							<td><%=operation.getTipo() %> </td>
							<td><%=operation.getDa_a() %> </td>
						</tr>
					</table>
					<% ArrayList<ComposizioneBean> comp=operation.getListaProdotti();
					if( comp!=null ) 
						for(ComposizioneBean c:comp){
						%>
						<table>
							<tr> 
								<th>Codice Prodotto </th>
								<th>Descrizione </th>
								<th>Quantità </th>
								<th>Unità di misura</th>
							</tr>
							<tr> 
								<%ProdottoBean p=c.getProdotto(); %>
								<td><%=p.getId() %> </td>
								<td><%=p.getDescrizione() %> </td>
								<td><%=c.getQuantita() %> </td>
								<td><%=p.getMisura()%> </td>
							</tr>
						</table>
						<%} %>
					<%} %>
			<%} else { %>
				<div id="nulldisp">
					Non hai disponibilità di prodotti! Devi ordinarli!
				</div>
			<%} %>
		</div>
	</div>
</body>
</html>