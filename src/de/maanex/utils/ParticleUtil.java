package de.maanex.utils;


import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;


public class ParticleUtil {

	private ParticleUtil() {
	}

	public static void spawn(Particle particle, Location l, int am, double speed, double xr, double yr, double zr) {
		l.getWorld().spawnParticle(particle, l, am, speed, xr, yr, zr);
	}

	public static void spawn(Particle particle, Location l, int am, double speed, double xr, double yr, double zr, int color, float size) {
		spawn(particle, l, am, speed, xr, yr, zr, Color.fromRGB(color), size);
	}

	public static void spawn(Particle particle, Location l, int am, double speed, double xr, double yr, double zr, Color color, float size) {
		l.getWorld().spawnParticle(particle, l, am, speed, xr, yr, zr, new Particle.DustOptions(color, size));
	}

	public static <T> void spawn(Particle particle, Location l, int am, double speed, double xr, double yr, double zr, T t) {
		l.getWorld().spawnParticle(particle, l, am, speed, xr, yr, zr, t);
	}

	//

	public static void spawn(Player p, Particle particle, Location l, int am, double speed, double xr, double yr, double zr) {
		p.spawnParticle(particle, l, am, speed, xr, yr, zr);
	}

	public static void spawn(Player p, Particle particle, Location l, int am, double speed, double xr, double yr, double zr, int color, float size) {
		spawn(p, particle, l, am, speed, xr, yr, zr, Color.fromRGB(color), size);
	}

	public static void spawn(Player p, Particle particle, Location l, int am, double speed, double xr, double yr, double zr, Color color, float size) {
		p.spawnParticle(particle, l, am, speed, xr, yr, zr, new Particle.DustOptions(color, size));
	}

	public static <T> void spawn(Player p, Particle particle, Location l, int am, double speed, double xr, double yr, double zr, T t) {
		p.spawnParticle(particle, l, am, speed, xr, yr, zr, t);
	}

	//

	public static void spawn(ParticlePreset preset) {
		if (preset.color != null) spawn(preset.particle, preset.location, preset.amount, preset.speed, preset.xr, preset.yr, preset.zr, preset.color, preset.size);
		else if (preset.data != null) spawn(preset.particle, preset.location, preset.amount, preset.speed, preset.xr, preset.yr, preset.zr, preset.data);
		spawn(preset.particle, preset.location, preset.amount, preset.speed, preset.xr, preset.yr, preset.zr);
	}

	public static void spawn(Player p, ParticlePreset preset) {
		if (preset.color != null) spawn(p, preset.particle, preset.location, preset.amount, preset.speed, preset.xr, preset.yr, preset.zr, preset.color, preset.size);
		else if (preset.data != null) spawn(p, preset.particle, preset.location, preset.amount, preset.speed, preset.xr, preset.yr, preset.zr, preset.data);
		spawn(p, preset.particle, preset.location, preset.amount, preset.speed, preset.xr, preset.yr, preset.zr);
	}

	/*
	 * 
	 */

	public static class ParticlePreset {
		private Particle	particle;
		private Location	location;
		private int			amount;
		private double		speed, xr, yr, zr;

		private Color	color;
		private float	size;
		private Object	data;

		public ParticlePreset(Particle particle, Location location, int amount, double speed, double xr, double yr, double zr, Color color, float size, Object data) {
			this.particle = particle;
			this.location = location;
			this.amount = amount;
			this.speed = speed;
			this.xr = xr;
			this.yr = yr;
			this.zr = zr;
			this.color = color;
			this.size = size;
			this.data = data;
		}

		public ParticlePreset(Particle particle, Location location, int amount, double speed, double xr, double yr, double zr) {
			this(particle, location, amount, speed, xr, yr, zr, null, 0, null);
		}

		public ParticlePreset(Particle particle, Location location, int amount, double speed, double xr, double yr, double zr, Color color, float size) {
			this(particle, location, amount, speed, xr, yr, zr, color, size, null);
		}

		public ParticlePreset(Particle particle, Location location, int amount, double speed, double xr, double yr, double zr, Object data) {
			this(particle, location, amount, speed, xr, yr, zr, null, 0, data);
		}

		//

		public Particle getParticle() {
			return particle;
		}

		public Location getLocation() {
			return location;
		}

		public int getAmount() {
			return amount;
		}

		public double getSpeed() {
			return speed;
		}

		public double getXr() {
			return xr;
		}

		public double getYr() {
			return yr;
		}

		public double getZr() {
			return zr;
		}

		public Color getColor() {
			return color;
		}

		public float getSize() {
			return size;
		}

		public Object getData() {
			return data;
		}

		//

		public ParticlePreset setParticle(Particle particle) {
			this.particle = particle;
			return this;
		}

		public ParticlePreset setLocation(Location location) {
			this.location = location;
			return this;
		}

		public ParticlePreset setAmount(int amount) {
			this.amount = amount;
			return this;
		}

		public ParticlePreset setSpeed(double speed) {
			this.speed = speed;
			return this;
		}

		public ParticlePreset setXr(double xr) {
			this.xr = xr;
			return this;
		}

		public ParticlePreset setYr(double yr) {
			this.yr = yr;
			return this;
		}

		public ParticlePreset setZr(double zr) {
			this.zr = zr;
			return this;
		}

		public ParticlePreset setColor(Color color) {
			this.color = color;
			return this;
		}

		public ParticlePreset setSize(float size) {
			this.size = size;
			return this;
		}

		public ParticlePreset setData(Object data) {
			this.data = data;
			return this;
		}

	}

}
