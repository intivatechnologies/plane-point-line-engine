package core;

import java.util.ArrayList;

public class TextMemorySet {
	private ArrayList<String> lines;
	private ArrayList<Integer> linePositions;
	private char lineSeparator = '\n';
	private String text = "";
	
	public ArrayList<String> getLines(){
		return lines;
	}
	
	public ArrayList<Integer> getLinePositions(){
		return linePositions;
	}
	
	public String getText() {
		String newText = "";
		for(String line : lines) 
			newText += line + lineSeparator;
		
		if(!newText.equals(text))
			text = newText;
		
		return newText;
	}
}
