package glsl_shader;

import core.TextMemorySet;
import java.util.ArrayList;
import java.util.HashMap;

class ShaderTypeModel extends TextMemorySet {
	
	private final static String[] ALLOWED_KEYWORDS = new String[] { "layout", "uniform", "out", "in" };
	private static ArrayList<String>[] keywordEntries = new ArrayList[] { null, null, null, null };
	private HashMap<String, ArrayList<String>> variables = new HashMap<>();
	
	public ShaderTypeModel(ArrayList<String> source) {
		super(source);
		
		for(String line : source) {
			if(line.indexOf(' ') != -1) {
				String keyword = line.substring(0, line.indexOf(' '));
				for(String allowedKeyword : ALLOWED_KEYWORDS)
					if(keyword.equals(allowedKeyword)) {
						addAfterNullCheck(keyword, line.substring(keyword.length() + 1, line.length() - 1));
						break;
					}
			}
		}
	}
	
	public ShaderTypeModel(TextMemorySet copy) {
		super(copy);
	}
	
	public void addUniformVariable(String content) {
		final String LITERAL = "uniform";
		String line = LITERAL + ' ' + content;
		addAfterNullCheck(LITERAL, content);
		
		for (HashMap.Entry<String, ArrayList<String>> entry : variables.entrySet()) {
			//System.out.println("keyword: " + entry.getKey() + ", content: " + entry.getValue().get(0));
			for(String individualKeywordContent : entry.getValue())
				System.out.println("keyword: " + entry.getKey() + ", content: " + individualKeywordContent);
		}
	}
	
	private void addAfterNullCheck(String keyword, String content) {
		int keywordIndex = checkKeywordIndex(keyword);
		if(variables.get(keyword) == null) {
			variables.put(keyword, new ArrayList<>());
			keywordEntries[keywordIndex] = new ArrayList<>();
		}
		
		variables.get(keyword).add(content);
		keywordEntries[keywordIndex].add(keyword + ' ' + content);
	}
	
	private int checkKeywordIndex(String keyword) {
		int i = -1, j = -1;
		while(++i < ALLOWED_KEYWORDS.length) {
			if(ALLOWED_KEYWORDS[i].equals(keyword)) {
				j = i;
				break;
			}
		}
		
		return j;
	}
}

public class ShaderSourceModel {
	ShaderTypeModel vertexSrc, fragmentSrc;
	
	public ShaderSourceModel(ArrayList<String> vertexSrc, ArrayList<String> fragmentSrc) {
		this.vertexSrc = new ShaderTypeModel(vertexSrc);
		this.fragmentSrc = new ShaderTypeModel(fragmentSrc);
	}
	
	public ShaderSourceModel(ShaderSourceModel copy) {
		this.vertexSrc = new ShaderTypeModel(copy.getShaderVertexModel());
		this.fragmentSrc = new ShaderTypeModel(copy.getShaderFragmentModel());
	}
	
	public ShaderTypeModel getShaderVertexModel() {
		return vertexSrc;
	}
	
	public ShaderTypeModel getShaderFragmentModel() {
		return fragmentSrc;
	}
}
