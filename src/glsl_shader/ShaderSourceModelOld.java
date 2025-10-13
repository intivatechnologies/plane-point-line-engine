package glsl_shader;

import java.util.ArrayList;
import java.util.HashMap;

class CustomLoopIterator {
	
	public CustomLoopIterator(int maxTick) {
		this.maxTick = maxTick;
	}
	
	public void reset() {
		tick = 0;
	}
	
	public boolean tick() {
		boolean doesTick = false;
		if(tick + 1 < maxTick) {
			++tick;
			doesTick = true;
		}
		
		return doesTick;
	}
	
	public int getTick() {
		return tick;
	}
	
	private int tick = 0, maxTick;
}

class KeywordInterpreter {
	
	/**
	 * Stores select keywords that are to be allowed and interpreted.
	 */
	public KeywordInterpreter(String ...keywords) {
		this.keywords = new ArrayList<>();
		for(String keyword : keywords)
			this.keywords.add(keyword);
	}
	
	public int scanLine(String line) throws IllegalArgumentException {
		int id = -1;
		for(int i = 0; i < keywords.size(); i++)
			if(line.contains(keywords.get(i))) 
				return i + 1;
		
		if(id == -1) {
			String keywordSeq = "";
			for(String keyword : keywords)
				keywordSeq += keyword + ", ";
			throw new IllegalArgumentException("Line <" + line + "> matched no keywords on keychain [" + keywordSeq + "]");
		}
		return id;
	}
	
	public int getCombinations() {
		return keywords.size();
	}
	
	public ArrayList<String> getKeywords(){
		return keywords;
	}
	
	private ArrayList<String> keywords;
}

class KeywordChain {
	
	public KeywordChain(KeywordInterpreter ...keywordLinks) {
		this.keywordLinks = keywordLinks;
	}
	
	public int scanLine(String line) {
		int combinator = 1;
		for(KeywordInterpreter ki : keywordLinks)
			combinator *= ki.scanLine(line);
		
		return combinator;
	}
	
	private KeywordInterpreter[] keywordLinks;
}

public class ShaderSourceModelOld {
	
	public ShaderSourceModelOld(ArrayList<String> source, String shaderType) {
		for(int i = 1; i < source.size(); i++) {
			String line = source.get(i);
			String[] words = line.split(" ");
			Integer variableIdentifier = KEYWORD_CHAIN.scanLine(line);
			
			ArrayList<String> variableNames = !variableMap.containsKey(variableIdentifier)?
				new ArrayList<>() : variableMap.get(variableIdentifier);
			
			variableNames.add(words[words.length - 1]);
			variableMap.put(variableIdentifier, variableNames);
		}
	}
	
	public String print() {
		String print = VERSION_NOTE;
		
		do {
			print += "\n" + getNext();
		} while(!loopIteratorsNeedAReset);
		
		return print;
	}
	
	public ArrayList<String> printList(){
		ArrayList<String> print = new ArrayList<>();
		
		do {
			print.add(getNext());
		} while(!loopIteratorsNeedAReset);
		
		return print;
	}
	
	private String getNext() {
		String line = "";
		
		if(loopIteratorsNeedAReset) {
			loopIteratorsNeedAReset = false;
			
			STORAGE_TYPE_ITER = new CustomLoopIterator(STORAGE_TYPES.getCombinations());
			STORAGE_QUALIFIER_ITER = new CustomLoopIterator(STORAGE_QUALIFIERS.getCombinations());
			LAYOUT_ITER = new CustomLoopIterator(LAYOUT_QUALIFIERS.getCombinations());
		}
		
		int sttick = STORAGE_TYPE_ITER.getTick(), sqtick = STORAGE_QUALIFIER_ITER.getTick(), ltick = LAYOUT_ITER.getTick();
		if(ltick == LAYOUT_QUALIFIERS.getKeywords().indexOf("layout")) {
			
		}
		
		
		return line;
	}
	
	private static final String VERSION_NOTE = "#version 330 core";
	
	private static final KeywordInterpreter STORAGE_TYPES = new KeywordInterpreter(
		"bool", "float", "int", "mat4", "vec2", "vec3", "vec4", "void"
	),
	STORAGE_QUALIFIERS = new KeywordInterpreter(
		"out", "in", "uniform", "attribute", "const"	
	),
	LAYOUT_QUALIFIERS = new KeywordInterpreter(
		"layout", ""
	);
	
	private static final int STORAGE_COMBINATIONS = STORAGE_TYPES.getCombinations() * STORAGE_QUALIFIERS.getCombinations(),
		ALL_COMBINATIONS = STORAGE_COMBINATIONS * LAYOUT_QUALIFIERS.getCombinations();
	
	private static final KeywordChain KEYWORD_CHAIN = new KeywordChain(
		LAYOUT_QUALIFIERS, STORAGE_QUALIFIERS, STORAGE_TYPES
	);
	
	private static CustomLoopIterator STORAGE_TYPE_ITER, STORAGE_QUALIFIER_ITER, LAYOUT_ITER;
	private static boolean loopIteratorsNeedAReset = true;
	
	/**
	 * Maps the variables by variable name. Since no distinct name can be the same but
	 * the keyword chain can, this list of various names are found by a distinct keyword chain
	 */
	private HashMap<Integer, ArrayList<String>> variableMap = new HashMap<>();
}
