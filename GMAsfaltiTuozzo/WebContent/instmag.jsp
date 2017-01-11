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
<link href="style\instmag.css" rel="stylesheet" type="text/css">
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
			<div id="information">
				<p id="pName" class="pInput">Nome Magazzino:</p>
				<input type="text" maxlength="6" id="mID" class="pInput"/>
				
				<p id="pDesc" class="pInput">Descrizione:</p>
				<textarea  rows="5" cols="35" name="Descrizione" id="mDesc"
					placeholder="Inserisci una descrizione" maxlength="100"></textarea>
				
				<p id="pNCitta" class="pInput">Città:</p>
				<input type="text" maxlength="20" id="mCitta" class="pInput"/>
				
				<p id="pVia" class="pInput">Via:</p>
				<input type="text" maxlength="20" id="mVia" class="pInput"/>
				
				<p id="pCAP" class="pInput">CAP:</p>
				<input type="text" maxlength="5" id="mCAP" class="pInput"/>
				
				
				<p id="pNCiv" class="pInput">Numero civico:</p>
				<input type="text" maxlength="3" id="mNCiv" class="pInput"/>
				
				<p id="pPass" class="pInput">Password:</p>
				<input type="text" maxlength="25" id="mPass" class="pInput"/>
				

				<input type="submit" value="Completa" onClick="send()">
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">

	function send(){
		var id=document.getElementById("mID").value;
		var desc=document.getElementById("mDesc").value;
		var cit=document.getElementById("mCitta").value;
		var via=document.getElementById("mVia").value;
		var cap=document.getElementById("mCAP").value;
		var nC=document.getElementById("mNCiv").value;
		var pass=document.getElementById("mPass").value;
		var flag=false;	
		if(id!="" && id!=null)
			if(desc!="" && desc!=null)
				if(cit!="" && cit!=null)
					if(via!="" && via!=null)
						if(cap!="" && cap!=null)
							if(nC!="" && nC!=null)
								if(pass!="" && pass!=null)
									flag=true;
			if(flag){
				var xhttp=getXmlHttpRequest();
				var json={
						"idM":id,
						"desc":desc,
						"cit":cit,
						"via":via,
						"cap":cap,
						"nC":nC,
						"pass":pass
						};
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
				xhttp.open("GET","creaMag?obj="+jsonS,true);
				xhttp.send();
			}
			else{
				window.alert("Completa tutti i campi per continuare");
			}
	}




</script>
</html>