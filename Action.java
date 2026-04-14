// To choose which action to perform 
public interface Action {
    String getName();
    void execute(Combatant actor, BattleEngine engine);
}
