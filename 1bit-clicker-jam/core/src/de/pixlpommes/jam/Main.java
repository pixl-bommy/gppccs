package de.pixlpommes.jam;

import de.pixlpommes.jam.actions.ActionManager;
import de.pixlpommes.jam.arena.Arena;
import de.pixlpommes.jam.units.Player;
import de.pixlpommes.jam.units.Slime;
import de.pixlpommes.jam.units.base.Unit;

/**
 * Game entry point.
 * 
 * @author Thomas Borck - http://www.pixlpommes.de
 * @version 0.1
 */
public class Main {
	
	/** the games frames per second */
	public final static float FPS = 60.0f;
	
	/** one second has 1000*1000*1000 nanoseconds */
	public final static float NANOSECONDS = 1000f * 1000f * 1000f;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Arena a = new Arena();
		ActionManager am = new ActionManager(a);
		
		// setup player
		a.setPlayer(new Player());
		
		// setup simple party member
		a.setUnit(new Slime(), 2, 1);
		
		// setup simple enemy
		a.setUnit(new Slime(), 3, 1);
		
		System.out.println(a);
		
		
		long timeLast = System.nanoTime();
		
		while(true) {
			// delta timer
			long timeNow = System.nanoTime();
			float delta = (float)(timeNow - timeLast) / NANOSECONDS;
			//System.out.println(delta);
			
			// check for inactive party members
			for(Unit party : a.getPartyMembers()) {
				if(party != null && !party.hasActiveAction()) {
					// get first enemy and attack it
					for(int id : Arena.IDS_ENEMY) {
						if(a.getUnit(id) != null) {
							// attack this unit
							int x = id % Arena.COLUMNS;
							int y = (int) Math.floor(id / Arena.COLUMNS);
							System.out.println(am.create(party, party.getAbility(0), x, y));
						}
					}
				}
			}
			
			// update action manager
			am.doTick(delta);
			
			
			// remove killed units
			for(int id=0; id<Arena.COLUMNS * Arena.ROWS; id++) {
				Unit unit = a.getUnit(id);
				
				if(unit != null && !unit.isAlive()) {
					// TODO: kill unit
					System.out.println("kill " + unit);
					a.setUnit(null, id);
				}
			}
			
			
			// update delta
			timeLast = timeNow;
		}
	}

}