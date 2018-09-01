package test.support.util;

import org.gusdb.wdk.model.api.GoEnrichmentRequest;

public class StepAnalysis {

  public static final String BASE_PATH = Steps.BY_ID_PATH + "/analyses";
  public static final String BY_ID_PATH = BASE_PATH + "/{analysisId}";

  private static final StepAnalysis INSTANCE = new StepAnalysis();

  private StepAnalysis() {
  }

  public GoEnrichmentRequest newGoEnrichmentParamsBody() {
    final GoEnrichmentRequest tmp = new GoEnrichmentRequest();
    final GoEnrichmentRequest.FormParams params = new GoEnrichmentRequest.FormParams();

    params.goAssociationsOntologies = new String[] { "Biological Process" };
    params.goSubset = new String[] { "No" };
    params.organism = new String[] { "Plasmodium adleri G01" };
    params.pValueCutoff = new String[] { "0.06" };
    params.goEvidenceCodes = new String[] { "Computed", "Curated" };
    tmp.setFormParams(params);

    return tmp;
  }

  public GoEnrichmentRequest newGoEnrichmentNameBody() {
    final GoEnrichmentRequest tmp = new GoEnrichmentRequest();

    tmp.setDisplayName("Test Value");

    return tmp;
  }

  public GoEnrichmentRequest newCreateGoEnrichmentRequestBody() {
    final GoEnrichmentRequest tmp = newGoEnrichmentParamsBody();

    tmp.setDisplayName("Test Analysis Display Name");

    return tmp;
  }

  public static StepAnalysis getInstance() {
    return INSTANCE;
  }
}
