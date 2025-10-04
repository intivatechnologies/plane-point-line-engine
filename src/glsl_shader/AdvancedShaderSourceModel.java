package glsl_shader;

import java.util.ArrayList;

public class AdvancedShaderSourceModel extends ShaderSourceModel {
	private ArrayList<String> vertexSrc, fragmentSrc;
	private final char lineSeparator = '\n';
	
	public AdvancedShaderSourceModel(ArrayList<String> vertexSrc, ArrayList<String> fragmentSrc) {
		this.vertexSrc = vertexSrc;
		this.fragmentSrc = fragmentSrc;
	}
	
	@Override
	public String getSource(int shaderEnum) {
		ArrayList<String> src = requestsShaderType(shaderEnum)? vertexSrc : fragmentSrc;
		String convertedSrc = "";
		
		for(String line : src)
			convertedSrc += line + lineSeparator;
		
		return convertedSrc;
	}
}
