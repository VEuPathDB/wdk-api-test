package test.support.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.gusdb.wdk.model.api.AnswerFormatting;
import org.gusdb.wdk.model.api.AnswerPagination;
import org.gusdb.wdk.model.api.AnswerSpec;
import org.gusdb.wdk.model.api.DefaultJsonAnswerFormatConfig;

import java.util.HashMap;

public class AnswerUtil {

  public static AnswerFormatting getDefaultFormattingOneRecord() {
    AnswerFormatting formatting = new AnswerFormatting("wdk-service-json");
    formatting.setFormatConfig(getDefaultFormatConfigOneRecord());
    return formatting;
  }

  public static DefaultJsonAnswerFormatConfig getDefaultFormatConfigOneRecord() {
    AnswerPagination paging = new AnswerPagination();
    paging.setNumRecords(1);
    DefaultJsonAnswerFormatConfig formatConfig = new DefaultJsonAnswerFormatConfig();
    formatConfig.setPagination(paging);
    return formatConfig;
  }

  public static AnswerSpec createExonCountAnswerSpec(RequestFactory requestFactory) throws JsonProcessingException {
    AnswerSpec answerSpec = new AnswerSpec();
    var paramsMap = new HashMap<String, String>();
    paramsMap.put("organism", "Plasmodium adleri G01");
    paramsMap.put("scope", "Gene");
    // use counts that produce a small number of results
    paramsMap.put("num_exons_gte", "16");
    paramsMap.put("num_exons_lte", "17");
    answerSpec.setParameters(paramsMap);
    return answerSpec;
  }

  public static AnswerSpec createBlastAnswerSpec(RequestFactory requestFactory) throws JsonProcessingException {
    AnswerSpec answerSpec = new AnswerSpec(/*"GeneQuestions.GenesBySimilarity"*/);
    var paramsMap = new HashMap<String, String>();
    paramsMap.put("organism", "Plasmodium adleri G01");
    paramsMap.put("scope", "Gene");
    paramsMap.put("BlastDatabaseOrganism", "Plasmodium falciparum 3D7");
    paramsMap.put("BlastDatabaseType", "Transcripts");
    paramsMap.put("-e", "10");
    paramsMap.put("-b", "50");
    paramsMap.put("-filter", "no");
    paramsMap.put("BlastAlgorithm", "blastn");
    paramsMap.put("BlastQuerySequence", "GCAGGAAATATGATTCCAGATAATGATAAAAATTCAAATTATAAATATCCAGCTGTTTATGATGACAAAGATAAAAAGTGTCATATATTATATATTGCAGCTCAAGAAAATAATGGTCCT");
    answerSpec.setParameters(paramsMap);
    return answerSpec;
  }

  public static AnswerSpec createPopsetByCountryAnswerSpec(RequestFactory requestFactory) throws JsonProcessingException {
    AnswerSpec answerSpec = new AnswerSpec(/*"PopsetQuestions.PopsetByCountry"*/);
    var paramsMap = new HashMap<String, String>();
    paramsMap.put("country", "GAZ_00002560");
    answerSpec.setParameters(paramsMap);
    return answerSpec;
  }

}
