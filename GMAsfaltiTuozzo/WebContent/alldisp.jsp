<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="it.asfalti.javabean.MagazzinoBean,
java.util.ArrayList,it.asfalti.javabean.DisponibilitaBean" %>
<%
	MagazzinoBean user=(MagazzinoBean)session.getAttribute("user");
	if(user==null || !user.getTipo().equals("admin")){
		response.sendRedirect("pagecomposer");
		return;	
	}
	ArrayList<MagazzinoBean> mags=(ArrayList<MagazzinoBean>)request.getAttribute("mags"); 

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="style\alldisp.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="script\ajax.js"></script>
<title>Disponibilità tutti i Magazzini</title>
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
			<%if(mags!=null && mags.size()>0){ %>
				<p id="idMagazzinoP">Id magazzino</p>
				<select id="selectMag" onChange="getDisp(this)">
					<option value="none"> </option>
					<%for(MagazzinoBean m:mags){ %>
					<option value="<%=m.getIdM() %>"><%=m.getIdM() %> </option>
					<%} %>
				</select>
				<div id="magDisp" style="visibility:hidden;">
					<table id="magTable">
						<caption>Disponibilità prodotti del magazzino</caption>
							<tr id="tHeader"> 
								<th>IdProdotto </th>
								<th>Descrizione </th>
								<th>Quantità </th>
								<th>Unità di misura </th>
							</tr>
							
					</table>
				</div>				
			<%} else { %>
				<div id="nulldisp">
					Nessun Magazzino, creane uno prima!
				</div>
			<%} %>
		</div>
	</div>
</body>

<script type="text/javascript">
	var table=document.getElementById("magTable").cloneNode(true);
	var div=document.getElementById("magDisp");
	function getDisp(optionElement){
		var id=optionElement.options[optionElement.selectedIndex].value;
		document.getElementById("magTable").remove();
		div.appendChild(table);
		table=table.cloneNode(true);
		div.style.visibility="hidden";
		var xhttp=getXmlHttpRequest();
		if(id!="none"){
			xhttp.onreadystatechange=function(){
				if(xhttp.readyState==4 && xhttp.status==200){
					var text=xhttp.responseText;
					var disp=JSON.parse(text);
					div.style.visibility="visible";
					var t=document.getElementById("magTable");
					for(i=0;i<disp.length;i++){
						var row=document.createElement("tr");
						var id=document.createElement("td");
						var desc=document.createElement("td");
						var mis=document.createElement("td");
						var q=document.createElement("td");
						id.appendChild(document.createTextNode(disp[i].id));
						desc.appendChild(document.createTextNode(disp[i].desc));
						mis.appendChild(document.createTextNode(disp[i].mis));
						q.appendChild(document.createTextNode(disp[i].q));
						row.appendChild(id);
						row.appendChild(desc);
						row.appendChild(mis);
						row.appendChild(q);
						t.appendChild(row);
					}
				}		
			};
		}
		else{
			div.style.visibility="hidden";
		}
		xhttp.open("GET","getDispOf?id="+id,true);
		xhttp.send();
	}
</script>
</html>