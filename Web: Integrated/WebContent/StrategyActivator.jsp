<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.trader.feedchecker.*" import="com.trader.util.*"
    import="java.util.*" import="com.trader.servlet.strategy.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Symbol Selector</title>
</head>
<body>
<%
ArrayList<String> selectOutput = (ArrayList<String>) request.getAttribute("selectOutput");
%>

<center>
	<form action="ActivateStrategy" method="post">
		<h2>Select a stock to activate</h2>
		<select id="companyNameAct" name="companyRef" >
		<% for (String output : selectOutput) {%>
			<option id="value"><%=output%></option>
		<%} %>
		</select>
		<input type="submit" value="Activate Strategy">
	</form>
	<br>
	<form action="DeactivateStrategy" method="post">
		<h2>Select a stock to deactivate</h2>
		<select id="companyNameDeact" name="companyRef" >
		<%for (String output : selectOutput) { %>
			<option id="value"><%=output%></option>
		<% } %>
		</select>
		<input type="submit" value="Deactivate Strategy">
	</form>
	<br>
	<form action="index.html">
		<input type="submit" value="Go back to main menu">
	</form>
</center>
</body>
</html>