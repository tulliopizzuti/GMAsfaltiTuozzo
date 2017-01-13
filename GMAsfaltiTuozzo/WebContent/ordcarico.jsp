<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="it.asfalti.javabean.MagazzinoBean,java.util.ArrayList,
it.asfalti.javabean.OperazioneSospesaBean,it.asfalti.javabean.ComposizioneCaricoBean
,it.asfalti.javabean.ProdottoBean,it.asfalti.javabean.OrdineCaricoBean"
 %>
<%
	MagazzinoBean user=(MagazzinoBean)session.getAttribute("user");
	if(user==null || !user.getTipo().equals("admin")){
		session.removeAttribute("user");
		session.invalidate();
		response.sendRedirect("pagecomposer?responsepage=login");
		return;	
	}
	ArrayList<OrdineCaricoBean> ops=(ArrayList<OrdineCaricoBean>)request.getAttribute("ops"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="style\ordcarico.css" rel="stylesheet" type="text/css">
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
			<%if(ops!=null && ops.size()>0){ %>
				<h2>Richieste di carico</h2>
				<%for(OrdineCaricoBean or:ops){ %>
					<form onsubmit="return reg(this)" >
						<table class="opLine">        
							<tr> 
								<th>Codice Operazione </th>
								<th>Richiedente </th>
								<th>Data/Ora </th>
								<th>Tipo </th>
								<th>Mittente/Destinazione </th>
								<th>Stato</th>
							</tr>
							<tr>
								<td><%=or.getIdOp() %> </td>
								<td><%=or.getIdM() %></td>
								<td><%=or.getData() %> </td>
								<td><%=or.getTipo() %> </td>
								<td><%=or.getDa_a() %> </td>
								<td><%=or.getStato() %> </td>							
							</tr>
						</table>
						<%for(ComposizioneCaricoBean ccb:or.getCompCar()){ %>
							<table class="opComp">
								<tr> 
									<th>Codice Prodotto </th>
									<th>Quantità </th>
									<th>Magazzini con disponibilità</th>
								</tr>
								<tr>
									<td><%=ccb.getProdotto().getId() %> </td>
									<td><%=ccb.getQuantita()%> </td>
									<td>
										<select name="magToSend">
											<%for(MagazzinoBean m:ccb.getMags()){ %>
												<%if(!(or.getIdM().equals(m.getIdM()))){ %>
													<option value="<%=m.getIdM() %>" ><%=m.getIdM() %> </option>
												<%} %>
											<%} %>
										</select>
									</td>
									<td>
										<input type="hidden" value="<%=ccb.getProdotto().getId() %> " name="idProduct"/>
										<input type="hidden" value="<%=ccb.getQuantita()%> " name="quant"/>
									</td>
								</tr>
							</table>
						<%} %>
						<input type="hidden" value="<%=or.getIdOp() %> " name="idOperation"/>
						<input type="hidden" value="<%=or.getIdM() %>" name="idMagToSend"/>
						<input type="submit" value="Registra">				
					</form>
				<%} %>
			<%} else { %>
				<div id="nulldisp">
					Non hai Operazioni di carico in sospeso!
				</div>
			<%} %>
		</div>
	</div>
</body>
<script type="text/javascript">
	
	function reg(form){
		var products=[];
		for(i=0;i<form.idProduct.length;i++){
			products.push(form.idProduct[i].value);
		}
		var q=[];
		for(i=0;i<form.quant.length;i++){
			q.push(form.quant[i].value);
		}
		var mags=[];
		for(i=0;i<form.magToSend.length;i++){
			mags.push(form.magToSend[i].options[form.magToSend[i].selectedIndex].value);
		}
		var idOp=form.idOperation.value;
		var magToSend=form.idMagToSend.value;
		if(mags.length==products.length && products.length==q.length){
			var xhttp=getXmlHttpRequest();
			var json={"prods":products,"q":q,"mags":mags,"idOp":idOp,"idMag":magToSend};
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
			xhttp.open("GET","caricaMag?obj="+jsonS,true);
			xhttp.send();
		}
		else{
			window.alert("Errore");
		}
		
		
		
		
		
		
		
		
		
		return false;
	}
</script>
</html>