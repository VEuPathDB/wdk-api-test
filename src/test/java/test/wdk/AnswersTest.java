package test.wdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.support.Conf.SERVICE_PATH;

import java.util.List;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.SearchConfig;
import org.gusdb.wdk.model.api.DefaultReportRequestBody;
import org.gusdb.wdk.model.api.StandardReportConfig;
import org.gusdb.wdk.model.api.RecordInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import test.support.Category;
import test.support.util.ReportUtil;
import test.support.util.GuestRequestFactory;

public class AnswersTest extends TestBase {

  public static final String REPORT_PATH = SERVICE_PATH + "/searches/{searchName}/reports/{reportName}";

  public final GuestRequestFactory _guestRequestFactory;

  public AnswersTest(GuestRequestFactory req) {
    this._guestRequestFactory = req;
  }

  @Test
  @Tag(Category.PLASMO_TEST)
  @DisplayName("Test answer GET (by POST)")
  void testSingleRecordAnswer() throws JsonProcessingException {
    SearchConfig searchConfig = ReportUtil.createExonCountSearchConfig(_guestRequestFactory);
    StandardReportConfig reportConfig = ReportUtil.getStandardReportConfigOneRecord();
    DefaultReportRequestBody requestBody = new DefaultReportRequestBody(searchConfig);
    requestBody.setFormatConfig(reportConfig);
    Response response = _guestRequestFactory.jsonPayloadRequest(requestBody, HttpStatus.SC_OK,
        ContentType.JSON).when().post(REPORT_PATH, "", "standard");
    
    // minimally, confirm we got exactly one record
    List<RecordInstance> records = response.body().jsonPath().getList("records", RecordInstance.class);
    assertEquals(1, records.size(), "Expected exactly one record, but got " + records.size());

  }

}
