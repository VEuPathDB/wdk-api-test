package test.wdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static test.support.Conf.SERVICE_PATH;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.DefaultAnswerReportRequest;
import org.gusdb.wdk.model.api.GenomeViewInstance;
import org.gusdb.wdk.model.api.IsolateViewInstance;
import org.gusdb.wdk.model.api.RecordInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import test.support.Category;
import test.support.util.AnswerUtil;
import test.support.util.Session;
import test.support.util.SessionFactory;

public class ReportersTest extends TestBase {
  public static final String
    BASE_PATH = SERVICE_PATH + "/record-types/{recordType}/searches/{search}/reports",
    BY_NAME_PATH = BASE_PATH + "/{reporter}";

  public final Session _session;

  public ReportersTest(SessionFactory sessionFactory) {
    _session = sessionFactory.getCachedGuestSession();
  }

  @Test
  @Tag (Category.PLASMO_TEST)
  @DisplayName("Test Default Answer Reporter")
  void testDefaultJsonReporterSuccess() {

    // should return ? when the search is not BLAST
    var request = new DefaultAnswerReportRequest(
      AnswerUtil.createExonCountAnswerSpec(),
      AnswerUtil.getDefaultFormatConfigOneRecord());

    var response = _session
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
  void testBlastReporterSuccess() {

    var request = new DefaultAnswerReportRequest(
      AnswerUtil.createBlastAnswerSpec(),
      AnswerUtil.getBlastReporterFormatting());

    var response = _session
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
  void testMultiBlastReporterSuccess() {

    var request = new DefaultAnswerReportRequest(
      AnswerUtil.createMultiBlastAnswerSpec(),
      AnswerUtil.getBlastReporterFormatting());

    var response = _session
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
  void testBlastReporterFailure() {

    // should return 400 when the search is not BLAST
    var request = new DefaultAnswerReportRequest(
      AnswerUtil.createExonCountAnswerSpec(),
      AnswerUtil.getDefaultFormatConfigOneRecord());

    _session.jsonPayloadRequest(request, HttpStatus.SC_UNPROCESSABLE_ENTITY)
      .when()
      .post(BY_NAME_PATH, "transcript", "GenesByExonCount", "blastSummaryView");
  }

  @Test
  @Tag(Category.PLASMO_TEST)
  @DisplayName("Test isolates summary view reporter")
  void testIsolatesSummaryView() throws IOException  {

    var requestBody = new DefaultAnswerReportRequest(
      AnswerUtil.createPopsetByCountryAnswerSpec(),
      AnswerUtil.getDefaultFormatConfigOneRecord());

    var response = _session
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
      AnswerUtil.createExonCountAnswerSpec(),
      AnswerUtil.getDefaultFormatConfigOneRecord());

    var response = _session
      .jsonPayloadRequest(requestBody, HttpStatus.SC_OK, ContentType.TEXT)
      .when()
      .post(BY_NAME_PATH, "transcript", "GenesByExonCount", "geneGenomeSummaryView");

    // parsing into IsolateRecordInstance validates the response contents
    getMapper().readValue(response.body().asString(), GenomeViewInstance.class);
  }
}
