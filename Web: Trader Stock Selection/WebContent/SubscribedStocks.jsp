<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*" import="com.trader.util.*"%>
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
				ArrayList<String> selectOutput = (ArrayList<String>) request.getAttribute("selectOutput");
					if (selectOutput != null) {
							for (int x = 0; x < selectOutput.size(); x = x + 2) {%>
								<tr>
									<td>
										<%=selectOutput.get(x)%>
									</td>
									<td>
										<%=selectOutput.get(x+1)%>
									</td>
								</tr>
						<% 	}
						} else {
							out.print("<tr><td>No stocks are subscribed</td></tr>");
						}
				%>
		</table>
		<br>
		<form action="StockSelector" method="post">
			<input type="submit" value="Go back to the symbol selector screen">
		</form>
	</center>
</body>
</html>