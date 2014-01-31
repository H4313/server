package com.h4313.deephouse.server.main;

import java.util.Scanner;

import com.h4313.deephouse.network.TcpReceiveExample;
import com.h4313.deephouse.network.TcpSendExample;

public class Main {
	public static void main(String[] args) throws Exception
	{
//		if(args.length == 1)
//		{
//			int port = Integer.valueOf(args[0]);
//			new TcpReceiveExample(port);
//		}
//		else if(args.length == 2)
//		{
//			String ip = args[0];
//			int port = Integer.valueOf(args[1]);
//			new TcpSendExample(ip, port);
//		}
		
		
			String str = "";
			Scanner scExit;
			do {
				scExit = new Scanner(System.in);
				System.out.println("/// Tapez 'EXIT' pour arreter le serveur ///");
				str = scExit.nextLine();
			} while (!str.toLowerCase().contains((CharSequence) "exit"));
			scExit.close();
		
			System.out.println("Arret du serveur");

			System.exit(0);
	}
}
