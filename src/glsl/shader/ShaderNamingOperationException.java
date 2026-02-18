package glsl.shader;

public class ShaderNamingOperationException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5843417328899164269L;

	public ShaderNamingOperationException() {
		super("A shader name is being queried for printing, but we dont\'t have one!");
	}
}
