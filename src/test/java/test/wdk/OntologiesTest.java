package test.wdk;

import static test.support.Conf.SERVICE_PATH;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import test.support.util.Session;
import test.support.util.SessionFactory;

@DisplayName("Ontologies")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OntologiesTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/ontologies";
  public static final String BY_NAME_PATH = BASE_PATH + "/{ontologyName}";

  public final Session _session;

  public OntologiesTest(SessionFactory sessionFactory) {
    _session = sessionFactory.getCachedGuestSession();
  }

  @Test
  @DisplayName("GET " + BASE_PATH)
  void getOntologies() {
    getOntologyList();
  }

  @ParameterizedTest(name = "GET " + BASE_PATH + "/{0}")
  @DisplayName("GET " + BY_NAME_PATH)
  @MethodSource("getOntologyList")
  void getOntology(String name) {
    _session.jsonSuccessRequest().when().get(BY_NAME_PATH, name);
  }

  @ParameterizedTest
  @DisplayName("POST " + BY_NAME_PATH)
  @MethodSource("getOntologyList")
  @Disabled("What is this endpoint?")
  void postOntologyPath(String name) {
    // TODO
  }

  String[] getOntologyList() {
    return _session.jsonSuccessRequest().when().get(BASE_PATH).as(String[].class);
  }
}
