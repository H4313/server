package com.h4313.deephouse.server.network;

import java.io.IOException;

public class TcpExample implements CallBack
{
	private boolean broadcast;
	
	public TcpExample(String ip, int port)
	{
		TcpSender tcpClient = null;
		try {
			tcpClient = new TcpSender(ip, port, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(tcpClient.receive());
		try {
			tcpClient.closeClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.broadcast = true;
	}
	
	public String callBack(String s)
	{
		String value = null;
		
		if(broadcast)
		{
			value = "AABB22837423827364730182ABAB";

			this.broadcast = false;
		}
		
		return value;
	}
}
