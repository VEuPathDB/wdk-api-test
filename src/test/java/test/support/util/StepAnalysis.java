package test.support.util;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.users.steps.analyses.plugins.GoEnrichmentCreateRequest;
import org.gusdb.wdk.model.api.users.steps.analyses.plugins.GoEnrichmentPatchRequest;
import org.gusdb.wdk.model.api.users.steps.analyses.plugins.GoEnrichmentResponse;
import org.gusdb.wdk.model.api.users.steps.analyses.plugins.NewAnalysisRequest;

public class StepAnalysis {
  private static StepAnalysis instance;
  private final Requests req;

  private StepAnalysis(Requests req) {
    this.req = req;
  }

  public interface Paths {
    String BASE = Steps.BY_ID_PATH + "/analyses";
    String BY_ID = BASE + "/{analysisId}";
    String RESULT = BY_ID + "/result";
    String STATUS = RESULT + "/status";
  }


  public GoEnrichmentPatchRequest newGoEnrichmentParamsBody() {
    final GoEnrichmentPatchRequest tmp = new GoEnrichmentPatchRequest();

    tmp.getFormParams()
        .setGoAssociationsOntologies(new String[] { "Biological Process" })
        .setGoSubset(new String[] { "No" })
        .setOrganism(new String[] { "Plasmodium adleri G01" })
        .setpValueCutoff(new String[] { "0.06" })
        .setGoEvidenceCodes(new String[] { "Computed", "Curated" });

    return tmp;
  }

  public GoEnrichmentPatchRequest newGoEnrichmentNameBody() {
    final GoEnrichmentPatchRequest tmp = new GoEnrichmentPatchRequest();

    tmp.setDisplayName("Test Value");

    return tmp;
  }

  /**
   * Deletes a step analysis instance.
   *
   * @param user       url param user id
   * @param stepId     url param step id
   * @param analysisId analysis instance id for deletion
   */
  public void deleteStepAnalysis(String user, long stepId, long analysisId) {
    req.authRequest(HttpStatus.SC_NO_CONTENT)
        .when()
        .delete(Paths.BY_ID, user, stepId, analysisId);
  }

  public NewAnalysisRequest[] analysisCreateRequestFac() {
    return new NewAnalysisRequest[] {
        new GoEnrichmentCreateRequest(),
    };
  }

  /**
   * Creates a new Go Enrichment step analysis instance
   *
   * @param user   url param user id
   * @param stepId url param step id
   *
   * @return step analysis creation API response
   */
  public GoEnrichmentResponse newGoEnrichment(String user, long stepId) {
    return req.authJsonPayloadRequest(new GoEnrichmentCreateRequest(), HttpStatus.SC_OK)
        .when()
        .post(Paths.BASE, user, stepId)
        .as(GoEnrichmentResponse.class);
  }

  public void populateGoEnrichment(String user, long stepId, long analysisId) {
    req.authJsonPayloadRequest(newGoEnrichmentParamsBody(), HttpStatus.SC_NO_CONTENT)
        .contentType("")
        .when()
        .patch(Paths.BY_ID, user, stepId, analysisId);
  }

  public void runAnalysis(String user, long stepId, long analysisId) {
    req.authJsonRequest(HttpStatus.SC_ACCEPTED)
        .when()
        .post(Paths.RESULT, user, stepId, analysisId);
  }

  public static StepAnalysis getInstance(Requests req) {
    if (instance == null) {
      instance = new StepAnalysis(req);
    }
    return instance;
  }
}
