<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*" import="com.trader.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Subscribed Stocks</title>
</head>
<body>
	<center>
		<h2>List of subscribed stocks</h2>
			<table width=100%>
				<tr>
					<td><strong>Symbol</strong></td>
					<td><strong>Company Name</strong></td>
				</tr>
				<%
					ResultSet rs = null;
					OpenConnection connectionPool = new OpenConnection("javaDemoDB");
					connectionPool.start();
					Connection con = connectionPool.getConnection();
					try {
						
						Statement statement = con.createStatement();
						rs = statement.executeQuery("SELECT symbolname, companyname FROM tbl_StockSymbols WHERE subscribed = 'Y'");
						
						if (rs != null) {
							while (rs.next()) {%>
								<tr>
									<td>
										<%=rs.getString(1)%>
									</td>
									<td>
										<%=rs.getString(2)%>
									</td>
								</tr>
						<%	}
						} else {
							out.print("<tr><td>No stocks are subscribed</td></tr>");
						}
					} catch (SQLException e) {
						out.print(e);
					}
				%>
		</table>
		<br>
		<form action="SymbolSelector.jsp">
			<input type="submit" value="Go back to the symbol selector screen">
		</form>
	</center>
</body>
</html>