package test.support.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.gusdb.wdk.model.api.FilterValueSpec;
import org.gusdb.wdk.model.api.SearchConfig;
import org.gusdb.wdk.model.api.Step;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class StepUtil {
  private static StepUtil instance;

  private StepUtil() {}

  public static final String BASE_PATH = UserUtil.BY_ID_PATH + "/steps";
  public static final String BY_ID_PATH = BASE_PATH + "/{stepId}";

  public static StepUtil getInstance() {
    if (instance == null) {
      instance = new StepUtil();
    }
    return instance;
  }

  public Response createValidExonCountStepResponse(RequestFactory requestFactory)
      throws JsonProcessingException {

    Step step = new Step(ReportUtil.createValidExonCountSearchConfig(requestFactory), "GenesByExonCount");

    return requestFactory.jsonPayloadRequest(step, HttpStatus.SC_OK, ContentType.JSON).when().post(BASE_PATH,
        "current");
  }

  public SearchConfig createSearchConfigWithStepFilter(String filterName, RequestFactory requestFactory)
      throws JsonProcessingException {
    SearchConfig searchConfig = ReportUtil.createValidExonCountSearchConfig(requestFactory);

    // add filter to searchConfig
    FilterValueSpec filterSpec = new FilterValueSpec();
    filterSpec.setName(filterName);
    Map<String, Object> value = new HashMap<String, Object>();
    String[] matches = { "Y", "N" };
    value.put("values", matches);
    filterSpec.setValue(value);
    List<FilterValueSpec> filterSpecs = new ArrayList<FilterValueSpec>();
    filterSpecs.add(filterSpec);
    searchConfig.setFilters(filterSpecs);
    return searchConfig;
  }

  public void getStep(long stepId, RequestFactory requestFactory, String cookieId, int expectedStatus)
      throws JsonProcessingException {
    requestFactory.emptyRequest().cookie("JSESSIONID", cookieId).expect().statusCode(
        expectedStatus).when().get(BY_ID_PATH, "current", stepId);
  }

  public void deleteStep(long stepId, RequestFactory requestFactory, String cookieId, int expectedStatus)
      throws JsonProcessingException {

    requestFactory.emptyRequest().cookie("JSESSIONID", cookieId).expect().statusCode(
        expectedStatus).when().delete(BY_ID_PATH, "current", stepId);
  }  

}
