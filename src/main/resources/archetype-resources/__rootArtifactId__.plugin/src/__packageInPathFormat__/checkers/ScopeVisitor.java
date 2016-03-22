#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.checkers;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;

import ${package}.ObjElement;
import ${package}.PtrElement;
import ${package}.Scope;

class ScopeVisitor extends ASTVisitor {

	Scope scope = null;
	private ICheckerReporter testChecker;

	public ScopeVisitor(ICheckerReporter testChecker) {
		this.testChecker = testChecker;
		shouldVisitStatements = true;
		shouldVisitDeclarations = true;
	}

	@Override
	public int visit(IASTStatement statement) {
		if (statement instanceof IASTCompoundStatement) {
			IASTCompoundStatement compound = (IASTCompoundStatement) statement;
			System.out.println("ENTERING COMPOUND");
			System.out.println(compound);
			scope = new Scope(scope);

		} else if (statement instanceof IASTExpressionStatement) {
			IASTExpressionStatement statement1 = (IASTExpressionStatement) statement;
			IASTNode firstNode = statement1.getChildren()[0];
			if (firstNode instanceof IASTBinaryExpression
					&& ((IASTBinaryExpression) firstNode).getOperator() == IASTBinaryExpression.op_assign) {

				visitAssignOperation((IASTBinaryExpression) firstNode);

			}

		} else
			System.out.println(statement);
		return super.visit(statement);
	}

	@Override
	public int leave(IASTStatement statement) {
		if (statement instanceof IASTCompoundStatement) {
			System.out.println("LEAVING COMPOUND");
			System.out.println(scope);
			System.out.println("END OF SCOPEPRINT");
			scope.kill();
			scope = scope.parent;

		}
		return super.leave(statement);
	}

	@Override
	public int visit(IASTDeclaration declaration) {
		if (declaration instanceof IASTSimpleDeclaration && scope != null) {
			IASTSimpleDeclaration simple = (IASTSimpleDeclaration) declaration;

			for (IASTDeclarator declarator : simple.getDeclarators()) {
				IASTName name = declarator.getName();
				if (declarator.getPointerOperators().length > 0) {
					scope.declerations.add(createPtrElement(declarator, name));
				} else {
					scope.declerations.add(new ObjElement(name.toString()));
				}
			}
		}
		return super.visit(declaration);
	}

	private void visitAssignOperation(IASTBinaryExpression expression) {
		IASTExpression lhs = (IASTExpression) expression.getChildren()[0];
		IASTExpression rhs = (IASTExpression) expression.getChildren()[1];
		ExpressionElementVisitor lhsV = new ExpressionElementVisitor(scope,testChecker);
		ExpressionElementVisitor rhsV = new ExpressionElementVisitor(scope,testChecker);
		lhsV.visit(lhs);
		rhsV.visit(rhs);
		if (lhsV.getElement() instanceof PtrElement) {
			PtrElement ptrElem = (PtrElement) lhsV.getElement();
			ptrElem.pointTo = rhsV.getElement();
		}

	}

	private PtrElement createPtrElement(IASTDeclarator declarator, IASTName name) {
		PtrElement elem = new PtrElement(name.toString());

		IASTInitializer initializer = declarator.getInitializer();
		if (initializer != null && initializer.getChildren().length == 1) {
			IASTExpression expression = (IASTExpression) initializer.getChildren()[0];

			ExpressionElementVisitor visitor = new ExpressionElementVisitor(scope, testChecker);
			expression.accept(visitor);
			elem.setElement(visitor.getElement());
		}

		return elem;
	}

}
