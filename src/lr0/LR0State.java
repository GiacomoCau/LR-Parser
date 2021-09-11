package lr0;

import static java.util.stream.Collectors.toSet;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import util.Grammar;
import util.State;

public class LR0State extends State<LR0State, LR0Item> {

	public LR0State(Grammar grammar, Set<LR0Item> items) {
		this.items = new LinkedHashSet<>(items);
		transitions = new LinkedHashMap<>();
		closure(grammar);
	}

	private void closure(Grammar grammar) {
		for (;;) {
			Set<LR0Item> temp = items.stream()
				.map(i-> i.getSymbol())
				.filter(s-> s!=null && grammar.isVariable(s))
				.flatMap(s-> grammar.getRulesByLhs(s).stream())
				.map(LR0Item::new)
				.collect(toSet())
			;
			if (items.containsAll(temp)) break;
			items.addAll(temp);
		}
	}
}
