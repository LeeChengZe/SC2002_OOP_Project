public class ShadowVeilEffect extends SmokeBombEffect {
    public ShadowVeilEffect() {
        super(1); // invincibility lasts 1 turn
    }

    @Override
    public void onApply(Combatant target, BattleEngine engine) {
        super.onApply(target, engine);
        engine.getBattleLog().addMessage(target.getName() + " vanishes into the shadows! Enemy attacks deal 0 damage this turn.");
    }

    @Override
    public void onExpire(Combatant target, BattleEngine engine) {
        engine.setSmokeBombTurnsRemaining(0);
        engine.getBattleLog().addMessage(target.getName() + "'s Shadow Veil fades.");
        target.addStatusEffect(new ShadowVeilBonusEffect(), engine);
    }
}
