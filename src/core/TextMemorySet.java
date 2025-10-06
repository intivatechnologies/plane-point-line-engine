package core;

import java.util.ArrayList;
import java.util.Iterator;

public class TextMemorySet implements Iterable<String> {
	
	private String text = "";
	private ArrayList<Integer> lineBreaks = new ArrayList<>();
	
	/*
	public TextMemorySet(String text) {
		this.text = text;
		
		// End of File
		boolean EOF = false;
		int lineStart = 0;
		do {
			// End of Line, Start of Next Line
			int EOL = text.indexOf('\n', lineStart), SONL = EOL + 1;
			if(SONL < text.length()) {
				lineStart = SONL;
				lineBreaks.add(EOL);
			} else
				EOF = true;
		} while (!EOF);
	}
	*/
	
	public TextMemorySet(ArrayList<String> text) {
		int countSoFar = 0;
		for(String line : text) {
			countSoFar += line.length();
			lineBreaks.add(countSoFar);
			this.text += line + '\n';
		}
	}
	
	public TextMemorySet(TextMemorySet copy) {
		this.text = copy.getText();
		this.lineBreaks = new ArrayList<>(copy.getLineBreaks());
	}
	
	public String getText() {
		return text;
	}
	
	public ArrayList<Integer> getLineBreaks(){
		return lineBreaks;
	}
	
	@Override
	public Iterator<String> iterator() {
		return new TMSIterator<String>(this);
	}
}

@SuppressWarnings("hiding")
class TMSIterator<String> implements Iterator<String> {
	
	private final java.lang.String text;
	private int index = 0;
	
	public TMSIterator(TextMemorySet tms){
		text = tms.getText();
	}

	@Override
	public boolean hasNext() {
		return text.indexOf('\n', index) != -1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String next() {
		int breakIndex = text.indexOf('\n', index);
		if(breakIndex != -1) {
			String line = (String) text.substring(index, breakIndex);
			index = breakIndex + 1;
			return line;
		} else
			return (String) "\n";
	}
}
