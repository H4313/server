package com.h4313.deephouse.server.controller;

import java.util.ArrayList;

import com.h4313.deephouse.exceptions.DeepHouseException;
import com.h4313.deephouse.frame.Frame;
import com.h4313.deephouse.housemodel.House;

public class Controller {

	protected House house;
	
	protected SensorsListener listener;
	
	public Controller(int inputPort) {
		this.listener = new SensorsListener(inputPort);
	}
	
	
	public void updateSensors() throws DeepHouseException {
		ArrayList<String> messages = listener.clearBuffer();
		for(String message : messages) {
			Frame frame = new Frame(message);
			house.updateSensor(frame);
		}
	}
	
}
