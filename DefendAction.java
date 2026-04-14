// This class implements the Action interface
public class DefendAction implements Action {
    @Override
    public String getName() {
        return "Defend";
    }
    /**
    *   Applies the additional defense
    *   @param actor Apply the defense buff to this Combatant
    *   @param engine Log the message and manage the state of the game
    */
    @Override
    public void execute(Combatant actor, BattleEngine engine) {
        actor.addStatusEffect(new DefenseBuffEffect(10, 2), engine);
        engine.getBattleLog().addMessage(actor.getName() + " uses Defend and gains +10 DEF for this round and the next round.");
    }
}
