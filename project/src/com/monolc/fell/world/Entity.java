package com.monolc.fell.world;

import com.monolc.fell.resources.Shader;
import com.monolc.fell.resources.Sprite;

public class Entity {
	public static final int PLAYER = 0, GOBLIN = 1;
	Sprite sprite;
	Location location;
	boolean inMove;
	float posX, posY;
	int type;
	public Entity(Sprite s, Location loc, int t) {
		type = t;
		location = loc;
		sprite = s;
		location.getFloor().setEntity(location.getX(), location.getY(), this);
		posX = loc.getX();
		posY = loc.getY();
		inMove = false;
	}
	int getType() {
		return type;
	}
	public void update() {
		float c = 0.02f;
		if (inMove) {
			if (Math.abs(posX - location.getX()) < c && Math.abs(posY - location.getY()) < c) {
				posX = location.getX();
				posY = location.getY();
				inMove = false;
			}
			if (posX < location.getX()) {
				posX += c;
			}
			if (posY < location.getY()) {
				posY += c;
			}
			if (posX > location.getX()) {
				posX -= c;
			}
			if (posY > location.getY()) {
				posY -= c;
			}
		}
	}
	public void draw(Shader s) {
		s.setUniformf("z", 0.9f);
		s.setUniformf("x", posX * Tile.TILE_SIZE);
		s.setUniformf("y", posY * Tile.TILE_SIZE);
		sprite.draw();
	}
	public Location getLocation() {
		return location;
	}
	public boolean moveUp() {
		return moveTo((new Location(location)).modY(1));
	}
	public boolean moveDown() {
		return moveTo((new Location(location)).modY(-1));
	}
	public boolean moveLeft() {
		return moveTo((new Location(location)).modX(-1));
	}
	public boolean moveRight() {
		return moveTo((new Location(location)).modX(1));
	}
	public boolean moveTo(Location newLoc) {
		if (inMove) {
			return false;
		}
		if (newLoc.isPassable() || (type == PLAYER && newLoc.getFloor().getEntity(newLoc) != null && newLoc.getFloor().getEntity(newLoc).getType() == Entity.GOBLIN)) {
			location.clear();
			location = newLoc;
			location.getFloor().setEntity(location.getX(), location.getY(), this);
			inMove = true;
			return true;
		}
		return false;
	}
}
