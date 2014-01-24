package com.h4313.deephouse.server.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.h4313.deephouse.server.util.Constant;

public class TcpSender extends Thread
{
	private volatile boolean alive = true;
	private Socket socket;
	private OutputStream out;
	private String clientAddress;
	private Integer clientPort;
	private static int lastClientId = 0;
    private int clientId;
    private CallBack applicant;
	
	public TcpSender(String ip, int port, CallBack applicant) throws IOException
	{	
		this.alive = true;
		this.clientId = TcpSender.lastClientId++;
		
		try 
		{	
//			System.out.println("ip = " + ip);
//			System.out.println("port = " + port);
			this.socket = new Socket(ip, port);
			
			this.clientAddress = ip;
			this.clientPort = port;
			
			this.out = this.socket.getOutputStream();
			
			this.applicant = applicant;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			throw new IOException("Erreur a la connexion avec le client");
		}		
	}
	
	@Override
	public void run()
	{
		try
		{
			String s = null;
			while(true)
			{
				s = applicant.callBack(null);
				System.out.println("Retour du callback=" + s);
				
				if(s != null)
					send(s);
				
				Thread.sleep(20000);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Erreur avec le TCP");
		}
	}
	
	/**
	* Envoi un message.
	*/
	public void send(String message) throws Exception
	{
		try
		{
//			message = "F602010008000000FF9F1E070C00";
			byte[] byteswrite = new byte[Constant.TCP_FRAME_LENGTH];
			byteswrite = message.getBytes();
			out.write(byteswrite);
			
//			System.out.println("Message envoye en string =" + message);
//			System.out.println("Message envoye en bytes =" + byteswrite);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("Impossible d'envoyer un message");
		}
	}
	
	/**
	* Fermeture du client, Fermeture des connexions.
	*/
	public void closeSender() throws Exception
	{
		try
		{		
			this.alive = false;		
			this.out.close();
			this.socket.close();
		}
		catch(Exception e)
		{
			throw new Exception("Impossible d'arreter le client.");
		}
	}
}