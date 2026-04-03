/*
*  Blueprint that forces every item in the game to have a name and a use method
*/
public interface Item {
    String getName();
    void use(Player user, Combatant target, BattleEngine engine);
}
