<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="it.asfalti.javabean.MagazzinoBean,it.asfalti.javabean.ProdottoBean,java.util.ArrayList" %>
<%
	MagazzinoBean user=(MagazzinoBean)session.getAttribute("user");
	if(user==null || !user.getTipo().equals("admin")){
		session.removeAttribute("user");
		session.invalidate();
		response.sendRedirect("pagecomposer?responsepage=login");
		return;	
	}
	ArrayList<ProdottoBean> prods=(ArrayList<ProdottoBean>)request.getAttribute("prods");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="style\delprod.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="script\ajax.js"></script>
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
			<div id="prods">
				<%if(prods!=null && prods.size()>0){ %>
					
					<table>
						<caption>Prodotti</caption>
						<tr> 
							<th>IdProdotto </th>
							<th>Descrizione </th>
							<th>Unità di misura </th>
							<th>Da eliminare<th>
						</tr>
						<%for(ProdottoBean p:prods){ %>
							<tr>
								<td><%=p.getId() %> </td>
								<td><%=p.getDescrizione()%>  </td>
								<td><%=p.getMisura()%>  </td>
								<td>
									<input class="toDel" type="checkbox" name="<%=p.getId() %>" value="<%=p.getId()%>"/>
								</td>
							</tr>
						<%} %>
					</table>
					<input type="submit" value="Elimina selezionati" onClick="del()">
				<%} else { %>
					<div id="nulldisp">
						Non hai prodotti! Devi crearli!
					</div>
				<%} %>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function del(){
		var checkbox=document.getElementsByClassName("toDel");
		var json=[];
		for(i=0;i<checkbox.length;i++){
			if(checkbox[i].checked){
				json.push({"id":checkbox[i].value});
			}
		}		
		jsonS=JSON.stringify(json);
		jsonS=encodeURIComponent(jsonS);
		var xhttp=getXmlHttpRequest();
		xhttp.onreadystatechange=function(){
			if(xhttp.readyState==4 && xhttp.status==200){
				var text=xhttp.responseText;
				var s;
				if(text=="true"){
					var s = "Operazione completata";
				}
				else{
					var s="Errore nel completare l'operazione per alcuni prodotti";
				}
				window.alert(s);
				window.location.reload();					
			}
		};	
		xhttp.open("GET","eliminaProdotti?obj="+jsonS,true);
		xhttp.send();
		
		
	}
















</script>
</html>