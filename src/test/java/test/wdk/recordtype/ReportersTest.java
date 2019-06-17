package test.wdk.recordtype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test.support.Category;
import test.support.util.GuestRequestFactory;
import test.support.util.ReportUtil;
import test.support.util.SearchUtil;
import test.wdk.TestBase;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReportersTest extends TestBase {
  public static final String REPORT_PATH = SearchUtil.KEYED_URI + "/reports/{reportName}";

  public final GuestRequestFactory _guestRequestFactory;

  public ReportersTest(GuestRequestFactory req) {
    this._guestRequestFactory = req;
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test Default Answer Reporter")
  void testDefaultJsonReporterSuccess() {

    SearchConfig searchConfig = ReportUtil.createValidExonCountSearchConfig();
    StandardReportConfig reportConfig = ReportUtil.getStandardReportConfigOneRecord();
    DefaultReportRequest  requestBody = new DefaultReportRequest(searchConfig, reportConfig);
    Response response = _guestRequestFactory.jsonPayloadRequest(requestBody, HttpStatus.SC_OK, ContentType.JSON)
        .when()
        .post(REPORT_PATH, "transcript", "GenesByExonCount", "standard");

    // minimally, confirm we got exactly one record
    List<RecordInstance> records = response.body().jsonPath().getList("records", RecordInstance.class);
    assertEquals(1, records.size(), "Expected exactly one record, but got " + records.size());
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test Blast Reporter Success")
  void testBlastReporterSuccess() throws JsonProcessingException {
    SearchConfig searchConfig = ReportUtil.createBlastSearchConfig();
    StandardReportConfig reportConfig = ReportUtil.getStandardReportConfigOneRecord();
    DefaultReportRequest  requestBody = new DefaultReportRequest(searchConfig, reportConfig);
    Response response = _guestRequestFactory.jsonPayloadRequest(requestBody, HttpStatus.SC_OK, ContentType.JSON)
        .when()
        .post(REPORT_PATH, "transcript", "GenesBySimilarity", "blastSummaryView");
    assertNotNull(response.body().jsonPath().get("blastMeta"), "Missing 'blastMeta' object in request body");
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test Blast Reporter Failure")
  void testBlastReporterFailure() {

    // should return 400 when the search is not BLAST
    SearchConfig searchConfig = ReportUtil.createValidExonCountSearchConfig();
    StandardReportConfig reportConfig = ReportUtil.getStandardReportConfigOneRecord();
    DefaultReportRequest  requestBody = new DefaultReportRequest(searchConfig, reportConfig);
    _guestRequestFactory.jsonPayloadRequest(requestBody, HttpStatus.SC_UNPROCESSABLE_ENTITY)
        .when()
        .post(REPORT_PATH, "transcript", "GenesBySimilarity", "blastSummaryView");
  }

  @Test
  @Tag(Category.PLASMO_TEST)
  @DisplayName("Test isolates summary view reporter")
  void testIsolatesSummaryView() throws IOException  {
    SearchConfig searchConfig = ReportUtil.createPopsetByCountryAnswerSpec();
    StandardReportConfig reportConfig = ReportUtil.getStandardReportConfigOneRecord();
    DefaultReportRequest  requestBody = new DefaultReportRequest(searchConfig, reportConfig);
    Response response = _guestRequestFactory.jsonPayloadRequest(requestBody, HttpStatus.SC_OK,
        ContentType.TEXT).when().post(REPORT_PATH, "popsetSequence", "PopsetByCountry", "geoIsolateSummaryView");

    // parsing into IsolateRecordInstance validates the response contents
    new ObjectMapper().readValue(response.body().asString(), IsolateViewInstance.class);

  }

  @Test
  @Tag(Category.PLASMO_TEST)
  @DisplayName("Test Gene Genome summary view reporter")
  void testGeneGenomeSummaryView() throws IOException {
    SearchConfig searchConfig = ReportUtil.createValidExonCountSearchConfig();
    StandardReportConfig reportConfig = ReportUtil.getStandardReportConfigOneRecord();
    DefaultReportRequest  requestBody = new DefaultReportRequest(searchConfig, reportConfig);
    Response response = _guestRequestFactory.jsonPayloadRequest(requestBody, HttpStatus.SC_OK,
        ContentType.TEXT).when().post(REPORT_PATH, "transcript", "GenesByExonCount", "geneGenomeSummaryView");

    // parsing into IsolateRecordInstance validates the response contents
    new ObjectMapper().readValue(response.body().asString(), GenomeViewInstance.class);
  }
}
