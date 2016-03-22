#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.checkers;

import org.eclipse.cdt.core.dom.ast.IASTNode;

public interface ICheckerReporter {
	void reportProblem(IASTNode expression);
}
