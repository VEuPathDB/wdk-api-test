package test.support.util;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.users.steps.analyses.plugins.GoEnrichmentRequest;
import org.gusdb.wdk.model.api.users.steps.analyses.plugins.GoEnrichmentResponse;

public class StepAnalysis {

  public static final String BASE_PATH = Steps.BY_ID_PATH + "/analyses";
  public static final String BY_ID_PATH = BASE_PATH + "/{analysisId}";

  private static final StepAnalysis INSTANCE = new StepAnalysis();

  public GoEnrichmentRequest newGoEnrichmentParamsBody() {
    final GoEnrichmentRequest tmp = new GoEnrichmentRequest();

    tmp.getFormParams()
        .setGoAssociationsOntologies(new String[] { "Biological Process" })
        .setGoSubset(new String[] { "No" })
        .setOrganism(new String[] { "Plasmodium adleri G01" })
        .setpValueCutoff(new String[] { "0.06" })
        .setGoEvidenceCodes(new String[] { "Computed", "Curated" });

    return tmp;
  }

  public GoEnrichmentRequest newGoEnrichmentNameBody() {
    final GoEnrichmentRequest tmp = new GoEnrichmentRequest();

    tmp.setDisplayName("Test Value");

    return tmp;
  }

  public static StepAnalysis getInstance() {
    return INSTANCE;
  }

  public GoEnrichmentResponse newGoEnrichment(Auth auth, String defaultUser, long stepId) {
    return auth.prepRequest()
        .contentType(ContentType.JSON)
        .body(new GoEnrichmentRequest())
        .expect()
        .statusCode(HttpStatus.SC_OK)
        .contentType(ContentType.JSON)
        .when()
        .post(BASE_PATH, defaultUser, stepId)
        .as(GoEnrichmentResponse.class);
  }
}
