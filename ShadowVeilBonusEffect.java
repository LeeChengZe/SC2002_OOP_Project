public class ShadowVeilBonusEffect extends StatusEffect {
    private static final int ATTACK_BONUS = 10;

    public ShadowVeilBonusEffect() {
        super("Shadow Veil Bonus", 1);
    }

    @Override
    public void onApply(Combatant target, BattleEngine engine) {
        target.increaseAttack(ATTACK_BONUS);
        engine.getBattleLog().addMessage(target.getName() + " emerges from the shadows with +" + ATTACK_BONUS + " ATK for 1 turn!");
    }

    @Override
    public void onExpire(Combatant target, BattleEngine engine) {
        target.increaseAttack(-ATTACK_BONUS);
        engine.getBattleLog().addMessage(target.getName() + "'s Shadow Veil ATK bonus fades.");
    }
}
