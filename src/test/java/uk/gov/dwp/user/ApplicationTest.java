package uk.gov.dwp.user;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootTest
class ApplicationTest {

  @Autowired
  private GenericApplicationContext ctx;

  @Test
  void contextLoads() {
    assertTrue("Context should be running", ctx.isRunning());
  }

}
