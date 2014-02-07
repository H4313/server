package com.h4313.deephouse.server.main;

import java.util.List;
import java.util.Scanner;

import com.h4313.deephouse.actuator.ActuatorType;
import com.h4313.deephouse.housemodel.House;
import com.h4313.deephouse.housemodel.Room;
import com.h4313.deephouse.sensor.SensorType;
import com.h4313.deephouse.server.controller.Controller;
import com.h4313.deephouse.util.DeepHouseCalendar;

public class Main {
	public static void main(String[] args) throws Exception
	{
		// Initialisation horloge
		DeepHouseCalendar.getInstance().init();
		
		// Initialisation de la maison // TODO : RETIRER POUR LA PRODUCTION
		try
		{
			List<Room> rooms = House.getInstance().getRooms();
			int id = 0;
			for(Room room : rooms)
			{
				room.addSensor(String.valueOf(id++), SensorType.TEMPERATURE);
				room.addSensor(String.valueOf(id++), SensorType.WINDOW);
				room.addSensor(String.valueOf(id++), SensorType.LIGHT);
				room.addSensor(String.valueOf(id++), SensorType.PRESENCE);
				
				room.addActuator(String.valueOf(id++), ActuatorType.LIGHTCONTROL);
				room.addActuator(String.valueOf(id++), ActuatorType.AIRCONDITION);
				room.addActuator(String.valueOf(id++), ActuatorType.RADIATOR);
				room.addActuator(String.valueOf(id++), ActuatorType.WINDOWCLOSER_1);
			}
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		// Initialisation du reseau
		Controller.getInstance().initSensorsListener(Integer.valueOf(args[0]).intValue());
		Controller.getInstance().initActuatorsSender(args[1], Integer.valueOf(args[2]).intValue());
		Controller.getInstance().start();
		
		
		// En attente de l'arret de la machine
		String str = "";
		Scanner scExit;
		do {
			scExit = new Scanner(System.in);
			System.out.println("/// Tapez 'EXIT' pour arreter la machine ///");
			str = scExit.nextLine();
		} while (!str.toLowerCase().contains((CharSequence) "exit"));
		scExit.close();
	
		Controller.getInstance().stopController();
		
		
		System.out.println("Arret du serveur");

		System.exit(0);
	}
}
