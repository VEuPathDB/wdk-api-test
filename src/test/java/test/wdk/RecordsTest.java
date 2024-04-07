package test.wdk;

import static test.support.Conf.SERVICE_PATH;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import test.support.util.RequestFactory;
import test.support.util.Session;
import test.support.util.SessionFactory;

@DisplayName("Records")
public class RecordsTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/record-types";
  public static final String EXP_PATH = BASE_PATH + "?format=expanded";

  public final Session _session;

  RecordsTest(SessionFactory sessionFactory) {
    _session = sessionFactory.getCachedGuestSession();
  }

  @Test
  @DisplayName("GET " + BASE_PATH)
  void getRecordNames() {
    getAllRecordNames(_session);
  }

  @Test
  @DisplayName("GET " + EXP_PATH)
  void getRecordDetails() {
    _session.jsonSuccessRequest().when().get(EXP_PATH);
  }

  public static String[] getAllRecordNames(RequestFactory req) {
    return req.jsonSuccessRequest().when().get(BASE_PATH).as(String[].class);
  }
}
