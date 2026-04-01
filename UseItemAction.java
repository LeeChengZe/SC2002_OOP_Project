public class UseItemAction implements Action {
    public void execute(Combatant actor, Combatant target) {
        if (actor instanceof Player) {
            ((Player) actor).getInventory().useItem(0, actor);
        }
    }
}