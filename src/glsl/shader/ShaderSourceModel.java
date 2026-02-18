package glsl.shader;

import java.util.ArrayList;
import glsl.syntax.*;

/**
 * A set of distinct or multiple-choice keywords that get recorded in a line
 */
class KeywordSet {
	
	private String[] supportedKeywords;
	
	public KeywordSet(String ...supportedKeywords) {
		this.supportedKeywords = supportedKeywords;
	}
	
	/**
	 * Detects if the parameter is one of the supported keywords
	 * @param word
	 * @return word in supportedKeywords
	 */
	public boolean scanEquals(String word) {
		boolean found = false;
		for(String k : supportedKeywords)
			if(k.equals(word)) {
				found = true;
				break;
			}
		
		return found;
	}
	
	/**
	 * Checks the first instance of a supported keyword in the parameter
	 * @param line
	 * @return 1 / supportedKeywords (contained in line)
	 */
	public String scan(String line) {
		String scanned = null;
		for(int i = 0; i < supportedKeywords.length; i++)
			if(line.contains(supportedKeywords[i])) {
				scanned = supportedKeywords[i];
				break;
			}
		
		return scanned;
	}
}

public class ShaderSourceModel {
	
	private static final String VERSION_NOTE = "#version 330 core";
	
	private static final KeywordSet LAYOUT_CUSTOM = new KeywordSet("layout"),
		BEHAVIOUR_CUSTOM = new KeywordSet("attribute", "in", "out", "uniform"),
		TYPE_CUSTOM = new KeywordSet("bool", "float", "int", "mat4", "vec2", "vec3", "vec4", "void");
	
	private int layoutCounter = 0;
	
	/** Temporary lines assigned to the main method */
	private ArrayList<String> mainMethod = new ArrayList<>();
	private int mainMethodCounter = 0;
	
	private ArrayList<GLSLVariable> variableChains = new ArrayList<>();
	
	private String[] getPrintSchema(){
		ArrayList<String> print = new ArrayList<>();
		print.add(VERSION_NOTE);
		
		for(GLSLVariable cv : variableChains)
			print.add(cv.print());
		
		for(String line : mainMethod)
			print.add(line);
		
		return print.toArray(new String[print.size()]);
	}
	
	public ShaderSourceModel(String[] source, String shaderType) {
		for(int i = 1; i < source.length; i++) {
			String line = source[i];

			if(line.contains("()")) {
				mainMethodCounter = i;
				break;
			}
			
			ArrayList<String> words = new ArrayList<>();
			String wordSeparator = "";
			for(int j = 0; j < line.length(); j++) {
				char c = line.charAt(j);
				if(c != ' ' && c != '\t' && c != '\n')
					wordSeparator += c;
				else {
					words.add(wordSeparator);
					wordSeparator = "";
				}
			}
			
			/*
			GLSLVariable cv = LAYOUT_CUSTOM.scanEquals(words.get(0)) ?
					new GLSLLayoutVariable(layoutCounter) : new GLSLVariable();

			String behaviourIndex = BEHAVIOUR_CUSTOM.scan(line);
			if(behaviourIndex == null)
				throw new IllegalArgumentException("An unsupported syntax occurrence was found in the scanning of"
					+ " the " + shaderType + " shader.");
			
			String typeIndex = TYPE_CUSTOM.scan(line);
			if(typeIndex == null)
				throw new IllegalArgumentException("An unsupported syntax occurrence was found in the scanning of"
						+ " the " + shaderType + " shader.");
			
			cv.push(behaviourIndex);
			cv.push(typeIndex);
			cv.push(words.get(words.size() - 1));
			
			variableChains.add(cv);
			*/
		}
		
		while(!source[mainMethodCounter].contains("}"))
			mainMethod.add(source[mainMethodCounter++]);
		mainMethod.add(source[mainMethodCounter]);
	}
	
	public String print() {
		String[] printList = getPrintSchema();
		
		StringBuilder sb = new StringBuilder("");
		for(String printLine : printList)
			sb.append("\n").append(printLine);
		
		return sb.toString();
	}
	
	public String[] printList() {
		return getPrintSchema();
	}
}
