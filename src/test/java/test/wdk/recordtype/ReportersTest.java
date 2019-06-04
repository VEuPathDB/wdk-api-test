package test.wdk.recordtype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.*;
import org.junit.jupiter.api.*;
import test.support.Category;
import test.support.util.GuestRequestFactory;
import test.support.util.ReportUtil;
import test.support.util.SearchUtil;
import test.wdk.TestBase;
import util.Json;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

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

  @Nested
  public class ColumnFilters {

    @Nested
    public class SuperFilter {
      @Test
      @Tag(Category.PLASMO_TEST)
      @DisplayName("Test super column filter on a number column with single values")
      void multiSingleValNumber() {
        SearchConfig conf1 = ReportUtil.allExonCountSearch();
        SearchConfig conf2 = ReportUtil.allExonCountSearch();

        conf1.getParameters().put("num_exons_lte", "16");
        conf1.getParameters().put("num_exons_gte", "14");

        conf2.columnFilters = new ColumnFilterConfig(){{
          put("exon_count", new HashMap<>(){{
            put("standard", new ArrayList<>(){{
              add(new HashMap<>(){{
                put("comparator", "lte");
                put("value", 16);
              }});
              add(new HashMap<>(){{
                put("comparator", "gte");
                put("value", 14);
              }});
            }});
          }});
        }};

        assertEquals(
          _guestRequestFactory.jsonIoSuccessRequest(new DefaultReportRequest(conf1, new StandardReportConfig()))
            .when()
            .post(REPORT_PATH, "transcript", "GenesByExonCount", "standard")
            .as(JsonNode.class),
          _guestRequestFactory.jsonIoSuccessRequest(new DefaultReportRequest(conf2, new StandardReportConfig()))
            .when()
            .post(REPORT_PATH, "transcript", "GenesByExonCount", "standard")
            .as(JsonNode.class)
        );
      }

      @Test
      @Tag(Category.PLASMO_TEST)
      @DisplayName("Test super column filter on a number column with a value range")
      void multiValNumber() {
        SearchConfig conf1 = ReportUtil.allExonCountSearch();
        SearchConfig conf2 = ReportUtil.allExonCountSearch();

        conf1.getParameters().put("num_exons_lte", "16");
        conf1.getParameters().put("num_exons_gte", "14");

        conf2.columnFilters = new ColumnFilterConfig(){{
          put("exon_count", new HashMap<>(){{
            put("standard", new ArrayList<>(){{
              add(new HashMap<>(){{
                put("min", 14);
                put("max", 16);
              }});
            }});
          }});
        }};

        assertEquals(
          _guestRequestFactory.jsonIoSuccessRequest(new DefaultReportRequest(conf1, new StandardReportConfig()))
            .when()
            .post(REPORT_PATH, "transcript", "GenesByExonCount", "standard")
            .as(JsonNode.class),
          _guestRequestFactory.jsonIoSuccessRequest(new DefaultReportRequest(conf2, new StandardReportConfig()))
            .when()
            .post(REPORT_PATH, "transcript", "GenesByExonCount", "standard")
            .as(JsonNode.class)
        );
      }

      @Test
      @Tag(Category.PLASMO_TEST)
      @DisplayName("Test super column filter on a string column a pattern")
      void patternString() {
        SearchConfig conf1 = ReportUtil.allUserCommentSearch();
        SearchConfig conf2 = ReportUtil.allUserCommentSearch();
        Pattern patt = Pattern.compile("^2012-.*");

        ObjectNode full = _guestRequestFactory.jsonIoSuccessRequest(
          new DefaultReportRequest(conf1, new StandardReportConfig()
            .addAttribute("min_comment_date")))
          .when()
          .post(REPORT_PATH, "transcript", "GenesWithUserComments", "standard")
          .as(ObjectNode.class);

        int count = (int) StreamSupport.stream(full.get("records").spliterator(), false)
          .map(n -> n.get("attributes").get("min_comment_date").textValue())
          .map(patt::matcher)
          .filter(Matcher::matches)
          .count();


        conf2.columnFilters = new ColumnFilterConfig(){{
          put("min_comment_date", new HashMap<>(){{
            put("standard", new ArrayList<>(){{
              add(new HashMap<>(){{
                put("filter", "2012-*");
              }});
            }});
          }});
        }};

        assertEquals(
          count,
          _guestRequestFactory.jsonIoSuccessRequest(new DefaultReportRequest(conf2, new StandardReportConfig().addAttribute("min_comment_date")))
            .when()
            .post(REPORT_PATH, "transcript", "GenesWithUserComments", "standard")
            .as(JsonNode.class)
            .get("meta")
            .get("totalCount")
            .intValue()
        );
      }

      @Test
      @Tag(Category.PLASMO_TEST)
      @DisplayName("Test super column filter on a string column a value set")
      void stringSet() {
        SearchConfig conf1 = ReportUtil.allUserCommentSearch();
        SearchConfig conf2 = ReportUtil.allUserCommentSearch();
        String[] values = {
          "2010-09-15 09:41:09",
          "2008-12-02 21:49:26",
          "2018-04-04 22:29:28",
        };


        ObjectNode full = _guestRequestFactory.jsonIoSuccessRequest(
          new DefaultReportRequest(conf1, new StandardReportConfig()
            .addAttribute("min_comment_date")))
          .when()
          .post(REPORT_PATH, "transcript", "GenesWithUserComments", "standard")
          .as(ObjectNode.class);

        int count = (int) StreamSupport.stream(full.get("records").spliterator(), false)
          .map(n -> n.get("attributes").get("min_comment_date").textValue())
          .filter(s -> {
            for (String v : values)
              if (v.equals(s))
                return true;
            return false;
          })
          .count();


        conf2.columnFilters = new ColumnFilterConfig(){{
          put("min_comment_date", new HashMap<>(){{
            put("standard", new ArrayList<>(){{
              add(new HashMap<>(){{
                put("filters", values);
              }});
            }});
          }});
        }};

        assertEquals(
          count,
          _guestRequestFactory.jsonIoSuccessRequest(new DefaultReportRequest(conf2, new StandardReportConfig().addAttribute("min_comment_date")))
            .when()
            .post(REPORT_PATH, "transcript", "GenesWithUserComments", "standard")
            .as(JsonNode.class)
            .get("meta")
            .get("totalCount")
            .intValue()
        );
      }

      @Test
      @Tag(Category.CLINEPI_TEST)
      @DisplayName("Test super column filter on a date column with single values")
      void multiSingleValDate() {
        SearchConfig conf1 = ReportUtil.prismObservationSearch();
        SearchConfig conf2 = ReportUtil.prismObservationSearch();

        conf1.getParameters().put("visit_date", Json.object()
          .put("min", "2013-07-01")
          .put("max", "2015-07-01")
          .toString());

        conf2.columnFilters = new ColumnFilterConfig(){{
          put("EUPATH_0000091", new HashMap<>(){{
            put("standard", new ArrayList<>(){{
              add(new HashMap<>(){{
                put("comparator", "gte");
                put("value", "2013-07-01T00:00:00");
              }});
              add(new HashMap<>(){{
                put("comparator", "lte");
                put("value", "2015-07-01T00:00:00");
              }});
            }});
          }});
        }};

        assertEquals(
          _guestRequestFactory.jsonIoSuccessRequest(new DefaultReportRequest(conf1, new StandardReportConfig()))
            .when()
            .post(REPORT_PATH, "DS_0ad509829e_observation",
              "ClinicalVisitsByRelativeVisits_prism", "standard")
            .as(JsonNode.class),
          _guestRequestFactory.jsonIoSuccessRequest(new DefaultReportRequest(conf2, new StandardReportConfig()))
            .when()
            .post(REPORT_PATH, "DS_0ad509829e_observation",
              "ClinicalVisitsByRelativeVisits_prism", "standard")
            .as(JsonNode.class)
        );
      }

      @Test
      @Tag(Category.CLINEPI_TEST)
      @DisplayName("Test super column filter on a date column with a value range")
      void multiValDate() {
        SearchConfig conf1 = ReportUtil.prismObservationSearch();
        SearchConfig conf2 = ReportUtil.prismObservationSearch();

        conf1.getParameters().put("visit_date", Json.object()
          .put("min", "2013-07-01")
          .put("max", "2015-07-01")
          .toString());

        conf2.columnFilters = new ColumnFilterConfig(){{
          put("EUPATH_0000091", new HashMap<>(){{
            put("standard", new ArrayList<>(){{
              add(new HashMap<>(){{
                put("min", "2013-07-01T00:00:00");
                put("max", "2015-07-01T00:00:00");
              }});
            }});
          }});
        }};

        assertEquals(
          _guestRequestFactory.jsonIoSuccessRequest(new DefaultReportRequest(conf1, new StandardReportConfig()))
            .when()
            .post(REPORT_PATH, "DS_0ad509829e_observation",
              "ClinicalVisitsByRelativeVisits_prism", "standard")
            .as(JsonNode.class),
          _guestRequestFactory.jsonIoSuccessRequest(new DefaultReportRequest(conf2, new StandardReportConfig()))
            .when()
            .post(REPORT_PATH, "DS_0ad509829e_observation",
              "ClinicalVisitsByRelativeVisits_prism", "standard")
            .as(JsonNode.class)
        );
      }
    }
  }
}
