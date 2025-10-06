package glsl_shader;

public class ShaderFactory {
	private static ShaderSourceModel sourceCache;
	
	private static void begin(ShaderConvention convention) {
		sourceCache = new ShaderSourceModel(ConventionalShaderLinker.getInstance().getShaderSourceModel(convention));
	}
	
	public static void beginFillShader() {
		begin(ShaderConvention.FILL);
	}
	
	public static void addUniformVariableVertex(String content) {
		sourceCache.getShaderVertexModel().addUniformVariable(content);
	}
	
	public static ShaderProgram build() {
		ShaderProgram s = new ShaderProgram();
		s.run(sourceCache.getShaderVertexModel().getText(), sourceCache.getShaderFragmentModel().getText());
		return s;
	}
	
	private static String generateLine(String input, String prependInput) {
		return prependInput + input + "\n";
	}
}
