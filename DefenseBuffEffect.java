public class DefenseBuffEffect extends StatusEffect {
    private final int bonusDefense;
    private boolean applied;

    public DefenseBuffEffect(int bonusDefense, int duration) {
        super("Defense Buff", duration);
        this.bonusDefense = bonusDefense;
        this.applied = false;
    }

    public void applyEffect(Combatant target, BattleEngine engine) {
        if (!applied) {
            target.setDefense(target.getDefense() + bonusDefense);
            applied = true;

            engine.getBattleLog().addMessage(
                target.getName() + " gains +" + bonusDefense + " defense for this round and the next round."
            );
        }
    }

    @Override
    public void onTurnStart(Combatant target, BattleEngine engine) {
        engine.getBattleLog().addMessage(
            target.getName() + " is protected by Defense Buff. (" + getRemainingTurns() + " turns left)"
        );
    }

    @Override
    public void onExpire(Combatant target, BattleEngine engine) {
        if (applied) {
            target.setDefense(target.getDefense() - bonusDefense);
        }

        engine.getBattleLog().addMessage(
            target.getName() + "'s Defense Buff has expired."
        );
    }
}