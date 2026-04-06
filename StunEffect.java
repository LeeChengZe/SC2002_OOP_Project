public class StunEffect extends StatusEffect {
    public StunEffect(int duration) {
        super("Stun", duration);
    }

    @Override
    public void onApply(Combatant target, BattleEngine engine) {
        target.setStunned(true);
    }

    @Override
    public void onTurnStart(Combatant target, BattleEngine engine) {
        engine.getBattleLog().addMessage(target.getName() + " is stunned and cannot act.");
    }

    @Override
    public void onExpire(Combatant target, BattleEngine engine) {
        target.setStunned(false);
        super.onExpire(target, engine);
    }
}