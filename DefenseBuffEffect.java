public class DefenseBuffEffect extends StatusEffect {
    private final int bonusDefense;

    public DefenseBuffEffect(int bonusDefense, int rounds) {
        super("Defense Buff", rounds);
        this.bonusDefense = bonusDefense;
    }

    @Override
    public void onApply(Combatant target, BattleEngine engine) {
        target.modifyDefense(bonusDefense);
    }

    @Override
    public void onRoundEnd(Combatant target, BattleEngine engine) {
        decrementDuration();
    }

    @Override
    public void onExpire(Combatant target, BattleEngine engine) {
        target.modifyDefense(-bonusDefense);
        engine.getBattleLog().addMessage(target.getName() + "'s Defense Buff has expired.");
    }
}
