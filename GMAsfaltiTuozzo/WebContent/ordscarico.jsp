<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="it.asfalti.javabean.MagazzinoBean,java.util.ArrayList,
it.asfalti.javabean.OperazioneSospesaBean,it.asfalti.javabean.ComposizioneBean
,it.asfalti.javabean.ProdottoBean"
 %>
<%
	MagazzinoBean user=(MagazzinoBean)session.getAttribute("user");
	if(user==null || !user.getTipo().equals("mag")){
		response.sendRedirect("pagecomposer");
		return;	
	}
	ArrayList<OperazioneSospesaBean> op=(ArrayList<OperazioneSospesaBean>)request.getAttribute("op"); 

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="style\ordscarico.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="script\ajax.js"></script>
<title>Ordini di scarico Magazzino <%=user.getIdM() %></title>
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
				<h2>Operazioni sospese magazzino</h2>
				<%for(OperazioneSospesaBean operation:op){ %>
					<table>
						<tr> 
							<th>Codice Operazione </th>
							<th>Data/Ora </th>
							<th>Tipo </th>
							<th>Mittente/Destinazione </th>
							<th>Stato</th>
						</tr>
						<tr> 
							<td><%=operation.getIdOp() %> </td>
							<td><%=operation.getData() %> </td>
							<td><%=operation.getTipo() %> </td>
							<td><%=operation.getDa_a() %> </td>
							<td><%=operation.getStato() %> </td>
							<%if(operation.getStato().equals("Elaborazione")){ %>
								<td>
									<button id=<%= operation.getIdOp()%>  
									onClick="change(this)">Spedita</button>
								</td>
							<%} %>
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
					Non hai Ordini di scarico
				</div>
			<%} %>
		</div>
	</div>
</body>
<script type="text/javascript">

	function change(element){
		var xhttp=getXmlHttpRequest();
		xhttp.onreadystatechange=function(){
			if(xhttp.readyState==4 && xhttp.status==200){
				var text=xhttp.responseText;
				var s;
				if(text=="true"){
					var s = "Operazione completata";
				}
				else{
					var s="Errore nel completare l'operazione";
				}
				window.alert(s);
				window.location.reload();
			}			
		};
		xhttp.open("GET","registraCarico?id="+element.id,true);
		xhttp.send();
	}








</script>
</html>