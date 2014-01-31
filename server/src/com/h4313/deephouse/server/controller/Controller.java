package com.h4313.deephouse.server.controller;

import java.util.ArrayList;

import com.h4313.deephouse.exceptions.DeepHouseException;
import com.h4313.deephouse.frame.Frame;
import com.h4313.deephouse.housemodel.House;

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
		
	}
	
	private void updateModel() {
		//TODO tout ce qui est IA
	}
	
}
