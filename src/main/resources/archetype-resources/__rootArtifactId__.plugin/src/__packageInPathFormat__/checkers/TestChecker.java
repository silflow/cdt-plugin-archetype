#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.checkers;

import org.eclipse.cdt.codan.core.CodanRuntime;
import org.eclipse.cdt.codan.core.cxx.model.AbstractIndexAstChecker;
import org.eclipse.cdt.codan.core.model.IProblemLocation;
import org.eclipse.cdt.codan.core.model.IProblemLocationFactory;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTNodeLocation;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import ${package}.constants.ProblemIDs;

public class TestChecker extends AbstractIndexAstChecker implements ICheckerReporter {

	private ScopeVisitor visitor = new ScopeVisitor(this);

	@Override
	public void processAst(IASTTranslationUnit ast) {
		System.out.println("Running Checker");
		ast.accept(visitor);
	}

	@Override
	public void reportProblem(IASTNode expression) {
		IASTNodeLocation firstLoc = expression.getNodeLocations()[0];

		int start = firstLoc.getNodeOffset();
		int end = firstLoc.getNodeOffset() + expression.getRawSignature().length();
		int line = firstLoc.asFileLocation().getStartingLineNumber();

		IProblemLocationFactory problemLocationFactory = CodanRuntime.getInstance().getProblemLocationFactory();
		IProblemLocation problemLocation = problemLocationFactory.createProblemLocation(getFile(), start, end, line);
		reportProblem(ProblemIDs.ALL_MY_PROBLEMS,
				problemLocation,
				expression.toString()+" Has a problem");

	}


}
