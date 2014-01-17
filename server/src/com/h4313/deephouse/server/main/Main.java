package com.h4313.deephouse.server.main;

import java.util.Scanner;

import com.h4313.deephouse.util.Tool;


public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			try {
				String str;

				Scanner scIp;
				do {
					scIp = new Scanner(System.in);
					System.out
							.println("Quelle est l'adresse de la Machine Actionneur ?");
					str = scIp.nextLine();
				} while (!Tool.isValidIp(str));
				scIp.close();

				String ipMActuator = str;

				Scanner scPort;
				do {
					scIp = new Scanner(System.in);
					System.out
							.println("Sur quel port se connecter ?? la Machine Actionneur ?");
					str = scIp.nextLine();
				} while (!Tool.isValidIp(str));
				scIp.close();

				Scanner scExit;
				do {
					scExit = new Scanner(System.in);
					System.out
							.println("/// Tapez 'EXIT' pour arreter le serveur ///");
					str = scExit.nextLine();
				} while (!str.toLowerCase().contains((CharSequence) "exit"));
				scExit.close();
			} catch (Exception e) {
				// System.out.println("Erreur de l'application : " +
				// e.getMessage());
			}

			System.out.println("Arret du serveur");

			System.exit(0);
		}
	}
}
