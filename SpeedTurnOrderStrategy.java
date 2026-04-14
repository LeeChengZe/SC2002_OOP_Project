import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
*   Determines the turn order according to the speed declared
*/
public class SpeedTurnOrderStrategy implements TurnOrderStrategy {
    @Override
    public List<Combatant> determineTurnOrder(List<Combatant> combatants) {
        List<Combatant> ordered = new ArrayList<>(combatants); 
        ordered.sort(Comparator.comparingInt(Combatant::getSpeed).reversed().thenComparing(Combatant::getName));
        return ordered;
    }
}
