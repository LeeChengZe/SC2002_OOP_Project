public class DefendAction implements Action {
    public void execute(Combatant actor, Combatant target) {
        actor.addEffect(new DefenseBuffEffect(10, 2));
    }
}