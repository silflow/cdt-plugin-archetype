#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

public class ObjElement extends Element {

	public ObjElement(String name) {
		this.name = name;
	}

	@Override
	public boolean isValid() {
		return wentOutOfScope;
	}

	@Override
	public void kill() {
		this.wentOutOfScope = false;
	}




}
