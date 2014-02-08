package com.h4313.deephouse.server.ai;

import java.util.ArrayList;

import com.h4313.deephouse.actuator.Actuator;
import com.h4313.deephouse.actuator.ActuatorType;
import com.h4313.deephouse.housemodel.House;
import com.h4313.deephouse.housemodel.Room;
import com.h4313.deephouse.sensor.Sensor;
import com.h4313.deephouse.sensor.SensorType;
import com.h4313.deephouse.util.DeepHouseCalendar;

public abstract class TemperatureAI {
	
	//Previous time used in the AI in each room in seconds
	public static ArrayList<Long> previousTime;
	
	public static ArrayList<Double> previousTemperature;
	
	public static ArrayList<Double> integralFactor;
	
	public static ArrayList<Double> proportionalFactor;
	
	public static ArrayList<Double> integral;
	
	public static void initTemperatureAI() {
		previousTime = new ArrayList<Long>();
		previousTemperature = new ArrayList<Double>();
		integralFactor = new ArrayList<Double>();
		proportionalFactor = new ArrayList<Double>();
		integral = new ArrayList<Double>();
		int nRooms = House.getInstance().getRooms().size();
		long initTime = DeepHouseCalendar.getInstance().getCalendar().getTimeInMillis()/1000;
		for(int i = 0 ; i < nRooms ; i++) {
			previousTime.add(initTime);
			previousTemperature.add(20.0);
			integralFactor.add(1.0);
			proportionalFactor.add(1.0);
			integral.add(0.0);
		}
	}
	
	public static void run() {
		for(int i = 0 ; i < House.getInstance().getRooms().size() ; i++) {
			evaluateDesiredValue(i);
			piControl(i);
		}
	}
	
	
	public static void evaluateDesiredValue(int n) {
		Room r = House.getInstance().getRooms().get(n);
		//TODO chercher la température optimale selon l'heure et la pièce
	}
	
	public static void piControl(int n) {
		Room r = House.getInstance().getRooms().get(n);
		Actuator<Object> heater = r.getActuatorByType(ActuatorType.RADIATOR).get(0);
		Sensor<Object> temp = r.getSensorByType(SensorType.TEMPERATURE).get(0);		
		
		Double error = (Double) heater.getDesiredValue() - (Double) temp.getLastValue();
		Long deltaT = DeepHouseCalendar.getInstance().getCalendar().getTimeInMillis()/1000 - previousTime.get(n);
		
		integral.set(n, integral.get(n) + error*deltaT.doubleValue());
		Double output = proportionalFactor.get(n)*error + integralFactor.get(n)*integral.get(n);
		heater.setLastValue(output);
	}
}
