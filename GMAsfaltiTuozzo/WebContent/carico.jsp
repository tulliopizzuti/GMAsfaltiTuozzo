<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="it.asfalti.javabean.MagazzinoBean,java.util.ArrayList,
it.asfalti.javabean.ProdottoBean" %>
<%
	MagazzinoBean user=(MagazzinoBean)session.getAttribute("user");
	if(user==null || !user.getTipo().equals("mag")){
		response.sendRedirect("pagecomposer");
		return;	
	}
	ArrayList<ProdottoBean> prods=(ArrayList<ProdottoBean>)request.getAttribute("prods"); 

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="style\carico.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="script\ajax.js"></script>
<title>Richiedi Carico, Magazzino <%=user.getIdM() %></title>
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
			<%if(prods!=null && prods.size()>0){ %>
				<table>
					<caption>Disponibilità prodotti del magazzino</caption>
					<tr> 
						<th>IdProdotto </th>
						<th>Descrizione </th>
						<th>Unità di misura </th>
					</tr>
					<%for(ProdottoBean d:prods){ %>
						<tr>
							<td><%=d.getId()%> </td>
							<td><%=d.getDescrizione()%>  </td>
							<td><%=d.getMisura() %>  </td>
							<td>
							<%if(d.getMisura().equals("Pz") || d.getMisura().equals("Rt")){ %>
									<input class="inputNumber" name=<%=d.getId()%> min=0 type="number"/>
							<%} else{ %>
									<input class="inputNumber" step="0.01" name=<%=d.getId()%> min=0  type="number" />
							<%} %></td>
						</tr>
					<%} %>
				</table>
				<input type="submit" onClick="send()" value="Invia ordine">
			<%} else { %>
				<div id="nulldisp">
					Non ci sono prodotti ordinabili!
				</div>
			<%} %>
		</div>
	</div>
</body>
<script type="text/javascript">
	function send(){
		var elements=document.getElementsByClassName("inputNumber");
		var json=[];
		for(i=0;i<elements.length;i++){
			if(elements[i].value>0){
				json.push(JSON.parse('{"id":"'+elements[i].name+'","q":"'+elements[i].value+'"}'));
			}
		}
		var jsonS=JSON.stringify(json);
		jsonS=encodeURIComponent(jsonS);;
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
		xhttp.open("GET","inviaCarico?obj="+jsonS,true);
		xhttp.send();
	}





</script>




</html>