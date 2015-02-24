package com.trader.servlet.strategy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.trader.util.OpenConnection;

/**
 * Servlet implementation class StrategyActivator
 */
@WebServlet("/StrategyActivator")
public class StrategyActivator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StrategyActivator() {
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
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		OpenConnection connectionPool = new OpenConnection("javaDemoDB");
		connectionPool.start();
		Connection con = connectionPool.getConnection();
		try {
			Statement st1 = con.createStatement();
			rs1 = st1.executeQuery("SELECT symbolname, companyname FROM tbl_StockSymbols WHERE subscribed = 'Y' AND trading = 'N' ORDER BY companyname");
			ArrayList<String> selectOutputSub = new ArrayList<>();	
			while (rs1.next()) {
				selectOutputSub.add(rs1.getString(2));
			}
			request.setAttribute("selectOutputSub", selectOutputSub);
			
			Statement st2 = con.createStatement();
			rs2 = st1.executeQuery("SELECT symbolname, companyname FROM tbl_StockSymbols WHERE trading = 'Y' ORDER BY companyname");
			ArrayList<String> selectOutputTrading = new ArrayList<>();	
			while (rs2.next()) {
				selectOutputTrading.add(rs2.getString(2));
			}
			request.setAttribute("selectOutputTrading", selectOutputTrading);
			getServletConfig().getServletContext().getRequestDispatcher("/StrategyActivator.jsp").forward(request,response);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
