package info.palamarchuk.api.cooking;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
public class AppTest {
  @Test
  public void startsApplication() throws Exception {
    App.main();
  }
}
