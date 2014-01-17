package com.h4313.deephouse.server.frame;

public class Frame {

	private String id;
	private String type;
	private byte[] data;
	private String status;
	private String checkSum;

	/**
	 * Constructor for a Frame: parse the given string into a Frame object
	 * 
	 * @param the
	 *            frame read, represented as a string
	 * @throws
	 * */
	public Frame(String frameStr) {

	}

	/********** getters *************/

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public byte[] getData() {
		return data;
	}

}
