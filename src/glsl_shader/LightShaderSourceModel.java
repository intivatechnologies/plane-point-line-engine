package glsl_shader;

public class LightShaderSourceModel extends ShaderSourceModel {
	private String vertexSrc, fragmentSrc;
	
	public LightShaderSourceModel(String vertexSrc, String fragmentSrc) {
		this.vertexSrc = vertexSrc;
		this.fragmentSrc = fragmentSrc;
	}
	
	@Override
	public String getSource(int shaderEnum) {
		return requestsShaderType(shaderEnum)? vertexSrc : fragmentSrc;
	}
}
