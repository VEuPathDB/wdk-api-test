package test.wdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.DefaultAnswerReportRequest;
import org.gusdb.wdk.model.api.GenomeViewInstance;
import org.gusdb.wdk.model.api.IsolateViewInstance;
import org.gusdb.wdk.model.api.RecordInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test.support.Category;
import test.support.util.AnswerUtil;
import test.support.util.GuestRequestFactory;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static test.support.Conf.SERVICE_PATH;

public class ReportersTest extends TestBase {
  public static final String
    BASE_PATH = SERVICE_PATH + "/record-types/{recordType}/searches/{search}/reports",
    BY_NAME_PATH = BASE_PATH + "/{reporter}";

  public final GuestRequestFactory _guestRequestFactory;

  public ReportersTest(GuestRequestFactory req) {
    _guestRequestFactory = req;
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test Default Answer Reporter")
  void testDefaultJsonReporterSuccess() throws JsonProcessingException {

    // should return ? when the search is not BLAST
    var request = new DefaultAnswerReportRequest(
      AnswerUtil.createExonCountAnswerSpec(_guestRequestFactory),
      AnswerUtil.getDefaultFormatConfigOneRecord());

    var response = _guestRequestFactory
      .jsonPayloadRequest(request, HttpStatus.SC_OK, ContentType.JSON)
      .when()
      .post(BY_NAME_PATH, "transcript", "GenesByExonCount", "standard");

    // minimally, confirm we got exactly one record
    List<RecordInstance> records = response.body()
      .jsonPath()
      .getList("records", RecordInstance.class);

    assertEquals(1, records.size(), "Expected exactly one record, but got " + records.size());
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test Old Blast Reporter Success")
  void testBlastReporterSuccess() throws JsonProcessingException {

    var request = new DefaultAnswerReportRequest(
      AnswerUtil.createBlastAnswerSpec(_guestRequestFactory),
      AnswerUtil.getBlastReporterFormatting());

    var response = _guestRequestFactory
      .jsonPayloadRequest(request, HttpStatus.SC_OK, ContentType.JSON)
      .when()
      .post(BY_NAME_PATH, "transcript", "GenesBySimilarity", "blastSummaryView");

    assertNotNull(
      response.body().jsonPath().get("blastMeta"),
      "Missing 'blastMeta' object in request body");
  }

 @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test MultiBlast Reporter Success")
  void testMultiBlastReporterSuccess() throws JsonProcessingException {

    var request = new DefaultAnswerReportRequest(
      AnswerUtil.createMultiBlastAnswerSpec(_guestRequestFactory),
      AnswerUtil.getBlastReporterFormatting());

    var response = _guestRequestFactory
      .jsonPayloadRequest(request, HttpStatus.SC_OK, ContentType.JSON)
      .when()
      .post(BY_NAME_PATH, "transcript", "GenesByMultiBlast", "blastSummaryView");

    assertNotNull(
      response.body().jsonPath().get("blastMeta"),
      "Missing 'blastMeta' object in request body");
  }


  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test Blast Reporter Failure")
  void testBlastReporterFailure() throws JsonProcessingException {

    // should return 400 when the search is not BLAST
    var request = new DefaultAnswerReportRequest(
      AnswerUtil.createExonCountAnswerSpec(_guestRequestFactory),
      AnswerUtil.getDefaultFormatConfigOneRecord());

    _guestRequestFactory.jsonPayloadRequest(request, HttpStatus.SC_UNPROCESSABLE_ENTITY)
      .when()
      .post(BY_NAME_PATH, "transcript", "GenesByExonCount", "blastSummaryView");
  }

  @Test
  @Tag(Category.PLASMO_TEST)
  @DisplayName("Test isolates summary view reporter")
  void testIsolatesSummaryView() throws IOException  {

    var requestBody = new DefaultAnswerReportRequest(
      AnswerUtil.createPopsetByCountryAnswerSpec(_guestRequestFactory),
      AnswerUtil.getDefaultFormatConfigOneRecord());

    var response = _guestRequestFactory
      .jsonPayloadRequest(requestBody, HttpStatus.SC_OK, ContentType.TEXT)
      .when()
      .post(BY_NAME_PATH, "popsetSequence", "PopsetByCountry", "geoIsolateSummaryView");

    // parsing into IsolateRecordInstance validates the response contents
    getMapper().readValue(response.body().asString(), IsolateViewInstance.class);
  }

  @Test
  @Tag(Category.PLASMO_TEST)
  @DisplayName("Test Gene Genome summary view reporter")
  void testGeneGenomeSummaryView() throws IOException {

    var requestBody = new DefaultAnswerReportRequest(
      AnswerUtil.createExonCountAnswerSpec(_guestRequestFactory),
      AnswerUtil.getDefaultFormatConfigOneRecord());

    var response = _guestRequestFactory
      .jsonPayloadRequest(requestBody, HttpStatus.SC_OK, ContentType.TEXT)
      .when()
      .post(BY_NAME_PATH, "transcript", "GenesByExonCount", "geneGenomeSummaryView");

    // parsing into IsolateRecordInstance validates the response contents
    getMapper().readValue(response.body().asString(), GenomeViewInstance.class);
  }
}
