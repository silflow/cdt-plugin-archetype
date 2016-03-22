#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

public class PtrElement extends Element {

	public Element pointTo = null;

	public PtrElement(String name) {
		this.name = name;
	}

	public void setElement(Element innerElem) {
		pointTo = innerElem;

	}

	@Override
	public String toString() {
		return name + " " + pointTo + " ";
	}

	@Override
	public boolean isValid(){
		if(!wentOutOfScope) return false;
		return pointTo==null? false: pointTo.isValid();
	}

	@Override
	public void kill() {
		this.wentOutOfScope =false;
	}

}
