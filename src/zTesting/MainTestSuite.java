package zTesting;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//JUnit Suite Test
@RunWith(Suite.class)

@Suite.SuiteClasses({ 
   TestMovement.class
})
/*
 * This class runs all the test classes specified above in @Suite.SuiteClasses({ })
 */
public class MainTestSuite {
}