package test.wdk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import test.support.Category;
import test.support.util.GuestRequestFactory;
import test.support.util.RecordTypeUtil;

import static test.support.util.RecordTypeUtil.BASE_URI;
import static test.support.util.RecordTypeUtil.KEYED_URI;

@DisplayName("Records")
public class RecordsTest extends TestBase {
  private final GuestRequestFactory req;

  RecordsTest(GuestRequestFactory req) {
    this.req = req;
  }

  @Test
  @DisplayName("GET " + BASE_URI)
  void getAllRecordNames() {
    req.jsonSuccessRequest().when().get(BASE_URI);
  }

  @Test
  @DisplayName("GET " + RecordTypeUtil.EXPANDED)
  void getAllRecordDetails() {
    req.jsonSuccessRequest().when().get(RecordTypeUtil.EXPANDED);
  }

  @Test
  @DisplayName("GET popsetSetquence record type")
  @Tag (Category.PLASMO_TEST)
  void getPopsetRecordDetails() {
    req.jsonSuccessRequest().when().get(KEYED_URI, "popsetSequence");
  }
}
