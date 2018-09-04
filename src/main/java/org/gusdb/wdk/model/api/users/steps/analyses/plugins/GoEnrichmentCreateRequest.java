package org.gusdb.wdk.model.api.users.steps.analyses.plugins;

public class GoEnrichmentCreateRequest extends NewAnalysisRequest {
  public static final String ANALYSIS_NAME = "go-enrichment";

  public GoEnrichmentCreateRequest() {
    super(ANALYSIS_NAME);
  }
}
