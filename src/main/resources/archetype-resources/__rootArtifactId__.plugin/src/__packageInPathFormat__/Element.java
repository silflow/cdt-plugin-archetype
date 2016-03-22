#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

public abstract class Element {

	protected String name;
	protected boolean wentOutOfScope = true;

	@Override
	public String toString() {
		return name.toString();
	}

	public boolean isNamed(String name2) {
		return name.equals(name2);
	}

	public abstract boolean isValid();

	public abstract void kill();
}
