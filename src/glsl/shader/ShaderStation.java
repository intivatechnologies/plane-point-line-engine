package glsl.shader;

class ShaderDispenser {
	private ShaderSourceModel vertexShaderSourceModel, fragmentShaderSourceModel;
	
	public ShaderDispenser(String[] vertexSource, String[] fragmentSource) {
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

public class ShaderStation {
	private static ShaderDispenser sourceDispenser;
	
	public static void beginFillShader() {
		begin(ShaderLinker.CommunicationKey.FILL);
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
	
	private static void begin(ShaderLinker.CommunicationKey key) {
		String[][] shaderSource = ShaderLinker.getInstance().getShaderSource(key);
		sourceDispenser = new ShaderDispenser(shaderSource[0], shaderSource[1]);
	}
}
