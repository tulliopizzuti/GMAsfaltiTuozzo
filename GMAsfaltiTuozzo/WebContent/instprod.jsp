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
<link href="style\instprod.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="script\ajax.js"></script>
<title>Amministratore</title>
</head>
<body>
	<div class="main">
		<nav id="navbar">
			<h1>Operazioni</h1>
			<ul id="navbarelements">
				<li><a href="./pagecomposer?responsepage=adminpage">Dati Amministratore</a></li>
				<li><a href="./pagecomposer?responsepage=alldisp">DisponibilitÓ</a></li>
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
			<div id="information">
				<p id="pName" class="pInput">Nome prodotto:</p>
				<input type="text" maxlength="10" id="iName" class="pInput"/>
				<p id="pDesc" class="pInput">Descrizione:</p>
				<textarea  rows="5" cols="35" name="Descrizione" id="pTextD"
					placeholder="Inserisci una descrizione" maxlength="100"></textarea>
				<p id="pUnit" class="pInput">UnitÓ di misura:</p>
				<select id="Pmis" class="pInput">
					<option value="Pz">Pz </option>
					<option value="Kg">Kg </option>
					<option value="Lt">Lt </option>
					<option value="Mq">Mq </option>
					<option value="Ml">Ml </option>
					<option value="Rt">Rt </option>
				</select>
				<input type="submit" value="Completa" onClick="send()">
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">

	function send(){
		var id=document.getElementById("iName").value;
		var desc=document.getElementById("pTextD").value;
		var e=document.getElementById("Pmis");
		var mis=e.options[e.selectedIndex].value;
		if(id!=null && id!=""){
			var xhttp=getXmlHttpRequest();
			var json={"id":id,"desc":desc,"mis":mis};
			jsonS=JSON.stringify(json);
			jsonS=encodeURIComponent(jsonS);
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
			xhttp.open("GET","creaProdotto?obj="+jsonS,true);
			xhttp.send();
		}		
	}




</script>
</html>