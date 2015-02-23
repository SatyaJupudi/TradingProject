<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.trader.feedchecker.*" import="com.trader.util.*"
    import="java.util.*" import="com.trader.servlet.stock.*"%>
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
	<form action="SetToY" method="post">
		<h2>Select a companies stock</h2>
		<select id="companyName" name="companyRef" >
		<% for (String output : selectOutput) {%>
			<option id="value"><%=output%></option>
		<% } %>
		</select>
		<input type="submit" value="Subscribe">
	</form>
</center>
<center>
	<form action="SetSubscription" method="post">
		<h2>Enter a stock symbol</h2>
		<input type="text" name="companyRef">
		<input type="submit" value="Subscribe">
	</form>
</center>
<br>
<br>
<center>
	<form action="SubscribedStocks" method="post">
		<input type="submit" value="See subscribed stocks">
	</form>
	<br>
	<form action="RemoveSubscription" method="post">
		<input type="submit" value="Remove stock subscriptions">
	</form>
	<br>
	<form action="index.html">
		<input type="submit" value="Return to Main Menu">
	</form>
</center>
</body>
</html>