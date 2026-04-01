// This class implements the Action interface and defines the behavior for a defend action in combat. When executed, it applies a DefenseBuffEffect to the actor, increasing their defense by 10 for 2 turns. This allows the combatant to reduce incoming damage from attacks during that duration.
public class DefendAction implements Action {
    public void execute(Combatant actor, Combatant target) {
        actor.addEffect(new DefenseBuffEffect(10, 2));
    }
}