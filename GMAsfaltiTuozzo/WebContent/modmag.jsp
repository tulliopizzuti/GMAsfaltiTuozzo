<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="it.asfalti.javabean.MagazzinoBean,java.util.ArrayList" %>
<%
	MagazzinoBean user=(MagazzinoBean)session.getAttribute("user");
	if(user==null || !user.getTipo().equals("admin")){
		session.removeAttribute("user");
		session.invalidate();
		response.sendRedirect("pagecomposer?responsepage=login");
		return;	
	}
	ArrayList<MagazzinoBean>mags=(ArrayList<MagazzinoBean>)request.getAttribute("mags");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="style\modmag.css" rel="stylesheet" type="text/css">
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
				<p id="pName" class="pInput">Nome magazzino:</p>
				<select id="prodName" onchange="set(this)">
					<option value=""></option>
					<%for(MagazzinoBean p:mags){ %>
						<option value="<%=p.getIdM() %>"><%=p.getIdM() %></option>
					<%} %>
				</select>
				
				<p id="pDesc" class="pInput">Descrizione:</p>
				<textarea  rows="5" cols="35" name="Descrizione" id="mDesc"
					placeholder="Inserisci una descrizione" maxlength="100"></textarea>
				<input type="submit" value="Aggiorna descrizione" onClick="updateDesc()">
				
				<p id="pNCitta" class="pInput">Città:</p>
				<input type="text" maxlength="20" id="mCitta" class="pInput"/>
				<input type="submit" value="Aggiorna città" onClick="updateCit()">
				
				<p id="pVia" class="pInput">Via:</p>
				<input type="text" maxlength="20" id="mVia" class="pInput"/>
				<input type="submit" value="Aggiorna via" onClick="updateVia()">
				
				<p id="pCAP" class="pInput">CAP:</p>
				<input type="text" maxlength="5" id="mCAP" class="pInput"/>
				<input type="submit" value="Aggiorna CAP" onClick="updateCAP()">
				
				
				<p id="pNCiv" class="pInput">Numero civico:</p>
				<input type="text" maxlength="3" id="mNCiv" class="pInput"/>
				<input type="submit" value="Aggiorna Numero Civico" onClick="updateNC()">
				
				<p id="pPass" class="pInput">Password:</p>
				<input type="text" maxlength="25" id="mPass" class="pInput"/>
				<input type="submit" value="Aggiorna Password" onClick="updatePass()">
				
			</div>			
		</div>
	</div>
</body>
	<script type="text/javascript">
		var id="";
		function set(sel){
			id=sel.options[sel.selectedIndex].value;		
		}
		function updatePass(){
			var value=document.getElementById("mPass").value;
			var campo="passwordM";
			if(id!=null && id!=""){ 
					if(value!="" && value!=null){
						var xhttp=getXmlHttpRequest();
						var json={"id":id,"value":value,"campo":campo};
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
						xhttp.open("GET","updateMagP?obj="+jsonS,true);
						xhttp.send();
				}
				else{
					window.alert("Inserire un valore!");	
				}
			}
			else{
				window.alert("Selezionare un magazzino");
			}
		}
		
		
		function updateNC(){
			var value=document.getElementById("mNCiv").value;
			var campo="nCivico";
			if(id!=null && id!=""){ 
				if(value!="" && value!=null){
					var xhttp=getXmlHttpRequest();
					var json={"id":id,"value":value,"campo":campo};
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
					xhttp.open("GET","updateMagP?obj="+jsonS,true);
					xhttp.send();
				}
				else{
					window.alert("Inserire un valore!");	
				}
			}else{
				window.alert("Selezionare un magazzino");
			}
		}
		
		function updateCAP(){
			var value=document.getElementById("mCAP").value;
			var campo="cap";
			if(id!=null && id!=""){ 
				if(value!="" && value!=null){
					var xhttp=getXmlHttpRequest();
					var json={"id":id,"value":value,"campo":campo};
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
					xhttp.open("GET","updateMagP?obj="+jsonS,true);
					xhttp.send();
				}
				else{
					window.alert("Inserire un valore!");	
				}
			}else{
				window.alert("Selezionare un magazzino");
			}
		}
		function updateVia(){
			var value=document.getElementById("mVia").value;
			var campo="via";
			if(id!=null && id!=""){ 
				if(value!="" && value!=null){
					var xhttp=getXmlHttpRequest();
					var json={"id":id,"value":value,"campo":campo};
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
					xhttp.open("GET","updateMagP?obj="+jsonS,true);
					xhttp.send();
				}
				else{
					window.alert("Inserire un valore!");	
				}
			}else{
				window.alert("Selezionare un magazzino");
			}
		}
		function updateDesc(){
			var value=document.getElementById("mDesc").value;
			var campo="descrizioneM";
			if(id!=null && id!=""){ 
				if(value!="" && value!=null){
					var xhttp=getXmlHttpRequest();
					var json={"id":id,"value":value,"campo":campo};
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
					xhttp.open("GET","updateMagP?obj="+jsonS,true);
					xhttp.send();
				}
				else{
					window.alert("Inserire un valore!");	
				}
			}else{
				window.alert("Selezionare un magazzino");
			}
		}
		function updateCit(){
			var value=document.getElementById("mCitta").value;
			var campo="citta";
			if(id!=null && id!=""){ 
				if(value!="" && value!=null){
					var xhttp=getXmlHttpRequest();
					var json={"id":id,"value":value,"campo":campo};
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
					xhttp.open("GET","updateMagP?obj="+jsonS,true);
					xhttp.send();
				}
				else{
					window.alert("Inserire un valore!");	
				}
			}else{
				window.alert("Selezionare un magazzino");
			}
		}
		
		
	</script>
</html>