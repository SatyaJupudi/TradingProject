package com.trader.servlet.stock;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trader.util.OpenConnection;

/**
 * Servlet implementation class SubscribedStocks
 */
@WebServlet("/SetToY")
public class SetToY extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetToY() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stockSelection = (String) request.getParameter("companyRef");
		OpenConnection connectionPool = new OpenConnection("javaDemoDB");
		connectionPool.start();
		Connection con = connectionPool.getConnection();
		try {
			PreparedStatement pst = null;
			pst = con.prepareStatement("UPDATE tbl_StockSymbols SET subscribed='Y' WHERE companyname=?");
			pst.setString(1, stockSelection);
			pst.executeUpdate();
			ServletContext context1 = this.getServletContext();
			RequestDispatcher dispatcher1 = context1.getRequestDispatcher("/StartFeed");
			dispatcher1.forward(request, response);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
