package test.support.util;

import java.util.HashMap;
import java.util.Map;

import org.gusdb.wdk.model.api.AnswerFormatting;
import org.gusdb.wdk.model.api.AnswerSpec;
import org.gusdb.wdk.model.api.DefaultJsonAnswerFormatConfig;
import org.gusdb.wdk.model.api.AnswerPagination;

import com.fasterxml.jackson.core.JsonProcessingException;

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
    AnswerSpec answerSpec = new AnswerSpec("GeneQuestions.GenesByExonCount");
    Map<String, String> paramsMap = new HashMap<String, String>();
    paramsMap.put("organism", "Plasmodium adleri G01");
    paramsMap.put("scope", "Gene");
    paramsMap.put("num_exons_gte", "6");
    paramsMap.put("num_exons_lte", "7");
    answerSpec.setParameters(paramsMap);
    return answerSpec;
  }

  public static AnswerSpec createBlastAnswerSpec(RequestFactory requestFactory) throws JsonProcessingException {
    AnswerSpec answerSpec = new AnswerSpec("GeneQuestions.GenesBySimilarity");
    Map<String, String> paramsMap = new HashMap<String, String>();
    paramsMap.put("organism", "Plasmodium adleri G01");
    paramsMap.put("scope", "Gene");
    paramsMap.put("Blast", "GCAGGAAATATGATTCCAGATAATGATAAAAATTCAAATTATAAATATCCAGCTGTTTATGATGACAAAGATAAAAAGTGTCATATATTATATATTGCAGCTCAAGAAAATAATGGTCCT");
    answerSpec.setParameters(paramsMap);
    return answerSpec;
  }

}
