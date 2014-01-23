package com.h4313.deephouse.server.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.h4313.deephouse.server.util.Constant;

public class TcpReceiver extends Thread
{
	private volatile boolean alive;
	private ServerSocket serverSocket;
	private static int lastServerId = 0;
    private int serverId;
    private InputStream in;
    private Socket socket;
    private CallBack applicant;
    
	/**
	 * A l'ecoute sur port.
	 * @param port
	 * @throws IOException
	 */
    public TcpReceiver(int port, CallBack applicant) throws IOException
    {
		try
		{
			this.alive = true;
			this.serverId = TcpReceiver.lastServerId++;
			this.serverSocket = new ServerSocket(port);
			this.applicant = applicant;
		}
		catch (IOException e) 
		{ 
			throw new IOException("Impossible d'ouvrir le port " + port);
		}
    }
    
	@Override
	public void run()
	{
		try
		{
			while(this.alive)
			{
				this.applicant.callBack(this.receive());
			}
		}
		catch(Exception e)
		{
//			e.printStackTrace();
			System.out.println("Erreur avec le TCP");
		}
	}
	
	/**
	* Recoit un message.
	*/
	public String receive() throws Exception
	{
		byte[] buffer = new byte[Constant.TCP_FRAME_LENGTH];
		
		try
		{
			this.in.read(buffer);
		}
		catch(Exception e)
		{
//			e.printStackTrace();
			throw new Exception("Impossible de recevoir un message");
		}
		
		return new String(buffer);
	}
    
	/**
	 * Ouvre une socket, un input et un output.
	 * @throws Exception
	 */
	public void startListening() throws Exception
	{
		try
		{
			this.socket = serverSocket.accept();
			this.in = this.socket.getInputStream();
		}
		catch (Exception e) 
		{ 
//			e.printStackTrace();
			throw new Exception("Erreur client rejete");
		}
	}

	/**
	* Arret du serveur.
	* Ferme serverSocket.
	*/
	public void closeServer() throws Exception
	{
		try
		{	
			this.alive = false;
			this.in.close();
			this.serverSocket.close();
		}
		catch(Exception e)
		{
			throw new Exception("Impossible d'arreter le server.");
		}
	}
}


