#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ${package}.test.AliasingCheckerTest;

@RunWith(Suite.class)
@SuiteClasses({
//@formatter:off
	AliasingCheckerTest.class
//@formatter:on
})
public class TestSuite {
}
