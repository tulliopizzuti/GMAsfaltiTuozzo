<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="it.asfalti.javabean.MagazzinoBean,java.util.ArrayList,it.asfalti.javabean.DisponibilitaBean" %>
<%
	MagazzinoBean user=(MagazzinoBean)session.getAttribute("user");
	if(user==null || !user.getTipo().equals("mag")){
		response.sendRedirect("pagecomposer");
		return;	
	}
	ArrayList<DisponibilitaBean> disp=(ArrayList<DisponibilitaBean>)request.getAttribute("disp"); 

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="style\scarico.css" rel="stylesheet" type="text/css">
<title>Scarica merce Magazzino <%=user.getIdM() %></title>
</head>
<body>
	<div class="main">
		<nav id="navbar">
			<h1>Operazioni</h1>
			<ul id="navbarelements">
				<li><a href="./pagecomposer?responsepage=magpage">DatiMagazzino</a></li>
				<li><a href="./pagecomposer?responsepage=disp">Disponibilitą</a></li>
				<li><a href="./pagecomposer?responsepage=opconcl">Operazioni concluse</a></li>
				<li><a href="./pagecomposer?responsepage=opsosp">Operazioni sospese</a></li>
				<li><a href="./pagecomposer?responsepage=carico">Carico merci</a></li>
				<li><a href="./pagecomposer?responsepage=scarico">Scarico merci</a></li>
				<li><a href="./pagecomposer?responsepage=ordscarico">Oridini di scarico</a></li>
				<li><a href="logout">LogOut</a></li>
			</ul>		
		</nav>
		<div id="dynamiccontent">
			<%if(disp!=null && disp.size()>0){ %>
			
				<table>
					<caption>Disponibilitą prodotti del magazzino</caption>
					<tr> 
						<th>IdProdotto </th>
						<th>Descrizione </th>
						<th>Quantitą </th>
						<th>Unitą di misura </th>
					</tr>
					<%for(DisponibilitaBean d:disp){ %>
					
						<tr>
							<td><%=d.getProd().getId() %> </td>
							<td><%=d.getProd().getDescrizione()%>  </td>
							<td><%=d.getQ() %>  </td>
							<td><%=d.getProd().getMisura()%>  </td>
							<td> 
								<input class="inputNumber" name=<%=d.getProd().getId()%> min=0 max=<%=d.getQ()%> type="number"       />
							</td>
						</tr>
						
					
					<%} %>
				</table>
				<form method="post" onSubmit="">
					<input type="submit" value="Completa">
				</form>
			<%} else { %>
				<div id="nulldisp">
					Non hai disponibilitą di prodotti! Devi ordinarli!
				</div>
			<%} %>
		</div>
	</div>
	<script type="text/javascript">
		var elements=document.getElementsByClassName("inputNumber");
		var toSend[];
		for(i=0;i<elements.lenght;i++){
			if(elements[i].value>0){
				
			}
		}
	
	
	
	
	
	
	
	</script>
</body>
</html>