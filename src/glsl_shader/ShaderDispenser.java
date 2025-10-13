package glsl_shader;

import java.util.ArrayList;

public class ShaderDispenser {
	private ShaderSourceModel vertexShaderSourceModel, fragmentShaderSourceModel;
	
	public ShaderDispenser(ArrayList<String> vertexSource, ArrayList<String> fragmentSource) {
		vertexShaderSourceModel = new ShaderSourceModel(vertexSource, "vertex");
		fragmentShaderSourceModel = new ShaderSourceModel(fragmentSource, "fragment");
	}
	
	public ShaderDispenser(ShaderDispenser copy) {
		vertexShaderSourceModel = new ShaderSourceModel(copy.getVertexShaderSourceModel().printList(), "vertex");
		fragmentShaderSourceModel = new ShaderSourceModel(copy.getFragmentShaderSourceModel().printList(), "fragment");
	}
	
	public ShaderSourceModel getVertexShaderSourceModel() {
		return vertexShaderSourceModel;
	}
	
	public ShaderSourceModel getFragmentShaderSourceModel() {
		return fragmentShaderSourceModel;
	}
}
