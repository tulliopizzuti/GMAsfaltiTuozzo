<%@page import="it.asfalti.javabean.MagazzinoBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="it.asfalti.javabean.MagazzinoBean" %>   
<%
	String error=(String)session.getAttribute("error");
	MagazzinoBean user=(MagazzinoBean)session.getAttribute("user");
	if(user!=null)
		response.sendRedirect("pagecomposer="+user.getTipo()+"page.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="style\login.css" rel="stylesheet" type="text/css">

<title>Login</title>
</head>
<body>
	<div class="main">
		<div class="login_form">
			<form action="login" method="post">
				<div id="username">
					<label>Username: </label><input type="text" name="username" placeholder="E-mail" required="required">
				</div>    
				<div id="password">
					<label>Password: </label><input type="password" autocomplete="off" name="password" placeholder="Password" value="" required="required">
				</div>
				<div id="form_button">
					<input type="submit" value="Accedi">
					<input type="reset" value="Azzera">
				</div>
				<%if(error!=null){ %>
					<div>
						<p id="error"><%=error	%></p>
					</div>
				<%} %>
			</form>	
		</div>	
	</div>
</body>
</html>