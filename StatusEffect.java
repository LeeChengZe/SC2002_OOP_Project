public abstract class StatusEffect {
    private final String name;
    private int remainingDuration;

    protected StatusEffect(String name, int remainingDuration) {
        this.name = name;
        this.remainingDuration = remainingDuration;
    }
    //Returns the display name of this status effect.
    public String getName() {
        return name;
    }

    // Returns number of turns remaining for this effect
    public int getRemainingDuration() {
        return remainingDuration;
    }

    // Allows duration of method to be decremented
    protected void decrementDuration() {
        remainingDuration--;
    }

    // Returns when the effect expires (cleaner to use boolean everywhere)
    public boolean isExpired() {
        return remainingDuration <= 0;
    }

    /**
     * Called immediately when this effect is applied to a combatant.
     * Override to apply any immediate stat changes or notifications.
     *
     * @param target the combatant this effect is being applied to
     * @param engine the current BattleEngine
     */ 
    public void onApply(Combatant target, BattleEngine engine) {
        
    }

    /**
     * Called at the start of the affected combatant's turn each round.
     * Override to apply per-turn behaviour (e.g. preventing action when stunned).
     */
    public void onTurnStart(Combatant target, BattleEngine engine) {
        
    }

    
    /**
     * Called at the end of each round for this effect.
     * Override to apply per-round behaviour and call decrementDuration() when appropriate.
     */
    public void onRoundEnd(Combatant target, BattleEngine engine) {
        
    }
    // Called when this effect expires and is about to be removed from the combatant.
    public void onExpire(Combatant target, BattleEngine engine) {
        engine.getBattleLog().addMessage(target.getName() + " is no longer affected by " + name + ".");
    }
}
