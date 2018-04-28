package de.maanex.utils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


public class BlockUtil {
	public static final BlockFace[]	axis	= new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
	public static final BlockFace[]	radial	= new BlockFace[] { BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST,
			BlockFace.NORTH_WEST };

	public static BlockFace[] getCardinalBlockFaces() {
		BlockFace[] cardinalFaces = new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.DOWN };
		return cardinalFaces;
	}

	public static BlockFace[] getFacesForSnowPillar() {
		BlockFace[] cardinalFaces = new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.NORTH_EAST, BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST,
				BlockFace.SOUTH_WEST };
		return cardinalFaces;
	}

	public static final double lengthSq(double x, double y, double z) {
		return x * x + y * y + z * z;
	}

	public static final double lengthSq(double x, double z) {
		return x * x + z * z;
	}

	@SuppressWarnings("unchecked")
	public static List<Location> makeCylinder(Location location, int height, double radius) {
		HashMap<Integer, ArrayList<?>> locations = new HashMap<Integer, ArrayList<?>>();
		double invRadiusX = 1.0 / (radius += 0.5);
		double invRadiusZ = 1.0 / radius;
		int ceilRadiusX = (int) Math.ceil(radius);
		int ceilRadiusZ = (int) Math.ceil(radius);
		double nextXn = 0.0;
		int x = 0;
		block0: while (x <= ceilRadiusX) {
			double xn = nextXn;
			nextXn = (x + 1) * invRadiusX;
			double nextZn = 0.0;
			int z = 0;
			while (z <= ceilRadiusZ) {
				double zn = nextZn;
				nextZn = (z + 1) * invRadiusZ;
				double distanceSq = lengthSq(xn, zn);
				if (distanceSq > 1.0) {
					if (z != 0) break;
					break block0;
				}
				int y = 0;
				while (y < height) {
					if (!locations.containsKey(y)) {
						locations.put(y, new ArrayList<Object>());
					}
					((List<Location>) locations.get(y)).add(location.clone().add(x, y, z));
					((List<Location>) locations.get(y)).add(location.clone().add((-x), y, z));
					((List<Location>) locations.get(y)).add(location.clone().add(x, y, (-z)));
					((List<Location>) locations.get(y)).add(location.clone().add((-x), y, (-z)));
					++y;
				}
				++z;
			}
			++x;
		}
		ArrayList<Location> toSnow = new ArrayList<Location>();
		int i = 0;
		while (i < height) {
			if (locations.isEmpty()) break;
			toSnow.addAll((Collection<Location>) locations.get(i));
			++i;
		}
		return toSnow;
	}

	public static List<Location> makeSphere(Location location, double radius) {
		ArrayList<Location> toSnow = new ArrayList<Location>();
		double invRadiusX = 1.0 / (radius += 0.5);
		double invRadiusY = 1.0 / radius;
		double invRadiusZ = 1.0 / radius;
		int ceilRadiusX = (int) Math.ceil(radius);
		int ceilRadiusY = (int) Math.ceil(radius);
		int ceilRadiusZ = (int) Math.ceil(radius);
		double nextXn = 0.0;
		int x = 0;
		block0: while (x <= ceilRadiusX) {
			double xn = nextXn;
			nextXn = (x + 1) * invRadiusX;
			double nextYn = 0.0;
			int y = 0;
			block1: while (y <= ceilRadiusY) {
				double yn = nextYn;
				nextYn = (y + 1) * invRadiusY;
				double nextZn = 0.0;
				int z = 0;
				while (z <= ceilRadiusZ) {
					double zn = nextZn;
					nextZn = (z + 1) * invRadiusZ;
					double distanceSq = lengthSq(xn, yn, zn);
					if (distanceSq > 1.0) {
						if (z != 0) break;
						if (y != 0) break block1;
						break block0;
					}
					Random randomGenerator = new Random();
					if (y > 0) {
						if (randomGenerator.nextInt(10) > y) {
							toSnow.add(location.clone().add(x, y, z));
						}
						if (randomGenerator.nextInt(10) > y) {
							toSnow.add(location.clone().add((-x), y, z));
						}
						if (randomGenerator.nextInt(10) > y) {
							toSnow.add(location.clone().add(x, y, (-z)));
						}
						if (randomGenerator.nextInt(10) > y) {
							toSnow.add(location.clone().add((-x), y, (-z)));
						}
					}
					toSnow.add(location.clone().add(x, (-y), z));
					toSnow.add(location.clone().add((-x), (-y), z));
					toSnow.add(location.clone().add(x, (-y), (-z)));
					toSnow.add(location.clone().add((-x), (-y), (-z)));
					++z;
				}
				++y;
			}
			++x;
		}
		return toSnow;
	}

	public static BlockFace getCardinalDirection(Player player) {
		double rot = (player.getLocation().getYaw() + 180.0f) % 360.0f;
		if (rot < 0.0) {
			rot += 360.0;
		}
		return getDirection(rot);
	}

	private static BlockFace getDirection(double rot) {
		if (0.0 <= rot && rot < 22.5) { return BlockFace.NORTH; }
		if (22.5 <= rot && rot < 67.5) { return BlockFace.NORTH_EAST; }
		if (67.5 <= rot && rot < 112.5) { return BlockFace.EAST; }
		if (112.5 <= rot && rot < 157.5) { return BlockFace.SOUTH_EAST; }
		if (157.5 <= rot && rot < 202.5) { return BlockFace.SOUTH; }
		if (202.5 <= rot && rot < 247.5) { return BlockFace.SOUTH_WEST; }
		if (247.5 <= rot && rot < 292.5) { return BlockFace.WEST; }
		if (292.5 <= rot && rot < 337.5) { return BlockFace.NORTH_WEST; }
		if (337.5 <= rot && rot < 360.0) { return BlockFace.NORTH; }
		return null;
	}

	public static BlockFace getExactDirection(Player player) {
		double rot = (player.getLocation().getYaw() + 180.0f) % 360.0f;
		if (rot < 0.0) {
			rot += 360.0;
		}
		if (0.0 <= rot && rot < 45.0) { return BlockFace.NORTH; }
		if (45.0 <= rot && rot < 135.0) { return BlockFace.EAST; }
		if (135.0 <= rot && rot < 225.0) { return BlockFace.SOUTH; }
		if (225.0 <= rot && rot < 315.0) { return BlockFace.WEST; }
		if (315.0 <= rot && rot < 360.0) { return BlockFace.NORTH; }
		return null;
	}

	public static BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
		if (useSubCardinalDirections) { return radial[Math.round(yaw / 45.0f) & 7]; }
		return axis[Math.round(yaw / 90.0f) & 3];
	}

	public static BlockFace vectorToFace(Vector vector) {
		Vector v = vector.normalize();
		if (v.getX() > vector.getZ()) {
			if (v.getX() > 0.0) { return BlockFace.EAST; }
			if (v.getX() < 0.0) { return BlockFace.WEST; }
			return BlockFace.SELF;
		}
		if (v.getZ() > 0.0) { return BlockFace.SOUTH; }
		if (v.getZ() < 0.0) { return BlockFace.NORTH; }
		return BlockFace.SELF;
	}
}
