package main.java.ba.pickaxe_enhancements.models;

public class PlayerData
{
	private int skillPoints;
	private int explosionLevel;
	private int diskLevel;
	private int laserLevel;
	private String primaryEnhancement = "None";

	public PlayerData()
	{
		skillPoints = 0;
		explosionLevel = 0;
		diskLevel = 0;
		laserLevel = 0;
	}

	public PlayerData(String primaryEnhancement, int skillPoints, int explosionLevel, int diskLevel, int laserLevel)
	{
		this.primaryEnhancement = primaryEnhancement;
		this.skillPoints = skillPoints;
		this.explosionLevel = explosionLevel;
		this.diskLevel = diskLevel;
		this.laserLevel = laserLevel;
	}

	/*
		Get Methods
	 */
	public int getSkillPoints() { return skillPoints; }

	public int getExplosionLevel() { return explosionLevel; }

	public int getDiskLevel() { return diskLevel; }

	public int getLaserLevel() { return laserLevel; }

	public String getPrimaryEnhancement() { return primaryEnhancement; }

	/*
		Increment Methods
	 */
	public void increaseSkillPoints(int increase) { skillPoints += increase; }

	public void increaseExplosionLevel(int increase) { explosionLevel += increase; }

	public void increaseDiskLevel(int increase) { diskLevel += increase; }

	public void increaseLaserLevel(int increase) { laserLevel += increase; }

	/*
		Decrement Methods
	 */
	public void decreaseSkillPoints(int decrease) { skillPoints -= decrease; }
	public void decreaseExplosionLevel(int decrease) { explosionLevel -= decrease; }
	public void decreaseDiskLevel(int decrease) { diskLevel -= decrease; }
	public void decreaseLaserLevel(int decrease) { laserLevel -= decrease; }

	/*
		Set Methods
	 */
	public void setSkillPoints(int value) { skillPoints = value; }

	public void setExplosionLevel(int value) { explosionLevel = value; }

	public void setDiskLevel(int value) { diskLevel = value; }

	public void setLaserLevel(int value) { laserLevel = value; }

	/*
		Update Methods
	 */
	public void updatePrimaryEnhancement()
	{
		switch (primaryEnhancement)
		{
			case "None":
				if (explosionLevel > 0) primaryEnhancement = "Explosion";
				else if (diskLevel > 0) primaryEnhancement = "Disk";
				else if (laserLevel > 0) primaryEnhancement = "Laser";
				else primaryEnhancement = "None";
				break;
			case "Explosion":
				if (diskLevel > 0) primaryEnhancement = "Disk";
				else if (laserLevel > 0) primaryEnhancement = "Laser";
				else primaryEnhancement = "None";
				break;
			case "Disk":
				if (laserLevel > 0) primaryEnhancement = "Laser";
				else if (explosionLevel > 0) primaryEnhancement = "Explosion";
				else primaryEnhancement = "None";
				break;
			case "Laser":
				if (explosionLevel > 0) primaryEnhancement = "Explosion";
				else if (diskLevel > 0) primaryEnhancement = "Disk";
				else primaryEnhancement = "None";
				break;
		}
	}
}