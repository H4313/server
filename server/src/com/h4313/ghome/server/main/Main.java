package com.h4313.ghome.server.main;

import java.util.Scanner;

import com.h4313.ghome.server.util.Tool;

public class Main
{
	public static void main(String[] args)
	{
		if(args.length == 0)
		{
			try
			{
				Scanner sc;
				String str;
				do
				{
					sc = new Scanner(System.in);
				    System.out.println("Quelle est l'adresse de la Machine Actionneur ?");
				    str = sc.nextLine();
				} while(!Tool.isValidIp(str));
				sc.close();
				
				do
				{
					sc = new Scanner(System.in);
				    System.out.println("/// Tapez 'EXIT' pour arreter le serveur ///");
				    str = sc.nextLine();
				} while(!str.toLowerCase().contains((CharSequence)"exit"));
				sc.close();
			}
			catch(Exception e)
			{
				System.out.println("Erreur de l'application : " + e.getMessage());
			}
			
			System.out.println("Arret du serveur");
			
			System.exit(0);
		}
	}
}
