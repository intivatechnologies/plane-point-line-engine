package core;

import java.util.ArrayList;

public class ProgramDecoder {
	private final ArrayList<String[]> ALLOWED_KEYWORDS;
	private ArrayList<String> variableNames;
	
	public ProgramDecoder(final ArrayList<String[]> ALLOWED_KEYWORDS) {
		this.ALLOWED_KEYWORDS = ALLOWED_KEYWORDS;
		
	}
}
