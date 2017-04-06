package de.pixlpommes.jam.units.base;

import java.util.ArrayList;
import java.util.List;

import de.pixlpommes.jam.actions.Action;

/**
 * Basic setup for all units in game.
 * 
 * @author Thomas Borck - http://www.pixlpommes.de
 * @version 0.1
 */
public abstract class Unit {

	/** TODO: describe '_hpCurrent' */
	protected float _hpCurrent, _hpMax;
	
	/** TODO: describe '_mpCurrent' */
	protected float _mpCurrent, _mpMax;
	
	/** TODO: describe '_abilities' */
	protected List<Ability> _abilities;
	
	/** TODO: describe '_debuffs' */
	protected Debuff[] _debuffs;
	
	/** TODO: describe '_activeAction' */
	protected Action _activeAction;
	
	/**
	 * @param hp
	 * @param mp
	 */
	protected Unit(int hp, int mp) {
		_hpCurrent = _hpMax = hp;
		_mpCurrent = _mpMax = mp;
		
		_abilities = new ArrayList<>();
		_debuffs = new Debuff[] {
				new Debuff(Debuff.Type.POISON),
				new Debuff(Debuff.Type.SILENCE),
				new Debuff(Debuff.Type.PARALYZE)
		};
		_activeAction = null;
	}
	
	
	
	public float getMpCurrent() { return _mpCurrent; }
	
	public void setMpCurrent(float mp) {
		_mpCurrent = mp;
	}
	
	public void updateMpCurrent(float diffMp) {
		setMpCurrent(_mpCurrent + diffMp);
	}
	
	
	public float getHpCurrent() { return _hpCurrent; }
	
	public void setHpCurrent(float hp) {
		_hpCurrent = hp;
	}
	
	public void updateHpCurrent(float diffHp) {
		setHpCurrent(_hpCurrent + diffHp);
	}
	
	/**
	 * Set units active action.
	 * 
	 * @param action
	 * @return true, if action was set; false, if another action is active
	 */
	public boolean setActiveAction(Action action) {
		if(_activeAction != null)
			return false;
		
		_activeAction = action;
		return true;
	}
	
	/**
	 * @return currently active action or null
	 */
	public Action getActiveAction() {
		return _activeAction;
	}
	
	/**
	 * @return true, if action was reseted - false, if there was no action to reset
	 */
	public boolean resetActiveAction() {
		if(_activeAction == null)
			return false;
		
		_activeAction = null;
		return true;
	}
}
