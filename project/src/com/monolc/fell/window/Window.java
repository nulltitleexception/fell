package com.monolc.fell.window;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

import com.monolc.fell.graphics.Graphics;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	long id;
	KeyHandler kh;
	Graphics graphics;
	public Window(int w, int h, String t, int GLMaj, int GLMin) {
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, GLMaj);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, GLMin);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		id = glfwCreateWindow(w, h, t, NULL, NULL);
		if (id == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		this.bindKeyCallback();
		this.createContext();
		graphics = new Graphics(this);
	}
	public Window(int w, int h, String t, int GLMaj, int GLMin, boolean visible, boolean resizable, boolean floating, boolean decorated) {
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, visible ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, resizable ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_FLOATING, floating ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_DECORATED, decorated ? GL_TRUE : GL_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, GLMaj);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, GLMin);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		id = glfwCreateWindow(w, h, t, NULL, NULL);
		if (id == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
		this.bindKeyCallback();
		this.createContext();
		graphics = new Graphics(this);
	}
	public Graphics getGraphics() {
		return graphics;
	}
	public void bindKeyCallback() {
		kh = new KeyHandler(this);
		glfwSetKeyCallback(id, kh);
	}
	public void createContext() {
		glfwMakeContextCurrent(id);
		GLContext.createFromCurrent();
		System.out.println("OpenGL V" + GL11.glGetInteger(GL30.GL_MAJOR_VERSION) + "." + GL11.glGetInteger(GL30.GL_MINOR_VERSION) + " Initialized");
	}
	public void setPosition(int x, int y) {
		GLFW.glfwSetWindowPos(id, x, y);
	}
	public void keyPress(int key, int scancode, int action, int mods) {
		if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
			glfwSetWindowShouldClose(id, GL_TRUE);
		} else {
			if (action == GLFW_PRESS) {
				System.out.println("Key #" + key + " was pressed");
			}
		}
	}
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(id) != GL_TRUE;
	}
	public void update() {
		double time = glfwGetTime();
		glfwSwapBuffers(id);
		glfwPollEvents();
	}
	public void destroy() {
		glfwDestroyWindow(id);
		kh.release();
	}
}
