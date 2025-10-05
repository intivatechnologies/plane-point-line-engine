package glsl_shader;

public class ShaderFactory {
	private static ShaderSourceModel sourceCache;
	private boolean uniformAdded = false;
	
	private static void begin(ShaderConvention convention) {
		sourceCache = new ShaderSourceModel(ConventionalShaderLinker.getInstance().getShaderSourceModel(convention));
	}
	
	public static void beginFillShader() {
		begin(ShaderConvention.FILL);
	}
	
	public static void addUniformVariable(String declaration) {
		//String line = generateLine(declaration, "uniform ");
		int i = 0;
		for(String line : sourceCache.getVertexMemorySet()) {
			if(line.contains("void main()"))
				break;
			else
				++i;
		}
		
		
	}
	
	public static ShaderProgram build() {
		ShaderProgram s = new ShaderProgram();
		s.run(sourceCache.getVertexMemorySet().getText(), sourceCache.getFragmentMemorySet().getText());
		return s;
	}
	
	private static String generateLine(String input, String prependInput) {
		return prependInput + input + "\n";
	}
}
