package glsl_shader;

import java.util.Stack;
import java.util.ArrayList;

/*
class Keyword{
	
	public Keyword() {
		keyword = "";
	}
	
	public Keyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String print() {
		return keyword;
	}
	
	private String keyword;
}

class LayoutKeyword extends Keyword {
	
	public LayoutKeyword() {
		super("layout");
	}
	
	public LayoutKeyword(String mechanism) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("The layout mechanism does not support any parameters.");
	}
	
	@Override
	public String print() {
		return super.print() + " (location = )";
	}
}
*/

/*
class KeywordSet {
	
	public KeywordSet(Keyword ...supportedKeywords) {
		this.supportedKeywords = supportedKeywords;
	}
	
	public KeywordSet(String ...supportedKeywords) {
		Keyword[] converted = new Keyword[supportedKeywords.length];
		for(int i = 0; i < converted.length; i++)
			converted[i] = new Keyword(supportedKeywords[i]);
		
		this.supportedKeywords = converted;
	}
	
	public boolean scanEquals(String word) {
		boolean found = false;
		for(Keyword k : supportedKeywords)
			if(k.print().equals(word)) {
				found = true;
				break;
			}
		
		return found;
	}
	
	public Keyword scan(String line) {
		Keyword scanned = null;
		for(int i = 0; i < supportedKeywords.length; i++)
			if(line.contains(supportedKeywords[i].print())) {
				scanned = supportedKeywords[i];
				break;
			}
		
		return scanned;
	}
	
	private Keyword[] supportedKeywords;
}
*/

class KeywordSet {
	public KeywordSet(String ...supportedKeywords) {
		this.supportedKeywords = supportedKeywords;
	}
	
	public boolean scanEquals(String word) {
		boolean found = false;
		for(String k : supportedKeywords)
			if(k.equals(word)) {
				found = true;
				break;
			}
		
		return found;
	}
	
	public String scan(String line) {
		String scanned = null;
		for(int i = 0; i < supportedKeywords.length; i++)
			if(line.contains(supportedKeywords[i])) {
				scanned = supportedKeywords[i];
				break;
			}
		
		return scanned;
	}
	
	private String[] supportedKeywords;
}

class CustomVariable {
	
	public CustomVariable(String name) {
		this.name = name;
	}
	
	public void push(String k) {
		keywords.push(k);
	}
	
	public String print() {
		String print = "";
		for(String vm : keywords)
			print += vm + ' ';
		print += name;
		
		return print;
	}
	
	private Stack<String> keywords = new Stack<>();
	private String name;
}

public class ShaderSourceModel {
	
	private int layoutCounter = 0;
	
	private ArrayList<String> mainMethod = new ArrayList<>();
	private int mainMethodCounter = 0;
	
	public ShaderSourceModel(ArrayList<String> source, String shaderType) {
		for(int i = 1; i < source.size(); i++) {
			String line = source.get(i);
			
			/*
			if(line.contains("()") && !trackingMethod) {
				trackingMethod = true;
				
				ArrayList<String> method = new ArrayList<>();
				method.add(line);
				methods.add(method);
				
				break;
			} else if(trackingMethod) {
				ArrayList<String> method = methods.get(methods.size() - 1);
				method.add(line);
				if(line.contains("}"))
					trackingMethod = false;
				methods.set(methods.size() - 1, method);
				
				break;
			}
			*/
			if(line.contains("()")) {
				mainMethodCounter = i;
				break;
			}
			
			String[] words = line.split(" ");
			CustomVariable cv = new CustomVariable(words[words.length - 1]);
			
			if(LAYOUT_CUSTOM.scanEquals(words[0]))
				cv.push("layout (location = " + layoutCounter++ + ")");

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
			variableChains.add(cv);
		}
		
		while(!source.get(mainMethodCounter).contains("}"))
			mainMethod.add(source.get(mainMethodCounter++));
		mainMethod.add(source.get(mainMethodCounter));
	}
	
	public String print() {
		ArrayList<String> printList = getPrintSchema();
		
		String print = "";
		for(String printLine : printList)
			print += "\n" + printLine;
		
		return print;
	}
	
	public ArrayList<String> printList() {
		return getPrintSchema();
	}
	
	private static final String VERSION_NOTE = "#version 330 core";
	
	private static final KeywordSet LAYOUT_CUSTOM = new KeywordSet("layout"),
		BEHAVIOUR_CUSTOM = new KeywordSet("attribute", "const", "in", "out", "uniform"),
		TYPE_CUSTOM = new KeywordSet("bool", "float", "int", "mat4", "vec2", "vec3", "vec4", "void");
	
	private ArrayList<CustomVariable> variableChains = new ArrayList<>();
	
	private ArrayList<String> getPrintSchema(){
		ArrayList<String> print = new ArrayList<>();
		print.add(VERSION_NOTE);
		
		for(CustomVariable cv : variableChains)
			print.add(cv.print());
		
		for(String line : mainMethod)
			print.add(line);
		
		return print;
	}
}
