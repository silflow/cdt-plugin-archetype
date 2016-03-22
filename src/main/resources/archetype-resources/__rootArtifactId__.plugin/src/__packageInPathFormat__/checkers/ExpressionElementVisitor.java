#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.checkers;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;

import ${package}.Element;
import ${package}.Scope;

public class ExpressionElementVisitor extends ASTVisitor {

	private Scope scope;
	private Element element;
	private ICheckerReporter testChecker;

	public ExpressionElementVisitor(Scope scope, ICheckerReporter testChecker) {
		this.testChecker = testChecker;
		shouldVisitExpressions = true;
		this.scope = scope;
	}


	@Override
	public int visit(IASTExpression expression) {
		if (expression instanceof IASTUnaryExpression) {
			visitUnaryExpression((IASTUnaryExpression) expression);
		} else if (expression instanceof IASTIdExpression) {
			visitIdExpression((IASTIdExpression) expression);
		}
		return super.visit(expression);
	}

	private void visitUnaryExpression(IASTUnaryExpression expression) {
		if (expression.getOperator() == IASTUnaryExpression.op_amper) {
			if (expression.getChildren()[0] instanceof IASTIdExpression) {
				element = visitIdExpression((IASTIdExpression) expression.getChildren()[0]);
			}
		} else if (expression.getOperator() == IASTUnaryExpression.op_star){
			if (expression.getChildren()[0] instanceof IASTIdExpression) {
				Element e = visitIdExpression((IASTIdExpression) expression.getChildren()[0]);
				if(!e.isValid()){
					testChecker.reportProblem(expression);

				}
			}
		}
	}

	private Element visitIdExpression(IASTIdExpression iastNode) {
		return (scope.getElement(iastNode.getName()));
	}

	public Element getElement() {
		return element;
	}

}
