public class StunEffect extends StatusEffect {
    public StunEffect(int duration) {
        super("Stun", duration);
    }

    @Override
    public void onTurnStart(Combatant target, BattleEngine engine) {
        engine.getBattleLog().addMessage(target.getName() + " is stunned and cannot act.");
    }
}
