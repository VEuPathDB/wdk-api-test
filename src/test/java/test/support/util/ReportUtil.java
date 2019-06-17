package test.support.util;

import java.util.HashMap;
import java.util.Map;

import org.gusdb.wdk.model.api.ReportPagination;
import org.gusdb.wdk.model.api.SearchConfig;
import org.gusdb.wdk.model.api.StandardReportConfig;

import util.Json;

public class ReportUtil {

  public static StandardReportConfig getStandardReportConfigOneRecord() {
    ReportPagination paging = new ReportPagination();
    paging.setNumRecords(1);
    StandardReportConfig searchConfig = new StandardReportConfig();
    searchConfig.setPagination(paging);
    return searchConfig;
  }

  public static SearchConfig createValidExonCountSearchConfig() {
    Map<String, String> paramsMap = new HashMap<>();
    paramsMap.put("organism", "Plasmodium adleri G01");
    paramsMap.put("scope", "Gene");
    // use counts that produce a small number of results
    paramsMap.put("num_exons_gte", "16");
    paramsMap.put("num_exons_lte", "17");
    SearchConfig searchConfig = new SearchConfig();
    searchConfig.setParameters(paramsMap);
    return searchConfig;
  }

  public static SearchConfig allExonCountSearch() {
    return new SearchConfig()
      .setParameters(new HashMap<>(){{
        put("organism", "Plasmodium adleri G01");
        put("scope", "Transcript");
        put("num_exons_gte", "0");
        put("num_exons_lte", "50");
      }});
  }

  public static SearchConfig prismObservationSearch() {
    String emptyFilters = Json.object()
      .set("filters", Json.array())
      .toString();
    return new SearchConfig()
      .setParameters(new HashMap<>() {{
        put("geographic_region_prism", Json.object()
          .set("filters", Json.array()
            .add(Json.object()
              .put("field", "EUPATH_0000054")
              .put("type", "string")
              .put("isRange", false)
              .put("includeUnknown", false)
              .put("fieldDisplayName", "Sub-county in Uganda")))
          .toString());
        put("visit_date", Json.object()
          .put("min", "2011-07-01")
          .put("max", "2017-07-31")
          .toString());
        put("duration_observation", "1");
        put("visits_visitage_metadata_prism", emptyFilters);
        put("participants_prism", emptyFilters);
        put("households_prism", emptyFilters);
        put("visits_prism", emptyFilters);
        put("use_relative_visits", "No");
        put("days_between", Json.object()
          .put("min", "0")
          .put("max", "10")
          .toString());
        put("date_direction_fv", "before");
        put("dateOperator_fv", "remove");
        put("relative_visits_prism", emptyFilters);
        put("tbl_prefix", "D0ad509829e");
      }});
  }

  public static SearchConfig allUserCommentSearch() {
    return new SearchConfig().setParameters(new HashMap<>());
  }

  public static SearchConfig createInvalidExonCountSearchConfig() {
    Map<String, String> paramsMap = new HashMap<>();
    paramsMap.put("SILLY", "Plasmodium adleri G01");
    SearchConfig searchConfig = new SearchConfig();
    searchConfig.setParameters(paramsMap);
    return searchConfig;
  }

  public static SearchConfig createBlastSearchConfig() {
    SearchConfig searchConfig = new SearchConfig();
    Map<String, String> paramsMap = new HashMap<>();
    paramsMap.put("scope", "Gene");
    paramsMap.put("BlastRecordClass", "TranscriptRecordClasses.TranscriptRecordClass");
    paramsMap.put("BlastDatabaseOrganism", "Plasmodium adleri G01");
    paramsMap.put("BlastDatabaseType", "Transcripts");
    paramsMap.put("-e", "10");
    paramsMap.put("-b", "50");
    paramsMap.put("-filter", "no");
    paramsMap.put("BlastAlgorithm", "blastn");
    paramsMap.put("BlastQuerySequence", "GCAGGAAATATGATTCCAGATAATGATAAAAATTCAAATTATAAATATCCAGCTGTTTATGATGACAAAGATAAAAAGTGTCATATATTATATATTGCAGCTCAAGAAAATAATGGTCCT");
    searchConfig.setParameters(paramsMap);
    return searchConfig;
  }

  public static SearchConfig createPopsetByCountryAnswerSpec() {
    SearchConfig searchConfig = new SearchConfig();
    Map<String, String> paramsMap = new HashMap<String, String>();
    paramsMap.put("country", "GAZ_00002560");
    searchConfig.setParameters(paramsMap);
    return searchConfig;
  }

}
