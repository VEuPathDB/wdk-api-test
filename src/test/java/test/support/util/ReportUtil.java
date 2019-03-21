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
    
  public static SearchConfig createValidExonCountSearchConfig(RequestFactory requestFactory) throws JsonProcessingException {
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
  
  public static SearchConfig createInvalidExonCountSearchConfig(RequestFactory requestFactory) throws JsonProcessingException {
    Map<String, String> paramsMap = new HashMap<String, String>();
    paramsMap.put("SILLY", "Plasmodium adleri G01");
    SearchConfig searchConfig = new SearchConfig();
    searchConfig.setParameters(paramsMap);
    return searchConfig;
  }

  public static SearchConfig createBlastSearchConfig(RequestFactory requestFactory) throws JsonProcessingException {
    SearchConfig searchConfig = new SearchConfig();
    Map<String, String> paramsMap = new HashMap<String, String>();
    paramsMap.put("organism", "Plasmodium adleri G01");
    paramsMap.put("scope", "Gene");
    paramsMap.put("BlastRecordClass", "TranscriptRecordClasses.TranscriptRecordClass");
    paramsMap.put("BlastDatabaseOrganism", "Plasmodium falciparum 3D7");
    paramsMap.put("BlastDatabaseType", "Transcripts");
    paramsMap.put("-e", "10");
    paramsMap.put("-b", "50");
    paramsMap.put("-filter", "no");
    paramsMap.put("BlastAlgorithm", "blastn");
    paramsMap.put("BlastQuerySequence", "GCAGGAAATATGATTCCAGATAATGATAAAAATTCAAATTATAAATATCCAGCTGTTTATGATGACAAAGATAAAAAGTGTCATATATTATATATTGCAGCTCAAGAAAATAATGGTCCT");
    searchConfig.setParameters(paramsMap);
    return searchConfig;
  }

  public static SearchConfig createPopsetByCountryAnswerSpec(RequestFactory requestFactory) throws JsonProcessingException {
    SearchConfig searchConfig = new SearchConfig();
    Map<String, String> paramsMap = new HashMap<String, String>();
    paramsMap.put("country", "GAZ_00002560");
    searchConfig.setParameters(paramsMap);
    return searchConfig;
  }
  
  // this is a transform.  not allowed to have non-null step param
  public static SearchConfig createValidOrthologsSearchConfig(RequestFactory requestFactory) throws JsonProcessingException {
    Map<String, String> paramsMap = new HashMap<String, String>();
    paramsMap.put("organism", "Plasmodium adleri G01");
    SearchConfig searchConfig = new SearchConfig();
    searchConfig.setParameters(paramsMap);
    return searchConfig;
  }

  // this is a transform.  not allowed to have non-null step param
  public static SearchConfig createInvalidOrthologsSearchConfig(RequestFactory requestFactory, Long leafStepId) throws JsonProcessingException {
    SearchConfig searchConfig = createValidOrthologsSearchConfig(requestFactory);
    searchConfig.getParameters().put("gene_result", leafStepId.toString()); // naughty naughty
    return searchConfig;
  }

  public static SearchConfig createValidBooleanSearchConfig(RequestFactory requestFactory) throws JsonProcessingException {
    Map<String, String> paramsMap = new HashMap<String, String>();
    paramsMap.put("Operator", "INTERSECT");
    SearchConfig searchConfig = new SearchConfig();
    searchConfig.setParameters(paramsMap);
    return searchConfig;
  }

}
