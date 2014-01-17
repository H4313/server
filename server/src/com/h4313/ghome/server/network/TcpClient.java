package com.h4313.ghome.server.network;

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

public class TcpClient extends Thread
{
	private volatile boolean alive;
	private Socket socket;
	private OutputStream out;
	private BufferedReader in;
	private String clientAddress;
	private Integer clientPort;
	private static int lastClientId = 0;
    private int clientId;
	private String catalogue;
	
	public TcpClient(String ip, int port) throws IOException
	{
		try 
		{
			this.alive = true;
			this.clientId = TcpClient.lastClientId++;
			InetAddress clientAddress = new InetAddress(ip);
			Integer clientPort = new Integer(clientSocket.getPort());
			this.socket = clientSocket;
			this.clientAddress = clientAddress.toString();
			this.clientPort = clientPort;
			this.catalogue = catalogue;
			
			this.out = this.socket.getOutputStream();
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			throw new IOException("Erreur a la connexion avec le client");
		}		
	}
	
	private void setPort(Integer clientPort) throws Exception
	{
		try
		{
			this.socket = new Socket(this.socket.getInetAddress(), clientPort.intValue());
			this.out = this.socket.getOutputStream();
			this.clientPort = clientPort;
		}
		catch(Exception e)
		{
			throw new Exception("Probleme lors de la connexion sur le nouveau port");
		}
	}
	
	@Override
	public void run()
	{
		try
		{
			this.runCatalogue();
		}
		catch(Exception e)
		{
//			e.printStackTrace();
			System.out.println("Erreur avec le client TCP");
		}
	}
	
	public void runCatalogue() throws Exception
	{
		try
		{
			String received;
			
			while(this.alive)
			{
					received = this.receive();
					if(received != null)
					{
						System.out.println("----- Catalogue START -----");
						if(received.toLowerCase().contains((CharSequence)"catalog"))
						{
							System.out.println("Demande du catalogue");
							
							this.send(this.catalogue);
							
							System.out.println("Catalogue envoye");
						}
//						else
//						{
//							System.out.println(received);
//						}
						System.out.println("----- Catalogue END -----");
						this.receive(); // CRLF
					}
			}
		}
		catch(Exception e)
		{
//			e.printStackTrace();
			throw new Exception("Le catalogue n'a pas pu etre envoye !");
		}
	}
	
	/**
	* Recoit un message.
	*/
	public String receive() throws Exception
	{
		String message = null;

		try
		{
			message = this.in.readLine();
		}
		catch(Exception e)
		{
//			e.printStackTrace();
			throw new Exception("Impossible de recevoir un message");
		}
		
		return message;
	}
	
	/**
	* Envoi un message.
	*/
	public void send(String message) throws Exception
	{
		try
		{
			out.write(message.getBytes());
		}
		catch(Exception e)
		{
			throw new Exception("Impossible d'envoyer un message");
		}
	}
	
	public void stopThread() throws Exception
	{
		this.alive = false;
		this.closeClient();
	}
	
	/**
	* Fermeture du client, Fermeture des connexions.
	*/
	public void closeClient() throws Exception
	{
		try
		{				
			this.in.close();
			this.out.close();
			this.socket.close();
		}
		catch(Exception e)
		{
			throw new Exception("Impossible d'arreter le serveur.");
		}
	}
	
	/**
	 * Getters
	 */
	public String getClientAddress()
	{
		return this.clientAddress;
	}
	
	public Integer getClientPort()
	{
		return this.clientPort;
	}
	
	public int getClientId()
	{
		return this.clientId;
	}
}