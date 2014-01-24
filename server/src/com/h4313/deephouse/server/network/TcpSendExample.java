package com.h4313.deephouse.server.network;

import java.io.IOException;
import java.util.ArrayList;

public class TcpSendExample implements CallBack
{
	private ArrayList<String> messages;
	
	public TcpSendExample(String ip, int port)
	{
		messages = new ArrayList<String>();
//		messages.add("AAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//		messages.add("A55A0B0700597B0A0088B59F00CC");
		messages.add("A55A6B0550000000FF9F1E0530B3"); // on
		messages.add("A55A6B0570000000FF9F1E0530D3"); // off
		
		TcpSender tcpSender = null;
		try {
			tcpSender = new TcpSender(ip, port, this);
			tcpSender.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(tcpSender.receive());
		try {
			//tcpSender.closeSender();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String callBack(String s)
	{
//		System.out.println("Lancement du callbacksend");
		String value = null;
		
		if(messages.size() > 0)
		{
			value = messages.get(0);
			messages.remove(0);
		}
		
		return value;
	}
}
