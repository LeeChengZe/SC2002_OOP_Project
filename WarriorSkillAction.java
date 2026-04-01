// This class implements the Action interface and defines the behavior for a warrior's special skill in combat. When executed, it calculates damage based on the actor's attack and the target's defense, applies that damage to the target, and then applies a stun effect to the target for 2 turns. Additionally, it sets a cooldown of 3 turns for the actor, preventing them from using this skill again until the cooldown expires.
public class WarriorSkillAction implements Action {
    public void execute(Combatant actor, Combatant target) {
        int damage = Math.max(0, actor.attack - target.defense);
        target.takeDamage(damage);
        target.addEffect(new StunEffect(2));
        actor.setCooldown(3);
    }
}