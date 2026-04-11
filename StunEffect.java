// StunEffect is applied by the Warrior's Shield Bash special skill
public class StunEffect extends StatusEffect {
    // Constructor 
    public StunEffect(int duration) {
        super("Stun", duration);
    }

    // Flags the target as stunned
    @Override
    public void onApply(Combatant target, BattleEngine engine) {
        target.setStunned(true);
    }

    // Logs a message at the start of the stunned combatant's turn (to let user know)
    @Override
    public void onTurnStart(Combatant target, BattleEngine engine) {
        engine.getBattleLog().addMessage(target.getName() + " is stunned and cannot act.");
    }

    // Clears the stun flag from the target
    // then give to parent to log the expiry message
    @Override
    public void onExpire(Combatant target, BattleEngine engine) {
        target.setStunned(false);
        super.onExpire(target, engine);
    }
}