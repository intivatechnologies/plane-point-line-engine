package glsl_shader;

import java.util.ArrayList;
import java.util.HashMap;

public class ShaderSourceModelOlder {

	public ShaderSourceModelOlder(ArrayList<String> source, String shaderType) throws IllegalArgumentException {
		for(int i = 1; i < source.size(); i++) {
			String line = source.get(i);
			String[] words = line.split(" ");
			
			/*
			String variableName = words[words.length - 1].substring(0, words[words.length - 1].length() - 1);
			STORAGE_TYPE storageTypeEnum = STORAGE_TYPE.ILLEGAL;
			
			String storageType = words[words.length - 2];
			for(int j = 0; j < STORAGE_TYPE_STRS.length; j++)
				if(storageType.equals(STORAGE_TYPE_STRS[j])) {
					storageTypeEnum = STORAGE_TYPE_ENUMS[j];
					break;
				}
			if(storageTypeEnum == STORAGE_TYPE.ILLEGAL)
				throw new IllegalArgumentException("ShaderSourceModel detected an error in "
						+ "the source code of a " + shaderType + " shader.");
						*/
			STORAGE_TYPE storageTypeEnum = (STORAGE_TYPE) loadEnum(words[words.length - 2], STORAGE_TYPE_STRS, STORAGE_TYPE_ENUMS,
				STORAGE_TYPE.ILLEGAL, shaderType);
			
			ArrayList<String> storageTypeVariables = !variableDirectory.containsKey(storageTypeEnum)?
				new ArrayList<>() : variableDirectory.get(storageTypeEnum);
			
			storageTypeVariables.add(words[words.length - 1]);
			variableDirectory.put(storageTypeEnum, storageTypeVariables);
			
			/*
			String variableQualifier = words[words.length - 3];
			VARIABLE_QUALIFIER variableQualifierEnum = VARIABLE_QUALIFIER.ILLEGAL;
			for(int j = 0; j < VARIABLE_QUALIFIER_STRS.length; j++)
				if(variableQualifier.equals(VARIABLE_QUALIFIER_STRS[j])) {
					variableQualifierEnum = VARIABLE_QUALIFIER_ENUMS[j];
					break;
				}
			if(variableQualifierEnum == VARIABLE_QUALIFIER.ILLEGAL)
				throw new IllegalArgumentException("ShaderSourceModel detected an error in "
						+ "the source code of a " + shaderType + " shader.");
						*/
			
			VARIABLE_QUALIFIER variableQualifierEnum = (VARIABLE_QUALIFIER) loadEnum(words[words.length - 3], VARIABLE_QUALIFIER_STRS,
				VARIABLE_QUALIFIER_ENUMS, VARIABLE_QUALIFIER.ILLEGAL, shaderType);
			
			ArrayList<STORAGE_TYPE> variableQualifiers = !variableQualifierDirectory.containsKey(variableQualifierEnum)?
				new ArrayList<>() : variableQualifierDirectory.get(variableQualifierEnum);
			
			variableQualifiers.add(storageTypeEnum);
			variableQualifierDirectory.put(variableQualifierEnum, variableQualifiers);
			
			if(line.contains("layout"))
				layoutVariables.add(variableQualifierEnum);
			else
				nonLayoutVariables.add(variableQualifierEnum);
		}
	}
	
	public String print() {
		String source = VERSION_NOTE;
		
		for(int i = 0; i < layoutVariables.size(); i++) {
			/*
			source += "\nlayout (location = " + i + ") " + getVariableQualifierStr(layoutVariables.get(i))
				+ ' ' + getStorageTypeStr(variableQualifierDirectory.get(layoutVariables.get(i)));
				*/
		}
		
		return source;
	}
	
	public ArrayList<String> printList(){
		ArrayList<String> source = new ArrayList<>();
		
		source.add(VERSION_NOTE);
		
		for(int i = 0; i < layoutVariables.size(); i++) {
			String line = "layout (location = " + i + ") ";
			
		}
		
		return source;
	}
	
	private static final String VERSION_NOTE = "#version 330 core";
	
	private enum STORAGE_TYPE {
		ILLEGAL, VOID, FLOAT, INT, BOOL, VEC2, VEC3, VEC4, MAT4
	}
	private static final STORAGE_TYPE[] STORAGE_TYPE_ENUMS = new STORAGE_TYPE[] {
		STORAGE_TYPE.VOID,
		STORAGE_TYPE.FLOAT,
		STORAGE_TYPE.INT,
		STORAGE_TYPE.BOOL,
		STORAGE_TYPE.VEC2,
		STORAGE_TYPE.VEC3,
		STORAGE_TYPE.VEC4,
		STORAGE_TYPE.MAT4
	};
	private static final String[] STORAGE_TYPE_STRS = new String[] { "void", "float", "int", "bool", "vec2", "vec3", "vec4", "mat4" };
	
	private HashMap<STORAGE_TYPE, ArrayList<String>> variableDirectory = new HashMap<>();
	
	private enum VARIABLE_QUALIFIER {
		ILLEGAL, OUT, IN, UNIFORM, ATTRIBUTE, CONST
	}
	private static final VARIABLE_QUALIFIER[] VARIABLE_QUALIFIER_ENUMS = new VARIABLE_QUALIFIER[] {
		VARIABLE_QUALIFIER.OUT,
		VARIABLE_QUALIFIER.IN,
		VARIABLE_QUALIFIER.UNIFORM,
		VARIABLE_QUALIFIER.ATTRIBUTE,
		VARIABLE_QUALIFIER.CONST
	};
	private static final String[] VARIABLE_QUALIFIER_STRS = new String[] { "out", "in", "uniform", "attribute", "const" };
	
	private HashMap<VARIABLE_QUALIFIER, ArrayList<STORAGE_TYPE>> variableQualifierDirectory = new HashMap<>();
	
	private ArrayList<VARIABLE_QUALIFIER> layoutVariables = new ArrayList<>();
	private ArrayList<VARIABLE_QUALIFIER> nonLayoutVariables = new ArrayList<>();
	
	private Object loadEnum(String word, String[] enumStrs, Object[] enums, Object illegalEnum, String shaderType)
	throws IllegalArgumentException {
		Object returnEnum = illegalEnum;
		for(int i = 0; i < enumStrs.length; i++)
			if(word.equals(enumStrs[i])) {
				returnEnum = enums[i];
				break;
			}
		
		if(returnEnum == illegalEnum)
			throw new IllegalArgumentException("ShaderSourceModel detected an error in "
					+ "the source code of a " + shaderType + " shader.");
		return returnEnum;
	}
	
	private String getVariableQualifierStr(VARIABLE_QUALIFIER vqEnum) {
		String vqStr = null;
		for(int i = 0; i < VARIABLE_QUALIFIER_ENUMS.length; i++)
			if(vqEnum == VARIABLE_QUALIFIER_ENUMS[i]) {
				vqStr = VARIABLE_QUALIFIER_STRS[i];
				break;
			}
		
		return vqStr;
	}
	
	private String getStorageTypeStr(STORAGE_TYPE stEnum) {
		String stStr = null;
		for(int i = 0; i < STORAGE_TYPE_ENUMS.length; i++)
			if(stEnum == STORAGE_TYPE_ENUMS[i]) {
				stStr = STORAGE_TYPE_STRS[i];
				break;
			}
		
		return stStr;
	}
}
