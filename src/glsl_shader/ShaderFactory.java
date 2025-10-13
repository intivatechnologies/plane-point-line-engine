package glsl_shader;

public class ShaderFactory {
	private static ShaderDispenser sourceDispenser;
	
	public static void beginFillShader() {
		begin(ShaderConvention.FILL);
	}
	
	public static void addUniformVariableVertex(String content) {
		//System.out.println("Vertex source");
		//sourceDispenser.getVertexShaderSourceModel().addVariable("uniform", content);
	}
	
	public static ShaderProgram build() {
		System.out.println(sourceDispenser.getVertexShaderSourceModel().print());
		System.out.println(sourceDispenser.getFragmentShaderSourceModel().print());
		
		ShaderProgram s = new ShaderProgram();
		s.run(sourceDispenser.getVertexShaderSourceModel().print(), sourceDispenser.getFragmentShaderSourceModel().print());
		return s;
	}
	
	private static void begin(ShaderConvention convention) {
		sourceDispenser = new ShaderDispenser(ConventionalShaderLinker.getInstance().getShaderDispenser(convention));
	}
	
	private static String generateLine(String input, String prependInput) {
		return prependInput + input + "\n";
	}
}
