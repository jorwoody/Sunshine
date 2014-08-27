package jorwoody.sunshine.app.test;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;

/* Created by: Jordan Wood - August 2014
 * Description:
 * Test suite
 */
public class FullTestSuite {

    public static Test suite() {
        return new TestSuiteBuilder(FullTestSuite.class).includeAllPackagesUnderHere().build();
    }

    public FullTestSuite() {
        super();
    }
}
