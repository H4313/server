package com.h4313.deephouse.server.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import com.h4313.deephouse.actuator.Actuator;
import com.h4313.deephouse.actuator.ActuatorSet;
import com.h4313.deephouse.exceptions.DeepHouseException;
import com.h4313.deephouse.frame.Frame;
import com.h4313.deephouse.housemodel.House;
import com.h4313.deephouse.housemodel.Room;

public class Controller extends Thread {

	protected House house;
	
	protected SensorsListener listener;
	
	protected ActuatorsSender sender;
	
	public Controller(int inputPort, String outputIp, int outputPort) {
		this.listener = new SensorsListener(inputPort);
		this.sender = new ActuatorsSender(outputIp,outputPort);
	}
	
	public void run() {
		try {
			updateSensors();
			updateModel();
			sendActuators();
		} catch(Exception e) {
			System.out.println("Controller Error");
		}
	}
	
	private void updateSensors() throws DeepHouseException {
		ArrayList<String> messages = listener.clearBuffer();
		for(String message : messages) {
			Frame frame = new Frame(message);
			house.updateSensor(frame);
		}
	}
	
	private void sendActuators() {
		for(Room r : house.getRooms())
		{
			ActuatorSet actuators = r.getActuators();
	        Set<Map.Entry<String, Actuator>> set = actuators.entrySet();
	        for(Map.Entry<String,Actuator> entry : set)
	        {
	        	if(entry.getValue().getModified()) {
	        		sender.submitMessage(entry.getValue().getFrame());
	        		entry.getValue().setModified(false);
	        	}
	        }
		}
	}
	
	private void updateModel() {
		//TODO tout ce qui est IA
	}
	
}
