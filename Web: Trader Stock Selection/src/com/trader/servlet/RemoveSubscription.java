package com.trader.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
 * Servlet implementation class RemoveSubscription
 */
@WebServlet("/RemoveSubscription")
public class RemoveSubscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveSubscription() {
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
		String stockSelection = request.getParameter("companyRef");
		OpenConnection connectionPool = new OpenConnection("javaDemoDB");
		connectionPool.start();
		Connection con = connectionPool.getConnection();
		System.out.println(stockSelection);
		try {
			PreparedStatement pst = null;
			pst = con.prepareStatement("UPDATE tbl_StockSymbols SET subscribed='N' WHERE companyname=?");
			pst.setString(1, stockSelection);
			pst.executeUpdate();
			response.sendRedirect("SymbolSelector.jsp");
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}
}
