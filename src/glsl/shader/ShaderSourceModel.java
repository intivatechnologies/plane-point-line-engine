package glsl.shader;

import java.util.ArrayList;

import glsl.syntax.GLSLLayoutVariable;
import glsl.syntax.GLSLVariable;

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
	
	private String versionNote;
	
	/*
	private static final KeywordSet LAYOUT_CUSTOM = new KeywordSet("layout"),
		BEHAVIOUR_CUSTOM = new KeywordSet("attribute", "in", "out", "uniform"),
		TYPE_CUSTOM = new KeywordSet("bool", "float", "int", "mat4", "vec2", "vec3", "vec4", "void");
		*/
	
	private int layoutCounter = 0;
	
	/** Temporary lines assigned to the main method */
	private ArrayList<String> mainMethod = new ArrayList<>();
	private int mainMethodCounter = 0;
	
	private ArrayList<GLSLVariable> variableChains = new ArrayList<>();
	
	private String[] getPrintSchema(){
		ArrayList<String> print = new ArrayList<>();
		
		interface DecodeEntry {
			String compute(String line);
		}
		DecodeEntry decodeItSpecially = (line) -> line + "\n";
		DecodeEntry decodeItNormally = (line) -> line + ";\n";
		
		print.add(decodeItSpecially.compute(versionNote));
		
		for(GLSLVariable cv : variableChains)
			print.add(decodeItNormally.compute(cv.print()));
		
		for(String line : mainMethod)
			print.add(line);
		
		return print.toArray(new String[print.size()]);
	}
	
	public ShaderSourceModel(String[] source) throws Exception {
		//capture the left side of the line break
		if(source[0].contains("\n"))
			versionNote = source[0].split("\n")[0];
		else
			versionNote = source[0];
		
		for(int i = 1; i < source.length; i++) {
			//we\'ll get to this
			if(source[i].contains("()")) {
				mainMethodCounter = i;
				i = source.length;
				break;
			}
			
			//capture the left side of the line break and semicolon
			String line = source[i];
			if(line.contains("\n"))
				line = line.split("\n")[0];
			if(line.contains(";"))
				line = line.split(";")[0];
			
			//get all the text sequences (non-blank)
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
			
			//TODO error is likely somewhere here
			//remove any scripting that\'s unnecessary for parsing
			int j = 0;
			while(j < words.size()) {
				if(j < words.size()) {
					//remove layout location (but keep layout word)
					if(words.get(j).equals("layout")) {
						if(words.size() > j + 1) {
							String layoutSequenceIteration = words.remove(j + 1);
							j = !layoutSequenceIteration.contains(")") ?
									0 : 1;
							continue;
						} else
							throw new Exception("An illegal syntax entry was found on a layout declaration inside the GLSL.");
					}
					
					//we\'ll get to this
					if(words.get(j).contains("()")) {
						mainMethodCounter = i;
						i = source.length;
						break;
					}
					
					++j;
				} else
					break;
			}
			
			//now that we have the words on this line, store the variable
			GLSLVariable gv;
			int startJ;
			
			if(words.get(0).equals("layout")) {
				gv = new GLSLLayoutVariable(layoutCounter++);
				startJ = 1;
			} else {
				gv = new GLSLVariable();
				startJ = 0;
			}
			for(; startJ < words.size() - 1; startJ++)
				gv.define_a_modifier(words.get(startJ));
			
			gv.setName(words.get(words.size() - 1));
			variableChains.add(gv);
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
