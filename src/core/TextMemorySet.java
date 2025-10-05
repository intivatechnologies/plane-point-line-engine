package core;

import java.util.ArrayList;
import java.util.Iterator;

public class TextMemorySet implements Iterable<String> {
	
	private String text = "";
	private ArrayList<Integer> lineBreaks = new ArrayList<>();
	
	public TextMemorySet(String text) {
		installLines(text, '\n');
	}
	
	public TextMemorySet(String text, final char LINE_SEPARATOR) {
		installLines(text, LINE_SEPARATOR);
	}
	
	public TextMemorySet(ArrayList<String> text) {
		installLines(text, '\n');
	}
	
	public TextMemorySet(ArrayList<String> text, final char LINE_SEPARATOR) {
		installLines(text, LINE_SEPARATOR);
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
	
	/*
	 * Installs lines based off of String text.
	 * Assumes array-list linePositions.size() == 0.
	 */
	private void installLines(String text, final char LINE_SEPARATOR) {
		this.text = text;
		
		// End of File
		boolean EOF = false;
		int lineStart = 0;
		do {
			// End of Line, Start of Next Line
			int EOL = text.indexOf(LINE_SEPARATOR, lineStart), SONL = EOL + 1;
			if(SONL < text.length()) {
				lineStart = SONL;
				lineBreaks.add(EOL);
			} else
				EOF = true;
		} while (!EOF);
	}
	
	/*
	 * Installs lines based off of ArrayList<String> lines.
	 * Assumes array-list linePositions.size() == 0.
	 */
	private void installLines(ArrayList<String> lines, final char LINE_SEPARATOR) {
		int countSoFar = 0;
		for(String line : lines) {
			countSoFar += line.length();
			lineBreaks.add(countSoFar);
			text += line + LINE_SEPARATOR;
		}
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
