package test.wdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static test.support.Conf.SERVICE_PATH;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.SearchConfig;
import org.gusdb.wdk.model.api.DefaultReportRequest;
import org.gusdb.wdk.model.api.StandardReportConfig;
import org.gusdb.wdk.model.api.GenomeViewInstance;
import org.gusdb.wdk.model.api.IsolateViewInstance;
import org.gusdb.wdk.model.api.RecordInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import test.support.Category;
import test.support.util.ReportUtil;
import test.support.util.GuestRequestFactory;

public class ReportersTest extends TestBase {
  public static final String REPORT_PATH = SERVICE_PATH + "/record-types/{record-type}/searches/{searchName}/reports/{reportName}";

  public final GuestRequestFactory _guestRequestFactory;

  public ReportersTest(GuestRequestFactory req) {
    this._guestRequestFactory = req;
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test Default Answer Reporter")
  void testDefaultJsonReporterSuccess() throws JsonProcessingException {
    
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
  void testBlastReporterFailure() throws JsonProcessingException {
    
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
  void testIsolatesSummaryView() throws JsonProcessingException, IOException  {
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
  void testGeneGenomeSummaryView() throws JsonProcessingException, IOException { 
    SearchConfig searchConfig = ReportUtil.createValidExonCountSearchConfig();
    StandardReportConfig reportConfig = ReportUtil.getStandardReportConfigOneRecord();
    DefaultReportRequest  requestBody = new DefaultReportRequest(searchConfig, reportConfig);
    Response response = _guestRequestFactory.jsonPayloadRequest(requestBody, HttpStatus.SC_OK,
        ContentType.TEXT).when().post(REPORT_PATH, "transcript", "GenesByExonCount", "geneGenomeSummaryView");
    
    // parsing into IsolateRecordInstance validates the response contents
    new ObjectMapper().readValue(response.body().asString(), GenomeViewInstance.class);
  }


}
