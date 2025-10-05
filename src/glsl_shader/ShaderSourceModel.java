package glsl_shader;

import core.TextMemorySet;
import java.util.ArrayList;

public class ShaderSourceModel {
	TextMemorySet vertexSrc, fragmentSrc;
	
	/*
	 * Not currently in use.
	 */
	public ShaderSourceModel(String vertexSrc, String fragmentSrc) {
		this.vertexSrc = new TextMemorySet(vertexSrc);
		this.fragmentSrc = new TextMemorySet(fragmentSrc);
	}
	
	public ShaderSourceModel(ArrayList<String> vertexSrc, ArrayList<String> fragmentSrc) {
		this.vertexSrc = new TextMemorySet(vertexSrc);
		this.fragmentSrc = new TextMemorySet(fragmentSrc);
	}
	
	public ShaderSourceModel(ShaderSourceModel copy) {
		this.vertexSrc = new TextMemorySet(copy.getVertexMemorySet());
		this.fragmentSrc = new TextMemorySet(copy.getFragmentMemorySet());
	}
	
	public TextMemorySet getVertexMemorySet() {
		return vertexSrc;
	}
	
	public TextMemorySet getFragmentMemorySet() {
		return fragmentSrc;
	}
}
