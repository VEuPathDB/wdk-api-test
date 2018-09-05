package org.gusdb.wdk.model.api.users.steps.analyses.plugins;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoEnrichmentPatchRequest extends
    StepAnalysisPlugin<GoEnrichmentFormParams> {

  public GoEnrichmentPatchRequest() {
    this.setFormParams(new GoEnrichmentFormParams());
  }
}
