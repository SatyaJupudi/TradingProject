package com.trader.feedchecker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
// Get quote data for a list of symbols and returns it in a Map

public class GetYahooMarketData
{
	// Quote Data class
	public class QuoteData
	{
		public float askPrice;
		public float bidPrice;
		public int askSize;
		public int bidSize;
		public int volume;
		public float closeprice;
	}

	public Map<String, QuoteData> getQuote(String[] stocks) throws Exception
	{
		// Build the URL
		StringBuilder url =
				new StringBuilder("http://download.finance.yahoo.com/d/quotes.csv?s=");
		for (String s : stocks)
			url.append(s + ",");
		url.deleteCharAt(url.length()-1);
		// Properties is for bid and ask
		url.append("&f=a0a5b0b6v0p0&e=.csv");
		// Get the csv lines from Yahoo
		List<String> csv = getCsv(url.toString());

		// Build response
		Map<String, QuoteData> quotes = new HashMap<String, QuoteData>();
		int i = 0;
		for (String line : csv)
		{
			// Parse csv lines
			String[] fields = line.split(",", -1);

			// Should be 4 values if not discard line
			if (fields.length == 6 || fields.length == 8)
			{
				QuoteData quote = new QuoteData();
				try
				{
					if (fields.length == 6)
					{
						quote.askPrice = Float.parseFloat(fields[0]);
						quote.askSize = Integer.parseInt(fields[1]);
						quote.bidPrice = Float.parseFloat(fields[2]);
						quote.bidSize = Integer.parseInt(fields[3]);
						quote.volume = Integer.parseInt(fields[4]);
						quote.closeprice=Float.parseFloat(fields[5]);
					}
					else
					{
						quote.askPrice = Float.parseFloat(fields[0]);
						quote.bidPrice = Float.parseFloat(fields[3]);
						quote.volume = Integer.parseInt(fields[6]);
						quote.closeprice=Float.parseFloat(fields[7]);
						quote.askSize = (1000* Integer.parseInt(fields[1])) + Integer.parseInt(fields[2]);
						quote.bidSize = (1000* Integer.parseInt(fields[4])) + Integer.parseInt(fields[5]);
					}
					// Insert quote with stock as key
					quotes.put(stocks[i], quote);
				}
				catch (NumberFormatException e) {}
			}
			i++;
		}
		return quotes;
	}

	// Returns list of csv from Yahoo
	private List<String> getCsv(String url) throws Exception
	{
		List<String> response = new ArrayList<String>();
		URL obj = new URL(url);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// This is a GET request
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = con.getResponseCode();
		// If the response code is not OK then return empty line
		if (responseCode != 200)
			return response;

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine = null;


		while ((inputLine = in.readLine()) != null)
			response.add(inputLine);
		in.close();
		return response;
	}
} 
