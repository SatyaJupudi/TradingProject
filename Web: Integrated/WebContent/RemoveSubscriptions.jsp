<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.trader.feedchecker.*" import="com.trader.util.*"
    import="java.util.*" import="com.trader.servlet.stock.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
ArrayList<String> selectOutput = (ArrayList<String>) request.getAttribute("selectOutput");
%>

<center>
	<form action="SetToN" method="post">
		<h2>Select a companies stock</h2>
		<select id="companyName" name="companyRef" >
		<% for (String output : selectOutput) {%>
			<option id="value"><%=output%></option>
		<% } %>
		</select>
		<input type="submit" value="Unsubscribe">
	</form>
	<br>
<form action="SubscribedStocks" method="post">
	<input type="submit" value="See subscribed stocks">
</form>
<br>
<form action="StockSelector" method="post">
	<input type="submit" value="Go back to the symbol selector screen">
</form>
</center>
</body>
</html>