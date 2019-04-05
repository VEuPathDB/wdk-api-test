package test.support.util;

import java.util.HashMap;
import java.util.Map;

import org.gusdb.wdk.model.api.SearchConfig;
import org.gusdb.wdk.model.api.StandardReportConfig;
import org.gusdb.wdk.model.api.ReportPagination;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ReportUtil {
  
  public static StandardReportConfig getStandardReportConfigOneRecord() {
    ReportPagination paging = new ReportPagination();
    paging.setNumRecords(1);
    StandardReportConfig searchConfig = new StandardReportConfig();
    searchConfig.setPagination(paging);
    return searchConfig;
  }
    
  public static SearchConfig createValidExonCountSearchConfig() throws JsonProcessingException {
    Map<String, String> paramsMap = new HashMap<String, String>();
    paramsMap.put("organism", "Plasmodium adleri G01");
    paramsMap.put("scope", "Gene");
    // use counts that produce a small number of results
    paramsMap.put("num_exons_gte", "16");
    paramsMap.put("num_exons_lte", "17");
    SearchConfig searchConfig = new SearchConfig();
    searchConfig.setParameters(paramsMap);
    return searchConfig;
  }
  
  public static SearchConfig createInvalidExonCountSearchConfig() throws JsonProcessingException {
    Map<String, String> paramsMap = new HashMap<String, String>();
    paramsMap.put("SILLY", "Plasmodium adleri G01");
    SearchConfig searchConfig = new SearchConfig();
    searchConfig.setParameters(paramsMap);
    return searchConfig;
  }

  public static SearchConfig createBlastSearchConfig() throws JsonProcessingException {
    SearchConfig searchConfig = new SearchConfig();
    Map<String, String> paramsMap = new HashMap<String, String>();
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

  public static SearchConfig createPopsetByCountryAnswerSpec() throws JsonProcessingException {
    SearchConfig searchConfig = new SearchConfig();
    Map<String, String> paramsMap = new HashMap<String, String>();
    paramsMap.put("country", "GAZ_00002560");
    searchConfig.setParameters(paramsMap);
    return searchConfig;
  }
  
}
