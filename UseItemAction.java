// This class implements the Action interface and defines the behavior for using an item in combat.
// When executed, it removes the selected item from the player's inventory and applies the item effect.
public class UseItemAction implements Action {
    private final Item item;
    private final Combatant target;

    /*
    *   Store the item to use and the intended target
    */
    public UseItemAction(Item item, Combatant target) {
        this.item = item;
        this.target = target;
    }

    /*
    *   Returns the name of this action
    */
    @Override
    public String getName() {
        return "Use Item";
    }

    /*
    *   Validate the actor is a player and the item is still available, then uses it
    */
    @Override
    public void execute(Combatant actor, BattleEngine engine) {
        if (!(actor instanceof Player)) {
            engine.getBattleLog().addMessage("Only players can use items.");
            return;
        }

        Player player = (Player) actor;
        if (!player.getInventory().removeItem(item)) {
            engine.getBattleLog().addMessage("Item is no longer available.");
            return;
        }
        item.use(player, target, engine);
    }
}
