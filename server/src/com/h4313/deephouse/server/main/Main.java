package com.h4313.deephouse.server.main;

import java.util.Scanner;

import com.h4313.deephouse.server.network.CallBack;
import com.h4313.deephouse.server.network.TcpExample;
import com.h4313.deephouse.server.network.TcpSender;
import com.h4313.deephouse.util.Tool;

public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			try {
				
				String str = "";
				Scanner scIp;
				do {
					scIp = new Scanner(System.in);
					System.out
							.println("Quelle est l'adresse du proxy ?");
					str = scIp.nextLine();
				} while (!Tool.isValidIp(str));
				scIp.close();

				String ip = str;

				str = "";
				Scanner scPort;
				do {
					scPort = new Scanner(System.in);
					System.out.println("Sur quel port est connecté le proxy ?");
					str = scPort.nextLine();
				} while (!Tool.isValidPort(str));
				scPort.close();
				
				int port = Integer.valueOf(str).intValue();
				
				
				/************************/
				new TcpExample(ip, port);
				
				str = "";
				Scanner scExit;
				do {
					scExit = new Scanner(System.in);
					System.out
							.println("/// Tapez 'EXIT' pour arreter le serveur ///");
					str = scExit.nextLine();
				} while (!str.toLowerCase().contains((CharSequence) "exit"));
				scExit.close();
				
			} catch (Exception e) {
				 System.out.println("Erreur de l'application : " + e.getMessage());
			}

			System.out.println("Arret du serveur");

			System.exit(0);
		}
	}
}
