package com.monolc.fell.world;

public class Tile {
	public static final int TILE_SIZE = 32;
	static final boolean[] passable = { true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true, true, true, true };
	boolean explored;
	int id;
	public Tile(int idNum) {
		id = idNum;
		explored = false;
	}
	public int getID() {
		return id;
	}
	public boolean isPassable() {
		return passable[id % 100];
	}
	public boolean isExplored() {
		return explored;
	}
	public void explore() {
		explored = true;
	}
}
