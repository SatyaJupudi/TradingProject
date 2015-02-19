<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.trader.feedchecker.*" import="com.trader.util.*"
    import="java.sql.*" import="com.trader.servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Symbol Selector</title>
</head>
<body>
<%
ResultSet rs = null;
OpenConnection connectionPool = new OpenConnection("javaDemoDB");
connectionPool.start();
Connection con = connectionPool.getConnection();
try {
	Statement statement = con.createStatement();
	rs = statement.executeQuery("SELECT symbolname, companyname FROM tbl_StockSymbols ORDER BY companyname");
%>

<center>
	<form action="SetSubscription" method="post">
		<h2>Select a companies stock</h2>
		<select id="companyName" name="companyRef" >
		<% while(rs.next()) {%>
			<option id="value"><%=rs.getString(2)%></option>
		<% } %>
		</select>
		<input type="submit" value="Subscribe">
	</form>
</center>
<%
} catch (SQLException e) {
	out.print(e);
}
%>
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
	<form action="SubscribedStocks.jsp">
		<input type="submit" value="See subscribed stocks">
	</form>
	<br>
	<form action="RemoveSubscriptions.jsp">
		<input type="submit" value="Remove stock subscriptions">
	</form>
</center>
</body>
</html>