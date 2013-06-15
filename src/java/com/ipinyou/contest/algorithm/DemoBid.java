package com.ipinyou.contest.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DemoBid implements Algorithm{
    public static final Logger log = LoggerFactory.getLogger(DemoBid.class);

	private Set<String> domains = new HashSet<String>();
	
	private String model_file = null ;
	private String conf_file = null ;
	@Override
	public void init() {
        log.info("init....") ;
		domains.addAll(
				readModelFileToLines("model_file")
		);
	}

	@Override
	public int getBidPrice(BidRequest bidRequest) {
		String domain = bidRequest.getDomain();
		if(domain!=null){
			if(domains.contains(domain)){
				return 500;
			}
		}
		return -1;
	}
	
	private List<String> readModelFileToLines(String filename) {
		List<String> lines = new ArrayList<String>();
		InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
		if(resourceAsStream==null){
			resourceAsStream = getClass().getResourceAsStream("/".concat(filename));
			if(resourceAsStream==null){
				throw new RuntimeException("init algorithm error ");
			}
		}
		String line = null;
		BufferedReader br = null;
		try {
			br  = new BufferedReader(new InputStreamReader(resourceAsStream));
			while((line = br.readLine())!=null){
				lines.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(br!=null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lines;
	}
    public static void main(String [] args)
    {
       DemoBid demo_bid = new DemoBid() ;
       demo_bid.init() ;
       BidRequest bidRequest = new BidRequest() ;
       bidRequest.setDomain("HELLO") ;
       System.out.println(demo_bid.getBidPrice(bidRequest))  ;
       bidRequest.setDomain("world") ;
       System.out.println(demo_bid.getBidPrice(bidRequest)) ;
    }
}
