package com.monolc.fell;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import com.monolc.fell.graphics.*;
import com.monolc.fell.resources.ResourceHandler;
import com.monolc.fell.resources.Sprite;
import com.monolc.fell.version.*;
import com.monolc.fell.window.*;
import com.monolc.fell.world.Entity;
import com.monolc.fell.world.Floor;
import com.monolc.fell.world.Location;
import com.monolc.fell.world.Player;

public class Program {
	public static void main(String[] args) {
		System.out.println("Running...");
		VersionData v = new VersionData();
		System.out.println("Fell " + v.getStage() + " V" + v.getVersion() + ", Build#" + v.getBuild());
		System.out.println("LWJGL V" + Sys.getVersion());
		ResourceHandler res = new ResourceHandler();
		GLFWErrorCallback errorCallback = Callbacks.errorCallbackPrint(System.err);
		glfwSetErrorCallback(errorCallback);
		if (glfwInit() != GL11.GL_TRUE) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		int GLMajor = 4;
		int GLMinor = 1;
		Window w = new Window(1600, 900, "Fell " + v.getStage() + " V" + v.getVersion() + ", Build#" + v.getBuild() + " OpenGL V" + GLMajor + "." + GLMinor, GLMajor, GLMinor);
		w.setPosition(10, 30);
		res.getShader("default").bind();
		res.getShader("default").setUniformi("width", 6400);
		res.getShader("default").setUniformi("height", 3600);
		Floor floor = new Floor(res.getTexture("tilesC"), 120, 100);
		Player plr = new Player(res.getSprite("player"), floor.getOpenLocation());
		while (!w.shouldClose()) {
			w.update();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			while (w.eventsToQuery()) {
				Event e = w.queryEvent();
				if (e.getType() == Event.KEY_PRESS) {
					if (e.getKey() == GLFW.GLFW_KEY_W) {
						plr.moveUp(1);
					} else if (e.getKey() == GLFW.GLFW_KEY_A) {
						plr.moveLeft(1);
					} else if (e.getKey() == GLFW.GLFW_KEY_S) {
						plr.moveDown(1);
					} else if (e.getKey() == GLFW.GLFW_KEY_D) {
						plr.moveRight(1);
					}
				}
			}
			plr.moveCameraTo(res.getShader("default"));
			floor.draw(res.getShader("default"));
		}
		w.destroy();
		glfwTerminate();
		errorCallback.release();
	}
}
