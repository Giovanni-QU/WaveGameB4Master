package mainGame;

/**
 * The upgrades that a user can have (they modify the game for the user)
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Upgrades {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Player player;
	private SpawnEasy spawnerE;
	private Spawn1to10 spawner;
	private Spawn10to20 spawner2;
	private UpgradeScreen upgradeScreen;
	private String ability = "";

	public Upgrades(Game game, Handler handler, HUD hud, UpgradeScreen upgradeScreen, Player player, SpawnEasy spawnerE, Spawn1to10 spawner,
			Spawn10to20 spawner2) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.upgradeScreen = upgradeScreen;
		this.player = player;
		this.spawnerE = spawnerE;
		this.spawner = spawner;
		this.spawner2 = spawner2;
	}

	public void clearScreenAbility() {
		handler.clearEnemies();
		hud.setAbilityUses(-1);
		if (hud.getAbilityUses() == 0) {
			ability = "";
		}
	}

	public void decreasePlayerSize() {
		player.setPlayerSize(24);
	}

	public void extraLife() {
		hud.setExtraLives(hud.getExtraLives() + 1);
	}

	public void healthIncrease() {
		hud.healthIncrease();
	}

	public void healthRegeneration() {
		hud.setRegen();
	}

	public void improvedDamageResistance() {
		player.setDamage(1);
	}

	public void levelSkipAbility() {
		handler.clearEnemies();
		hud.setLevel(hud.getLevel() + 1);
		if (Spawn1to10.LEVEL_SET == 1 || Spawn1to10.LEVEL_SET == 3) {
			spawner.skipLevel();
		} else if (SpawnEasy.LEVEL_SET == 1) {
			spawnerE.skipLevel();
		} else if (Spawn1to10.LEVEL_SET == 2) {
			spawner2.skipLevel();
		}
		hud.setAbilityUses(hud.getAbilityUses() - 1);
		if (hud.getAbilityUses() == 0) {
			ability = "";
		}

	}

	public void freezeTimeAbility() {
		handler.pause();
		Spawn1to10.setSpawn(false);
		Spawn10to20.setSpawn(false);
		hud.setAbilityUses(hud.getAbilityUses() - 1);
		if (hud.getAbilityUses() == 0) {
			ability = "";
		}
	}

	public void speedBoost() {
		Player.playerSpeed *= 2;
		Player.diagonalPlayerSpeed *= 2;
	}

	public String getAbility() {
		return ability;
	}

	/**
	 * Activate the correct upgrade
	 * 
	 * @param path
	 *            is to the image of the upgrade that was pressed by the user
	 */
	public void activateUpgrade(String path) {
		if (path.equals("images/clearscreenability.png")) {
			ability = "clearScreen";
			hud.setAbility(ability);
			hud.setAbilityUses(3);
		} else if (path.equals("images/decreaseplayersize.png")) {
			ability = "decreasePlayerSize";
			decreasePlayerSize();
		} else if (path.equals("images/extralife.png")) {
			ability = "extraLife";
			extraLife();
		} else if (path.equals("images/healthincrease.png")) {
			ability = "HealthIncrease";
			healthIncrease();
		} else if (path.equals("images/healthregeneration.png")) {
			ability = "HealthRegen";
			healthRegeneration();
		} else if (path.equals("images/improveddamageresistance.png")) {
			ability = "ImprovedDamageRistance";
			improvedDamageResistance();
		} else if (path.equals("images/levelskipability.png")) {
			ability = "levelSkip";
			hud.setAbility(ability);
			hud.setAbilityUses(1);
		} else if (path.equals("images/freezetimeability.png")) {
			ability = "freezeTime";
			hud.setAbility(ability);
			hud.setAbilityUses(5);
		} else if (path.equals("images/speedboost.png")) {
			ability = "SpeedBoost";
			speedBoost();
		}
		
		System.out.println(ability);

	}
	
	public void setAbility(String theAbility){
		if(theAbility.equals("clearScreen")){
			ability = "clearScreen";
			hud.setAbility(ability);
			hud.setAbilityUses(3);
		} else if (theAbility.equals("decreasePlayerSize")) {
			ability = "decreasePlayerSize";
			decreasePlayerSize();
		} else if (theAbility.equals("extraLife")) {
			ability = "extraLife";
			extraLife();
		} else if (theAbility.equals("HealthIncrease")) {
			ability = "HealthIncrease";
			healthIncrease();
		} else if (theAbility.equals("HealthRegen")) {
			ability = "HealthRegen";
			healthRegeneration();
		} else if (theAbility.equals("ImprovedDamageRistance")) {
			ability = "ImprovedDamageRistance";
			improvedDamageResistance();
		} else if (theAbility.equals("levelSkip")) {
			ability = "levelSkip";
			hud.setAbility(ability);
			hud.setAbilityUses(1);
		} else if (theAbility.equals("freezeTime")) {
			ability = "freezeTime";
			hud.setAbility(ability);
			hud.setAbilityUses(5);
		} else if (theAbility.equals("SpeedBoost")) {
			ability = "SpeedBoost";
			speedBoost();
		}
	}

	public void resetUpgrades() {
		Player.playerSpeed = 10;
		hud.resetHealth();
		hud.resetRegen();
		hud.setExtraLives(0);
		player.setPlayerSize(32);
		upgradeScreen.resetPaths();
	}

}
