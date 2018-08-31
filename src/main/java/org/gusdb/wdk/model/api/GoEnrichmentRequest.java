package org.gusdb.wdk.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoEnrichmentRequest extends StepAnalysisPlugin<GoEnrichmentRequest.FormParams> {
  public static class FormParams {
    public String[] goAssociationsOntologies = new String[0];
    public String[] goSubset = new String[0];
    public String[] organism = new String[0];
    public String[] pValueCutoff = new String[0];
    public String[] goEvidenceCodes = new String[0];
  }
}
