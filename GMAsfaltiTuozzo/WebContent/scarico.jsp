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
 	<script type="text/javascript" src="script\ajax.js"></script>
<title>Scarica merce Magazzino <%=user.getIdM() %></title>
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
			<%if(disp!=null && disp.size()>0){ %>
			
				<table>
					<caption>Disponibilità prodotti del magazzino</caption>
					<tr> 
						<th>IdProdotto </th>
						<th>Descrizione </th>
						<th>Quantità </th>
						<th>Unità di misura </th>
						<th>Seleziona</th>
					</tr>
					<%for(DisponibilitaBean d:disp){ %>
					
						<tr>
							<td><%=d.getProd().getId() %> </td>
							<td><%=d.getProd().getDescrizione()%>  </td>
							<td><%=d.getQ() %>  </td>
							<td><%=d.getProd().getMisura()%>  </td>
							<td> 
							<%if(d.getProd().getMisura().equals("Pz") || d.getProd().getMisura().equals("Rt")){ %>
								
									<input class="inputNumber" name=<%=d.getProd().getId()%> min=0 max=<%=d.getQ()%> type="number"/>
								
							<%} else{ %>
							
									<input class="inputNumber" step="0.01" name=<%=d.getProd().getId()%> min=0 max=<%=d.getQ()%> type="number" />
								
							<%} %>
							<input type="hidden" value="<%=d.getQ() %>" class="quantity">
							</td>
						</tr>
						
					
					<%} %>
				</table>
					<input type="submit" value="Completa" onClick=send()>
				
			<%} else { %>
				<div id="nulldisp">
					Non hai disponibilità di prodotti! Devi ordinarli!
				</div>
			<%} %>
		</div>
	</div>
	
<script type="text/javascript">
	

	function send(){
		var elements=document.getElementsByClassName("inputNumber");
		var quantity=document.getElementsByClassName("quantity");
		var error=document.getElementsByClassName("errorInput")
		for(i=0;i<error.length;i++){
			error[i].parentNode.removeChild(error[i]);
		}
		var json=[];
		var flag=true;
		for(i=0;i<elements.length;i++){
			if(elements[i].value>0){
				if(parseFloat(quantity[i].value)>=elements[i].value){
					json.push(JSON.parse('{"id":"'+elements[i].name+'","q":"'+elements[i].value+'"}'));
				}else{
					flag=false;
					var p=document.createElement("p");
					var node = document.createTextNode("Valore non corretto");
					p.setAttribute("class","errorInput");
					p.appendChild(node);
					elements[i].parentNode.insertBefore(p,elements[i].nextSibling);
				}
			}
			
		}	
		if(flag){
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
			xhttp.open("GET","scaricaMerce?obj="+jsonS,true);
			xhttp.send();
		}
	}
	</script>
</html>