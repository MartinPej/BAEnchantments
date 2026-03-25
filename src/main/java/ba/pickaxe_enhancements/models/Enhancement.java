package main.java.ba.pickaxe_enhancements.models;

import org.bukkit.Particle;
import org.bukkit.Sound;

public class Enhancement
{
	private final Particle particle;
	private final Sound sound;
	private final float volume;
	private final float pitch;
	private final EnhancementLevel one;
	private final EnhancementLevel two;
	private final EnhancementLevel three;

	public Enhancement(Particle particle, Sound sound, float volume, float pitch, EnhancementLevel one, EnhancementLevel two, EnhancementLevel three)
	{
		this.particle = particle;
		this.sound = sound;
		this.volume = volume;
		this.pitch = pitch;
		this.one = one;
		this.two = two;
		this.three = three;
	}

	public Particle getParticle() { return particle; }
	public Sound getSound() { return sound; }
	public float getVolume() { return volume; }
	public float getPitch() { return pitch; }
	public EnhancementLevel getOne() { return one; }
	public EnhancementLevel getTwo() { return two; }
	public EnhancementLevel getThree() { return three; }
}