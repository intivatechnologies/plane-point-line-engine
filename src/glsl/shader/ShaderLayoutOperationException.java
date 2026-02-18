package glsl.shader;

public class ShaderLayoutOperationException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2612158100498427050L;
	
	private static final String message = "A shader layout is being queried printing, but we don\'t have an index!";

	public ShaderLayoutOperationException() {
		super(message);
	}
	
	public ShaderLayoutOperationException(Throwable cause) {
		super(message, cause);
	}
}
