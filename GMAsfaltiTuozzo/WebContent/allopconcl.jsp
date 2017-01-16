<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="it.asfalti.javabean.MagazzinoBean,
java.util.ArrayList,it.asfalti.javabean.OperazioneCompletataBean" %>
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
<link href="style\allopconcl.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="script\ajax.js"></script>
<title>Operazioni di tutti i Magazzini</title>
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
				<div id="magOp" style="visibility:hidden;">
				
					<table class="infOpTable">
							<tr id="tHeader"> 
								<th>Codice Operazione </th>
								<th>Data/Ora </th>
								<th>Tipo </th>
								<th>Mittente/Destinazione </th>
								<th>
							</tr>							
					</table>
					
					<table class="compOpTable">
							<tr> 
								<th>Codice Prodotto </th>
								<th>Descrizione </th>
								<th>Quantità </th>
								<th>Unità di misura</th>
							</tr>
							
					</table>
					
				</div>				
			<%} else { %>
				<div id="nulldisp">
					Nessun magazzino!
				</div>
			<%} %>
		</div>
	</div>
</body>

<script type="text/javascript">
	var infOpTable=document.getElementsByClassName("infOpTable")[0].cloneNode(true);
	var compOpTable=document.getElementsByClassName("compOpTable")[0].cloneNode(true);
	var div=document.getElementById("magOp");
	function getDisp(optionElement){
		var id=optionElement.options[optionElement.selectedIndex].value;
		var inf=document.getElementsByClassName("infOpTable");
		console.log(inf.length);
		for(i=0;i<inf.length;i++){ inf[i].remove();}
		var comp=document.getElementsByClassName("compOpTable");
		for(i=0;i<comp.length;i++){ comp[i].remove();}
		
		div.style.visibility="hidden";
		var xhttp=getXmlHttpRequest();
		if(id!="none"){
			xhttp.onreadystatechange=function(){
				if(xhttp.readyState==4 && xhttp.status==200){
					div.style.visibility="visible";
					var text=xhttp.responseText;
					var ops=JSON.parse(text);
					for(i=0;i<ops.length;i++){
						var iT=infOpTable.cloneNode(true);
						var cT=compOpTable.cloneNode(true);
						div.appendChild(iT);
						div.appendChild(cT);
						var row=document.createElement("tr");
						var id=document.createElement("td");
						var idM=document.createElement("td");
						var data=document.createElement("td");
						var da_a=document.createElement("td");
						var tipo=document.createElement("td");
						id.appendChild(document.createTextNode(ops[i].idO));
						row.appendChild(id);
						idM.appendChild(document.createTextNode(ops[i].idM));
						row.appendChild(idM);
						data.appendChild(document.createTextNode(ops[i].data));
						row.appendChild(data);
						da_a.appendChild(document.createTextNode(ops[i].da_a));
						row.appendChild(da_a);
						tipo.appendChild(document.createTextNode(ops[i].tipo));
						row.appendChild(tipo);
						iT.appendChild(row);
						for(j=0;j<ops[i].prods.length;j++){
							var comP=document.createElement("tr");
							var idP=document.createElement("td");
							var pDesc=document.createElement("td");
							var pMis=document.createElement("td");
							var pQ=document.createElement("td");
							idP.appendChild(document.createTextNode(ops[i].prods[j].idP));
							comP.appendChild(idP);
							pDesc.appendChild(document.createTextNode(ops[i].prods[j].pDesc));
							comP.appendChild(pDesc);
							pMis.appendChild(document.createTextNode(ops[i].prods[j].pMis));
							comP.appendChild(pMis);
							pQ.appendChild(document.createTextNode(ops[i].prods[j].pQ));
							comP.appendChild(pQ);
							cT.appendChild(comP);	
						}
					}
				}		
			};
		}
		else{
			div.style.visibility="hidden";
		}
		xhttp.open("GET","getOpOf?id="+id,true);
		xhttp.send();
	}
</script>
</html>