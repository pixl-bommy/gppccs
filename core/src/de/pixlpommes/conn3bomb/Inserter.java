package de.pixlpommes.conn3bomb;

import java.util.stream.IntStream;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * <p>
 * The inserter.
 * </p>
 *
 * <p>
 * This object is located above the arena and throws in new blocks and bombs in
 * given intervals.
 * </p>
 *
 * @author Thomas Borck
 */
public class Inserter extends ScreenObject {

	/** TODO: describe _state */
	private State _state;

	/** TODO: describe _timerDelay */
	private float _timer, _timerDelay;

	/** TODO: describe _blocks */
	private int[] _tiles;

	/**
	 * 
	 */
	public Inserter() {
		this.setOffset(0, 0);

		_timer = _timerDelay = 2f;
		_state = State.COUNTDOWN;

		_tiles = new int[Arena.COLS];
		IntStream.range(0, _tiles.length).forEach(i -> _tiles[i] = -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.pixlpommes.conn3bomb.ScreenObject#draw(com.badlogic.gdx.graphics.g2d.
	 * Batch)
	 */
	@Override
	public void draw(Batch batch) {
		IntStream.range(0, _tiles.length).forEach(index -> {
			if (_tiles[index] != -1) {
				batch.draw(Arena.TILES, _offsetX + index * Arena.TILESIZE,
						_offsetY, _tiles[index] * Arena.TILESIZE, 0,
						Arena.TILESIZE, Arena.TILESIZE);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.pixlpommes.conn3bomb.ScreenObject#update(float)
	 */
	@Override
	public void update(float delta) {
		// update FSM
		switch (_state) {
			case COUNTDOWN :
				if (_timer < 0) {
					_state = State.CREATE;
				} else {
					_timer -= delta;
				}
				break;

			case CREATE :
				// TODO: create random block/bomb
				int rndIndex = (int) (Math.random() * _tiles.length);
				int rndBlock = (int) (Math.random() * 3);
				_tiles[rndIndex] = rndBlock;

				// TODO: start throw-in-animation
				_timer = _timerDelay; /// remove
				_state = State.ANIMATE;
				break;

			case ANIMATE :
				// TODO: animate block throw-in
				// TODO: if animation ends, reset timer for next round
				if (_timer < 0) {
					_state = State.RESET;
				} else {
					_timer -= delta;
				}
				break;

			case RESET :
				// TODO: transfer block to arena
				IntStream.range(0, _tiles.length).forEach(i -> _tiles[i] = -1);

				// reset timer
				_timer = _timerDelay;

				// restart timer
				_state = State.COUNTDOWN;
				break;
		}

	}

	/**
	 * <p>
	 * TODO: short class description.
	 * </p>
	 *
	 * <p>
	 * TODO: detailed class description.
	 * </p>
	 *
	 * @author Thomas Borck
	 */
	private static enum State {
		COUNTDOWN,

		CREATE,

		ANIMATE,

		RESET;
	}
}
