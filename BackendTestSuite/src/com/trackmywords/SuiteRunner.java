package com.trackmywords;

import com.trackmywords.queryprovider.tests.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdminViewQueryTests.class,
        InvalidAdminDeleteTests.class,
        InvalidAdminInsertQueryTests.class,
        InvalidAdminUpdateQueryTests.class,
        InvalidAdminViewQueryTests.class,
        InvalidUserQueryTests.class,
        UserQueryTests.class
})
public class SuiteRunner {
}
