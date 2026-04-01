// This class implements the Action interface and defines the behavior for using an item in combat. When executed, it checks if the actor is an instance of Player and, if so, it uses the first item in the player's inventory on the actor. This allows the player to utilize items during combat, such as healing potions or buffs, to gain an advantage over their opponent.
public class UseItemAction implements Action {
    public void execute(Combatant actor, Combatant target) {
        if (actor instanceof Player) {
            ((Player) actor).getInventory().useItem(0, actor);
        }
    }
}