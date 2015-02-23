package com.trader.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trader.util.OpenConnection;

/**
 * Servlet implementation class SubscribedStoxks
 */
@WebServlet("/SubscribedStocks")
public class SubscribedStocks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubscribedStocks() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs = null;
		OpenConnection connectionPool = new OpenConnection("javaDemoDB");
		connectionPool.start();
		Connection con = connectionPool.getConnection();
		try {
			
			Statement statement = con.createStatement();
			rs = statement.executeQuery("SELECT symbolname, companyname FROM tbl_StockSymbols WHERE subscribed = 'Y'");
			ArrayList<String> selectOutput = new ArrayList<>();	
			while (rs.next()) {
				selectOutput.add(rs.getString(1));
				selectOutput.add(rs.getString(2));
			}
			request.setAttribute("selectOutput", selectOutput);
			getServletConfig().getServletContext().getRequestDispatcher("/SubscribedStocks.jsp").forward(request,response);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}
