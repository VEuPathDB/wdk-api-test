package test.wdk;

import java.util.LinkedList;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import test.support.util.Session;
import test.support.util.SessionFactory;

@DisplayName("Questions")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionsTest extends TestBase {
  public static final String
    BASE_PATH = RecordsTest.BASE_PATH + "/{recordType}/searches",
    PT_BASE_PATH = RecordsTest.BASE_PATH + "/{0}/searches";
  public static final String
    BY_NAME_PATH = BASE_PATH + "/{question}",
    PT_BY_NAME_PATH = PT_BASE_PATH + "/{1}";

  public final Session _session;

  public QuestionsTest(SessionFactory sessionFactory) {
    _session = sessionFactory.getCachedGuestSession();
  }

  @Test
  @DisplayName("GET " + BASE_PATH)
  void getQuestions() {
    getQuestionList();
  }

  @ParameterizedTest(name = "GET " + PT_BY_NAME_PATH)
  @DisplayName("GET " + BY_NAME_PATH)
  @MethodSource("getQuestionList")
  void getQuestionDetails(String record, String name) {
    _session.jsonSuccessRequest().when().get(BY_NAME_PATH, record, name);
  }

  public Stream<Arguments> getQuestionList() {
    var out = new LinkedList<String[]>();

    var recs = RecordsTest.getAllRecordNames(_session);

    for (var rec: recs) {
      var searches = _session.jsonSuccessRequest().when().
        get(BASE_PATH, rec).
        as(SimpleQuestion[].class);

      for (var search: searches) {
        if (!search.urlSegment.toLowerCase().contains("dataset"))
          out.add(new String[] { rec, search.urlSegment });
      }
    }

    return out.stream()
      .map(s -> Arguments.of(s[0], s[1]));
  }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class SimpleQuestion {
  public String getUrlSegment() {
    return urlSegment;
  }

  public void setUrlSegment(String urlSegment) {
    this.urlSegment = urlSegment;
  }

  String urlSegment;
}
