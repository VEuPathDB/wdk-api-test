package test;

import org.junit.jupiter.api.DisplayName;

@DisplayName("Steps")
public class StepsTest extends UsersTest {
  public static final String STEPS_PATH = USER_BY_ID_PATH + "/steps";
  public static final String STEP_BY_ID_PATH = STEPS_PATH + "/{stepId}";

}
