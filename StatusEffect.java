public abstract class StatusEffect {
    private final String name;
    private int remainingTurns;

    protected StatusEffect(String name, int remainingTurns) {
        this.name = name;
        this.remainingTurns = remainingTurns;
    }

    public String getName() {
        return name;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public boolean isActive() {
        return remainingTurns > 0;
    }

    public void decrementDuration() {
        remainingTurns--;
    }

    public void onTurnStart(Combatant target, BattleEngine engine) {
        // Default no-op
    }

    public void onExpire(Combatant target, BattleEngine engine) {
        engine.getBattleLog().addMessage(target.getName() + " is no longer affected by " + name + ".");
    }
}
