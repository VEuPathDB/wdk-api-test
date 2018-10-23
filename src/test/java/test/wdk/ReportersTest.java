package test.wdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static test.support.Conf.SERVICE_PATH;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.AnswerSpec;
import org.gusdb.wdk.model.api.DefaultAnswerReportRequest;
import org.gusdb.wdk.model.api.DefaultJsonAnswerFormatConfig;
import org.gusdb.wdk.model.api.DefaultJsonAnswerFormatting;
import org.gusdb.wdk.model.api.GenomeViewInstance;
import org.gusdb.wdk.model.api.IsolateRecordInstance;
import org.gusdb.wdk.model.api.RecordInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import test.support.Category;
import test.support.util.AnswerUtil;
import test.support.util.GuestRequestFactory;

public class ReportersTest extends TestBase {
  public static final String BASE_PATH = SERVICE_PATH + "/answer/report";

  public final GuestRequestFactory _guestRequestFactory;

  public ReportersTest(GuestRequestFactory req) {
    this._guestRequestFactory = req;
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test Default Answer Reporter")
  void testDefaultJsonReporterSuccess() throws JsonProcessingException {
    
    // should return ? when the search is not BLAST 
    AnswerSpec answerSpec = AnswerUtil.createExonCountAnswerSpec(_guestRequestFactory);
    DefaultJsonAnswerFormatConfig formatConfig = AnswerUtil.getDefaultFormatConfigOneRecord();
    DefaultJsonAnswerFormatting formatting = new DefaultJsonAnswerFormatting("wdk-service-json", formatConfig);
    DefaultAnswerReportRequest  requestBody = new DefaultAnswerReportRequest(answerSpec, formatting);
    Response response = _guestRequestFactory.jsonPayloadRequest(requestBody, HttpStatus.SC_OK, ContentType.JSON)
        .when()
        .post(BASE_PATH);
    
    // minimally, confirm we got exactly one record
    List<RecordInstance> records = response.body().jsonPath().getList("records", RecordInstance.class);
    assertEquals(1, records.size(), "Expected exactly one record, but got " + records.size());
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test Blast Reporter Success")
  void testBlastReporterSuccess() throws JsonProcessingException {
    AnswerSpec answerSpec = AnswerUtil.createBlastAnswerSpec(_guestRequestFactory);
    DefaultJsonAnswerFormatConfig formatConfig = AnswerUtil.getDefaultFormatConfigOneRecord();
    DefaultJsonAnswerFormatting formatting = new DefaultJsonAnswerFormatting("blastSummaryView", formatConfig);
    DefaultAnswerReportRequest  requestBody = new DefaultAnswerReportRequest(answerSpec, formatting);
    Response response = _guestRequestFactory.jsonPayloadRequest(requestBody, HttpStatus.SC_OK, ContentType.JSON)
        .when()
        .post(BASE_PATH);
    assertNotNull(response.body().jsonPath().get("blastMeta"), "Missing 'blastMeta' object in request body");
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test Blast Reporter Failure")
  void testBlastReporterFailure() throws JsonProcessingException {
    
    // should return 400 when the search is not BLAST 
    AnswerSpec answerSpec = AnswerUtil.createExonCountAnswerSpec(_guestRequestFactory);
    DefaultJsonAnswerFormatConfig formatConfig = AnswerUtil.getDefaultFormatConfigOneRecord();
    DefaultJsonAnswerFormatting formatting = new DefaultJsonAnswerFormatting("blastSummaryView", formatConfig);
    DefaultAnswerReportRequest  requestBody = new DefaultAnswerReportRequest(answerSpec, formatting);
    _guestRequestFactory.jsonPayloadRequest(requestBody, HttpStatus.SC_BAD_REQUEST)
        .when()
        .post(BASE_PATH);
  }
  
  @Test
  @Tag(Category.PLASMO_TEST)
  @DisplayName("Test isolates summary view reporter")
  void testIsolatesSummaryView() throws JsonProcessingException {
    AnswerSpec answerSpec = AnswerUtil.createExonCountAnswerSpec(_guestRequestFactory);
    DefaultJsonAnswerFormatConfig formatConfig = AnswerUtil.getDefaultFormatConfigOneRecord();
    DefaultJsonAnswerFormatting formatting = new DefaultJsonAnswerFormatting("geoIsolateSummaryView", formatConfig);
    DefaultAnswerReportRequest  requestBody = new DefaultAnswerReportRequest(answerSpec, formatting);
    Response response = _guestRequestFactory.jsonPayloadRequest(requestBody, HttpStatus.SC_OK,
        ContentType.JSON).when().post(BASE_PATH);
    
    // parsing into IsolateRecordInstance validates the response contents, and confirm we got exactly one record
    List<IsolateRecordInstance> records = response.body().jsonPath().getList("records", IsolateRecordInstance.class);
    assertEquals(1, records.size(), "Expected exactly one record, but got " + records.size());

  }

  @Test
  @Tag(Category.PLASMO_TEST)
  @DisplayName("Test Gene Genome summary view reporter")
  void testGeneGenomeSummaryView() throws JsonProcessingException {
    AnswerSpec answerSpec = AnswerUtil.createExonCountAnswerSpec(_guestRequestFactory);
    DefaultJsonAnswerFormatConfig formatConfig = AnswerUtil.getDefaultFormatConfigOneRecord();
    DefaultJsonAnswerFormatting formatting = new DefaultJsonAnswerFormatting("geneGenomeSummaryView", formatConfig);
    DefaultAnswerReportRequest  requestBody = new DefaultAnswerReportRequest(answerSpec, formatting);
    Response response = _guestRequestFactory.jsonPayloadRequest(requestBody, HttpStatus.SC_OK,
        ContentType.TEXT).when().post(BASE_PATH);
    
    // parsing into IsolateRecordInstance validates the response contents
    response.body().as(GenomeViewInstance.class);
  }


}
