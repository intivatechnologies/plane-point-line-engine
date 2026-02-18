package glsl.syntax;

public class GLSLLayoutVariable extends GLSLVariable {

	private int layoutIndex;
	
	public GLSLLayoutVariable(int layoutIndex) { this.layoutIndex = layoutIndex; }
	
	@Override
	public String print() {
		//"layout (location = 0) in vec3 aPos;"
		return (new StringBuilder("layout (location = ")
				.append(String.valueOf(layoutIndex)).append(") ").append(super.print())).toString();
	}
}
