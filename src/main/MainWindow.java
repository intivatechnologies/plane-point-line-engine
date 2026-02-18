package main;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import basic_shape.*;
import glsl.shader.ShaderProgram;
import glsl.shader.ShaderStation;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL20.*;

public class MainWindow {
	
	private Shape shape = new Rectangle();
	private ShaderProgram shaderProgram;

	public void run() {
		//Configure window instantiation
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		GLFWErrorCallback.createPrint(System.err).set();

		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		long window = glfwCreateWindow(800, 560, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Set callbacks
		glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(win, true); // We will detect this in the rendering loop
		});

		// Center the window
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);

			glfwGetWindowSize(window, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		}

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
				
		// Initialize OpenGL
		GL.createCapabilities();
		ShaderStation.beginFillShader();
		ShaderStation.addUniformVariableVertex("mat4 u_projection");
		shaderProgram = ShaderStation.build();
		shape.run();
		
		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
			
			glUseProgram(shaderProgram.getId());
			
			/*
			Matrix4f projection = new Matrix4f().ortho(0.0f, 800.0f, 0.0f, 600.0f, -1.0f, 1.0f);

			try (MemoryStack stack = MemoryStack.stackPush()) {
			    FloatBuffer fb = stack.mallocFloat(16);
			    projection.get(fb);
			    int loc = glGetUniformLocation(shaderProgram.getProgramId(), "u_projection");
			    glUniformMatrix4fv(loc, false, fb);
			}
			
			Matrix4f model = new Matrix4f().identity();
			model.translate(new Vector3f(100.0f, 50.0f, 0.0f));
			model.scale(2.0f, 1.5f, 1.0f);
			
			try (MemoryStack stack = MemoryStack.stackPush()) {
			    FloatBuffer fb = stack.mallocFloat(16);
			    projection.get(fb);
			    int loc = glGetUniformLocation(shaderProgram.getProgramId(), "u_model");
			    glUniformMatrix4fv(loc, false, fb);
			}
			*/
			
			shape.draw();

			glfwSwapBuffers(window);
			glfwPollEvents();
		}
		
		glDeleteProgram(shaderProgram.getId());
		shape.unbind();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public static void main(String[] args) {
		new MainWindow().run();
	}

}