package com.h4313.deephouse.server.network;

import java.io.IOException;
import java.util.ArrayList;

public class TcpReceiveExample implements CallBack
{
	private volatile boolean record;
	private ArrayList<String> messages;
	
	public TcpReceiveExample(int port)
	{
		messages = new ArrayList<String>();
		
		TcpReceiver tcpReceiver = null;
		try {
			tcpReceiver = new TcpReceiver(port, this);
			tcpReceiver.start();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(tcpClient.receive());
//		try {
//			tcpReceiver.closeReceiver();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		this.record = true;
	}
	
	public String callBack(String s)
	{
		if(this.record)
		{
			messages.add(s);
			System.out.println(messages.get(messages.size()-1));
//			this.record = false;
		}
		
		return null;
	}
}
