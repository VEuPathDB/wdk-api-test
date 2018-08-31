package test.support.util;

import org.gusdb.wdk.model.api.GoEnrichmentRequest;

public class StepAnalysis {
  public static final StepAnalysis INSTANCE = new StepAnalysis();

  private StepAnalysis() {
  }

  public GoEnrichmentRequest createGoEnrichmentParamsRequest() {
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

  public GoEnrichmentRequest createGoEnrichmentNameRequest() {
    final GoEnrichmentRequest tmp = new GoEnrichmentRequest();
    tmp.setDisplayName("Test Value");
    return tmp;
  }

  public static StepAnalysis getInstance() {
    return INSTANCE;
  }
}
