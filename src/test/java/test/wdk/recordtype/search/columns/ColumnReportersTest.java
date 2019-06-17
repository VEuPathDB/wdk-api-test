package test.wdk.recordtype.search.columns;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.gusdb.wdk.model.api.DefaultReportRequest;
import org.gusdb.wdk.model.api.SearchConfig;
import org.gusdb.wdk.model.api.StandardReportConfig;
import org.gusdb.wdk.model.api.record.search.column.reporter.Histogram;
import org.gusdb.wdk.model.api.record.search.reporter.JsonReporterResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import test.support.Category;
import test.support.util.ColumnUtil;
import test.support.util.GuestRequestFactory;
import test.support.util.ReportUtil;
import test.support.util.RequestFactory;
import test.wdk.TestBase;
import util.Json;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.function.Function;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.wdk.recordtype.ReportersTest.REPORT_PATH;

public class ColumnReportersTest extends TestBase {

  private final static String RECORD = "transcript";
  private final static String SEARCH = "GenesByExonCount";

  private final static String PATH = ColumnUtil.KEYED_URI + "/reporters/standard";

  private final RequestFactory rFac;

  public ColumnReportersTest(GuestRequestFactory rFac) {
    this.rFac = rFac;
  }

  @Nested
  public class SuperReporter {
    @Test
    @Tag(Category.PLASMO_TEST)
    @DisplayName("Test super column reporter on a number column")
    void numberColumn() {
      final var column = "exon_count";

      SearchConfig conf = ReportUtil.allExonCountSearch();

      conf.getParameters().put("num_exons_lte", "15");
      conf.getParameters().put("num_exons_gte", "14");

      JsonReporterResponse value = rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf, new StandardReportConfig()
          .addAttribute(column))
      )
        .when()
        .post(REPORT_PATH, RECORD, SEARCH, "standard")
        .as(JsonReporterResponse.class);

      Histogram<BigDecimal> report = Json.MAPPER.convertValue(
        rFac.jsonIoSuccessRequest(
          new DefaultReportRequest(conf, new StandardReportConfig()))
          .when()
          .post(PATH, RECORD, SEARCH, column)
          .as(JsonNode.class),
        new TypeReference<Histogram<BigDecimal>>(){}
      );

      compareHistogram(value, report, column, BigDecimal::new);
    }

    @Test
    @Tag(Category.PLASMO_TEST)
    @DisplayName("Test super column filter on a string column a pattern")
    void stringColumn() {
      final var column = "organism";

      SearchConfig conf = ReportUtil.allExonCountSearch();

      conf.getParameters().put("num_exons_lte", "15");
      conf.getParameters().put("num_exons_gte", "14");

      JsonReporterResponse value = rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf, new StandardReportConfig()
          .addAttribute(column))
      )
        .when()
        .post(REPORT_PATH, RECORD, SEARCH, "standard")
        .as(JsonReporterResponse.class);

      Histogram<String> report = Json.MAPPER.convertValue(
        rFac.jsonIoSuccessRequest(
          new DefaultReportRequest(conf, new StandardReportConfig()))
          .when()
          .post(PATH, RECORD, SEARCH, column)
          .as(JsonNode.class),
        new TypeReference<Histogram<String>>(){}
      );

      compareHistogram(value, report, column, Function.identity());
    }

    @Test
    @Tag(Category.CLINEPI_TEST)
    @DisplayName("Test super column filter on a date column with single values")
    void multiSingleValDate() {
      final var column = "EUPATH_0000091"; // Visit date
      final var conf1  = ReportUtil.prismObservationSearch();

      conf1.getParameters()
        .put("visit_date", Json.object()
          .put("min", "2013-07-01")
          .put("max", "2013-07-10")
          .toString());

      final var value = rFac.jsonIoSuccessRequest(
        new DefaultReportRequest(conf1, new StandardReportConfig()
          .addAttribute(column))
      )
        .when()
        .post(REPORT_PATH, "DS_0ad509829e_observation",
          "ClinicalVisitsByRelativeVisits_prism", "standard")
        .as(JsonReporterResponse.class);

      Histogram<LocalDateTime> report = Json.MAPPER.convertValue(
        rFac.jsonIoSuccessRequest(
          new DefaultReportRequest(conf1, new StandardReportConfig()))
          .when()
          .post(PATH, "DS_0ad509829e_observation",
            "ClinicalVisitsByRelativeVisits_prism", column)
          .as(JsonNode.class),
        new TypeReference<Histogram<LocalDateTime>>(){}
      );

      compareHistogram(value, report, column, s -> LocalDateTime.parse(
        s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")
      ));
    }
  }

  @SuppressWarnings("unchecked")
  private static <T> void compareHistogram(
    final JsonReporterResponse std,
    final Histogram<T>         his,
    final String               col,
    final Function<String, T>  fn
  ) {
    assertEquals(std.getRecords().size(), his.getTotalValues());

    var nullCount = 0L;
    var uniques   = new HashSet<T>();
    var min       = (T) null;
    var max       = (T) null;

    for (var rec : std.getRecords()) {
      var val = rec.getAttributes().get(col);

      if (val == null) {
        nullCount++;
        continue;
      }

      var dec = fn.apply(val);
      uniques.add(dec);

      if (his.hasMin() && dec instanceof Comparable)
        if (min == null || ((Comparable<T>) dec).compareTo(min) < 0)
          min = dec;

      if (his.hasMax() && dec instanceof Comparable)
        if (max == null || ((Comparable<T>) dec).compareTo(max) > 0)
          max = dec;
    }

    assertEquals(nullCount, his.getNullValues());
    assertEquals(uniques.size(), his.getUniqueValues());

    if (his.hasMin())
      assertEquals(min, his.getMinValue());
    if (his.hasMax())
      assertEquals(max, his.getMaxValue());
  }
}
