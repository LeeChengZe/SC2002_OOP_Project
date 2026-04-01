import java.util.List;


/*
* Forces all order strategy classes to implement this method
 */
public interface TurnOrderStrategy {
    List<Combatant> determineTurnOrder(List<Combatant> combatants);
}
