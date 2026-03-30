public class SmokeBomb implements Item {
    private final String name = "Smoke Bomb";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void use(Combatant user, Combatant target, BattleEngine engine) {
        engine.setSmokeBombTurnsRemaining(2);
        engine.getBattleLog().addMessage(user.getName() + " used Smoke Bomb. Enemy attacks deal 0 damage for current turn and next turn.");
    }
}
}