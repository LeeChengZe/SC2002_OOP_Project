public abstract class StatusEffect {
    private final String name;
    private int remainingDuration;

    protected StatusEffect(String name, int remainingDuration) {
        this.name = name;
        this.remainingDuration = remainingDuration;
    }

    public String getName() {
        return name;
    }

    public int getRemainingDuration() {
        return remainingDuration;
    }

    protected void decrementDuration() {
        remainingDuration--;
    }

    public boolean isExpired() {
        return remainingDuration <= 0;
    }

    public void onApply(Combatant target, BattleEngine engine) {
        
    }

    public void onTurnStart(Combatant target, BattleEngine engine) {
        
    }

    public void onRoundEnd(Combatant target, BattleEngine engine) {
        
    }

    public void onExpire(Combatant target, BattleEngine engine) {
        engine.getBattleLog().addMessage(target.getName() + " is no longer affected by " + name + ".");
    }
}
