public class SmokeBombEffect extends StatusEffect {
    public SmokeBombEffect(int duration) {
        super("Smoke Bomb", duration);
    }

    /*
    *  Method that tells the BattleEngine that the Smoke Bomb effect is still active and how many turns are left
    */
    @Override
    public void onTurnStart(Combatant target, BattleEngine engine) {
        engine.setSmokeBombTurnsRemaining(getRemainingTurns());
        engine.getBattleLog().addMessage("Smoke Bomb effect is active. Enemy attacks deal 0 damage.");
    }

    /*
    *  Method that tells the BattleEngine that the Smoke Bomb effect has ended and to allow normal damage
    */
    @Override
    public void onExpire(Combatant target, BattleEngine engine) {
        engine.setSmokeBombTurnsRemaining(0);
        engine.getBattleLog().addMessage("Smoke Bomb effect has expired.");
    }
}