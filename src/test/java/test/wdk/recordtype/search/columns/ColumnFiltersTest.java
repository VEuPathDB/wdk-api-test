package test.wdk.recordtype.search.columns;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.gusdb.wdk.model.api.ColumnFilterConfig;
import org.gusdb.wdk.model.api.DefaultReportRequest;
import org.gusdb.wdk.model.api.SearchConfig;
import org.gusdb.wdk.model.api.StandardReportConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test.support.Category;
import test.support.util.GuestRequestFactory;
import test.support.util.ReportUtil;
import test.support.util.RequestFactory;
import test.wdk.TestBase;
import util.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.wdk.recordtype.ReportersTest.REPORT_PATH;

public class ColumnFiltersTest extends TestBase {

  private final RequestFactory rFac;

  public ColumnFiltersTest(GuestRequestFactory rFac) {
    this.rFac = rFac;
  }

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

      conf2.columnFilters = new ColumnFilterConfig() {{
        put("exon_count", new HashMap<>() {{
          put("standard", new ArrayList<>() {{
            add(new HashMap<>() {{
              put("comparator", "lte");
              put("value", 16);
            }});
            add(new HashMap<>() {{
              put("comparator", "gte");
              put("value", 14);
            }});
          }});
        }});
      }};

      assertEquals(rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf1, new StandardReportConfig()))
        .when()
        .post(REPORT_PATH, "transcript", "GenesByExonCount", "standard")
        .as(JsonNode.class), rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf2, new StandardReportConfig()))
        .when()
        .post(REPORT_PATH, "transcript", "GenesByExonCount", "standard")
        .as(JsonNode.class));
    }

    @Test
    @Tag(Category.PLASMO_TEST)
    @DisplayName("Test super column filter on a number column with a value range")
    void multiValNumber() {
      SearchConfig conf1 = ReportUtil.allExonCountSearch();
      SearchConfig conf2 = ReportUtil.allExonCountSearch();

      conf1.getParameters().put("num_exons_lte", "16");
      conf1.getParameters().put("num_exons_gte", "14");

      conf2.columnFilters = new ColumnFilterConfig() {{
        put("exon_count", new HashMap<>() {{
          put("standard", new ArrayList<>() {{
            add(new HashMap<>() {{
              put("min", 14);
              put("max", 16);
            }});
          }});
        }});
      }};

      assertEquals(rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf1, new StandardReportConfig()))
        .when()
        .post(REPORT_PATH, "transcript", "GenesByExonCount", "standard")
        .as(JsonNode.class), rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf2, new StandardReportConfig()))
        .when()
        .post(REPORT_PATH, "transcript", "GenesByExonCount", "standard")
        .as(JsonNode.class));
    }

    @Test
    @Tag(Category.PLASMO_TEST)
    @DisplayName("Test super column filter on a string column a pattern")
    void patternString() {
      SearchConfig conf1 = ReportUtil.allUserCommentSearch();
      SearchConfig conf2 = ReportUtil.allUserCommentSearch();
      Pattern patt = Pattern.compile("^2012-.*");

      ObjectNode full = rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf1,
          new StandardReportConfig().addAttribute("min_comment_date")))
        .when()
        .post(REPORT_PATH, "transcript", "GenesWithUserComments", "standard")
        .as(ObjectNode.class);

      int count = (int) StreamSupport.stream(full.get("records").spliterator(),
        false)
        .map(n -> n.get("attributes").get("min_comment_date").textValue())
        .map(patt::matcher)
        .filter(Matcher::matches)
        .count();

      conf2.columnFilters = new ColumnFilterConfig() {{
        put("min_comment_date", new HashMap<>() {{
          put("standard", new ArrayList<>() {{
            add(new HashMap<>() {{
              put("filter", "2012-*");
            }});
          }});
        }});
      }};

      assertEquals(count, rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf2,
          new StandardReportConfig().addAttribute("min_comment_date")))
        .when()
        .post(REPORT_PATH, "transcript", "GenesWithUserComments", "standard")
        .as(JsonNode.class)
        .get("meta")
        .get("totalCount")
        .intValue());
    }

    @Test
    @Tag(Category.PLASMO_TEST)
    @DisplayName("Test super column filter on a string column a value set")
    void stringSet() {
      SearchConfig conf1 = ReportUtil.allUserCommentSearch();
      SearchConfig conf2 = ReportUtil.allUserCommentSearch();
      String[] values = { "2010-09-15 09:41:09", "2008-12-02 21:49:26",
        "2018-04-04 22:29:28", };

      ObjectNode full = rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf1,
          new StandardReportConfig().addAttribute("min_comment_date")))
        .when()
        .post(REPORT_PATH, "transcript", "GenesWithUserComments", "standard")
        .as(ObjectNode.class);

      int count = (int) StreamSupport.stream(full.get("records").spliterator(),
        false)
        .map(n -> n.get("attributes").get("min_comment_date").textValue())
        .filter(s -> {
          for (String v : values)
            if (v.equals(s))
              return true;
          return false;
        })
        .count();

      conf2.columnFilters = new ColumnFilterConfig() {{
        put("min_comment_date", new HashMap<>() {{
          put("standard", new ArrayList<>() {{
            add(new HashMap<>() {{
              put("filters", values);
            }});
          }});
        }});
      }};

      assertEquals(count, rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf2,
          new StandardReportConfig().addAttribute("min_comment_date")))
        .when()
        .post(REPORT_PATH, "transcript", "GenesWithUserComments", "standard")
        .as(JsonNode.class)
        .get("meta")
        .get("totalCount")
        .intValue());
    }

    @Test
    @Tag(Category.CLINEPI_TEST)
    @DisplayName("Test super column filter on a date column with single values")
    void multiSingleValDate() {
      SearchConfig conf1 = ReportUtil.prismObservationSearch();
      SearchConfig conf2 = ReportUtil.prismObservationSearch();

      conf1.getParameters()
        .put("visit_date", Json.object()
          .put("min", "2013-07-01")
          .put("max", "2015-07-01")
          .toString());

      conf2.columnFilters = new ColumnFilterConfig() {{
        put("EUPATH_0000091", new HashMap<>() {{
          put("standard", new ArrayList<>() {{
            add(new HashMap<>() {{
              put("comparator", "gte");
              put("value", "2013-07-01T00:00:00");
            }});
            add(new HashMap<>() {{
              put("comparator", "lte");
              put("value", "2015-07-01T00:00:00");
            }});
          }});
        }});
      }};

      assertEquals(rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf1, new StandardReportConfig()))
        .when()
        .post(REPORT_PATH, "DS_0ad509829e_observation",
          "ClinicalVisitsByRelativeVisits_prism", "standard")
        .as(JsonNode.class), rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf2, new StandardReportConfig()))
        .when()
        .post(REPORT_PATH, "DS_0ad509829e_observation",
          "ClinicalVisitsByRelativeVisits_prism", "standard")
        .as(JsonNode.class));
    }

    @Test
    @Tag(Category.CLINEPI_TEST)
    @DisplayName("Test super column filter on a date column with a value range")
    void multiValDate() {
      SearchConfig conf1 = ReportUtil.prismObservationSearch();
      SearchConfig conf2 = ReportUtil.prismObservationSearch();

      conf1.getParameters()
        .put("visit_date", Json.object()
          .put("min", "2013-07-01")
          .put("max", "2015-07-01")
          .toString());

      conf2.columnFilters = new ColumnFilterConfig() {{
        put("EUPATH_0000091", new HashMap<>() {{
          put("standard", new ArrayList<>() {{
            add(new HashMap<>() {{
              put("min", "2013-07-01T00:00:00");
              put("max", "2015-07-01T00:00:00");
            }});
          }});
        }});
      }};

      assertEquals(rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf1, new StandardReportConfig()))
        .when()
        .post(REPORT_PATH, "DS_0ad509829e_observation",
          "ClinicalVisitsByRelativeVisits_prism", "standard")
        .as(JsonNode.class), rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf2, new StandardReportConfig()))
        .when()
        .post(REPORT_PATH, "DS_0ad509829e_observation",
          "ClinicalVisitsByRelativeVisits_prism", "standard")
        .as(JsonNode.class));
    }
  }
}
