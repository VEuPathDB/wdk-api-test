package org.gusdb.wdk.model.api.users.steps.analyses.plugins;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoEnrichmentRequest extends
    StepAnalysisPlugin<GoEnrichmentFormParams> {

  public GoEnrichmentRequest() {
    this.setFormParams(new GoEnrichmentFormParams());
  }

}
