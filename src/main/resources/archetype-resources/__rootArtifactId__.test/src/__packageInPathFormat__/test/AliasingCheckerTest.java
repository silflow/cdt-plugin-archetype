#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test;

import ch.hsr.ifs.cdttesting.rts.junit4.RunFor;
import ${package}.constants.ProblemIDs;

@RunFor(rtsFile="/resources/Checkers/AliasingChecker.rts")
public class AliasingCheckerTest extends BaseCheckerTest {
	@Override
	protected String getProblemId() {
		return ProblemIDs.ALL_MY_PROBLEMS;
	}
}
