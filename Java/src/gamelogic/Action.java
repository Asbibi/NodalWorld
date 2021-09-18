package gamelogic;

/**
* Interface for actions produced by the rules at each frame.
* 
* @see Rule
*/ 
public interface Action {

	/**
	* Executes the action and therefore changes the state of the game.
	*/ 
	public abstract void execute();

}
