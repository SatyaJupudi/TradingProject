package com.trader.feedchecker;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Map;

import com.trader.util.OpenConnection;

// Get quote data from Yahoo, usage java TestYahoo <stock1> <stock2> ....

public class TestYahoo implements Runnable
{
    private static String output;
    private volatile boolean isRunning = true;
    private volatile String[] symbols;
    
    public TestYahoo(String... symbols) {
    	this.symbols = symbols;
    }
    
    public void start() {

		if (symbols != null) {
			float previous = 0;
	    	ResultSet rs = null;
	    	OpenConnection connectionPool = new OpenConnection("javaDemoDB");
	    	connectionPool.start();
	    	Connection con = connectionPool.getConnection();
	        GetYahooMarketData yahooReader = new GetYahooMarketData();
	        while (true)
	        {
	        	if (isRunning == false) {
    				break;
    			}
	        	// Get Quotes 
	            Map<String, GetYahooMarketData.QuoteData> data = null;
				try {
					data = yahooReader.getQuote(symbols);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            for (Map.Entry<String, GetYahooMarketData.QuoteData> entry : 
	                 data.entrySet())
	            {
	                GetYahooMarketData.QuoteData quote = entry.getValue();
	                String symbolName = entry.getKey();
	                
	                if (previous == 0) {
    	                try {
    	    	    		PreparedStatement pstSelect = con.prepareStatement("SELECT bid FROM tbl_SubscribedStocks WHERE symbol = ? ORDER BY ts DESC LIMIT 1");
    	    	    		pstSelect.setString(1, symbolName);
    	    	    		rs = pstSelect.executeQuery();
    	    	    		if (rs != null) {
    	    	    			rs.next();
    	    	    			previous = Float.parseFloat(rs.getString(1));
    	    	    		}
    	    	    	} catch (SQLException e) {
    	    	    		System.out.println(e);
    	    	    	}
	                }
	                
	                if (quote.bidPrice != previous) {
		                try {
		                	PreparedStatement pstInsert = con.prepareStatement("INSERT INTO tbl_SubscribedStocks VALUES (?, ?, ?, ?)");
		                	pstInsert.setString(1, symbolName);
		                	pstInsert.setString(2, Float.toString(quote.bidPrice));
		                	pstInsert.setString(3, Float.toString(quote.askPrice));
		                	pstInsert.setDate(4, new Date(Calendar.getInstance().getTimeInMillis()));
		                	pstInsert.execute();
		                } catch(SQLException e) {
		                	System.out.println(e);
		                }
		           }
	               previous = quote.bidPrice;
	           }
	       }
	}
    }
    
    public void run() {
    	
    }
    
    public static void main(String[] args) {
    	(new Thread(new TestYahoo())).start();
    }
    
    
    public void kill() {
    	isRunning = false;
    }
    
	/*public static void main(String[] args) throws Exception
    {
        GetYahooMarketData yahooReader = new GetYahooMarketData();
        while (true)
        {
            // Get Quotes 
            Map<String, GetYahooMarketData.QuoteData> data = 
                                                    yahooReader.getQuote(args);
            for (Map.Entry<String, GetYahooMarketData.QuoteData> entry : 
                 data.entrySet())
            {
                GetYahooMarketData.QuoteData quote = entry.getValue();
//                System.out.printf ("%s [%dx%.2f] x [%.2fx%d]\n",
//                                   entry.getKey(),
//                                   quote.bidSize,
//                                   quote.bidPrice,
//                                   quote.askPrice,
//                                   quote.askSize);
                setOutput( 
                		Float.toString(quote.bidSize +
                        quote.bidPrice +
                        quote.askPrice +
                        quote.askSize));
            }
        }
    }*/
    
    private static void setOutput(String output) {
    	TestYahoo.output = output;
    }
    
    public String getOutput() {
    	return output;
    }
}
