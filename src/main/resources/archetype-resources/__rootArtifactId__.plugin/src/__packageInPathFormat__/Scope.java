#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.eclipse.cdt.core.dom.ast.IASTName;

public class Scope {
	public Scope parent = null;
	public List<Element> declerations = new LinkedList<>();

	public Scope(Scope parent){
		this.parent = parent;
	}

	@Override
	public String toString() {
		return declerations.toString() +
				"${symbol_escape}n" + (parent!=null ? parent.toString():"");
	}

	public Element getElement(IASTName name) {
		Optional<Element> match = declerations.stream().filter(e -> e.isNamed(name.toString())).findFirst();
		return match.isPresent()? match.get(): parent.getElement(name);
	}

	public void kill(){
		declerations.stream().forEach(e->e.kill());
	}
}
