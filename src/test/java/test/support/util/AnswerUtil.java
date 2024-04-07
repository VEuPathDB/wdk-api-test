package test.support.util;

import java.util.Arrays;
import java.util.HashMap;

import org.gusdb.wdk.model.api.AnswerFormatting;
import org.gusdb.wdk.model.api.AnswerPagination;
import org.gusdb.wdk.model.api.AnswerSpec;
import org.gusdb.wdk.model.api.DefaultJsonAnswerFormatConfig;

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

  public static AnswerSpec createExonCountAnswerSpec() {
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

  public static AnswerSpec createBlastAnswerSpec() {
    AnswerSpec answerSpec = new AnswerSpec(/*"GeneQuestions.GenesBySimilarity"*/);
    var paramsMap = new HashMap<String, String>();
    paramsMap.put("organism", "Plasmodium adleri G01");
    paramsMap.put("scope", "Gene");
    paramsMap.put("BlastDatabaseOrganism", "Plasmodium falciparum 3D7");
    paramsMap.put("BlastDatabaseType", "Transcripts");
    paramsMap.put("BlastRecordClass", "TranscriptRecordClasses.TranscriptRecordClass");
    paramsMap.put("-e", "10");
    paramsMap.put("-b", "50");
    paramsMap.put("-filter", "no");
    paramsMap.put("BlastAlgorithm", "blastn");
    paramsMap.put("BlastQuerySequence", "GCAGGAAATATGATTCCAGATAATGATAAAAATTCAAATTATAAATATCCAGCTGTTTATGATGACAAAGATAAAAAGTGTCATATATTATATATTGCAGCTCAAGAAAATAATGGTCCT");
    answerSpec.setParameters(paramsMap);
    return answerSpec;
  }

  public static AnswerSpec createMultiBlastAnswerSpec() {
    AnswerSpec answerSpec = new AnswerSpec(/*"GeneQuestions.GenesByMultiBlast"*/);
    var paramsMap = new HashMap<String, String>();
    paramsMap.put("organism", "Plasmodium adleri G01");
    paramsMap.put("scope", "Gene");
    paramsMap.put("BlastDatabaseOrganism", "[\"Plasmodium falciparum 3D7\"]");
    paramsMap.put("MultiBlastDatabaseType", "AnnotatedTranscripts");
    paramsMap.put("BlastJobDescription", "testrun");
    paramsMap.put("ExpectationValue", "10");
    paramsMap.put("NumQueryResults", "100");
    paramsMap.put("MaxMatchesQueryRange", "0");
    paramsMap.put("WordSize", "11");
    paramsMap.put("ScoringMatrix", "none");
    paramsMap.put("MatchMismatchScore", "2,-3");
    paramsMap.put("GapCosts", "5,2");
    paramsMap.put("CompAdjust", "none");
    paramsMap.put("FilterLowComplex", "dust");
    paramsMap.put("SoftMask", "true");
    paramsMap.put("LowerCaseMask", "false");  
    paramsMap.put("BlastAlgorithm", "blastn");
    paramsMap.put("BlastQuerySequence", "GCAGGAAATATGATTCCAGATAATGATAAAAATTCAAATTATAAATATCCAGCTGTTTATGATGACAAAGATAAAAAGTGTCATATATTATATATTGCAGCTCAAGAAAATAATGGTCCT");
    answerSpec.setParameters(paramsMap);
    return answerSpec;
  }

  public static AnswerSpec createPopsetByCountryAnswerSpec() {
    AnswerSpec answerSpec = new AnswerSpec(/*"PopsetQuestions.PopsetByCountry"*/);
    var paramsMap = new HashMap<String, String>();
    paramsMap.put("country", "GAZ_00002560");
    answerSpec.setParameters(paramsMap);
    return answerSpec;
  }

  public static DefaultJsonAnswerFormatConfig getBlastReporterFormatting() {
    DefaultJsonAnswerFormatConfig config = getDefaultFormatConfigOneRecord();
    config.setAttributes(Arrays.asList(new String[] { "summary", "alignment" }));
    return config;
  }

}
