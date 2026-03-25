package main.java.ba.pickaxe_enhancements.models;

public class EnhancementLevel
{
	private final double rate;
	private final int range;

	public EnhancementLevel(double rate, int range)
	{
		this.rate = rate;
		this.range = range;
	}

	public double getRate() { return rate; }
	public int getRange() { return range; }
}