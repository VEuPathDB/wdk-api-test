package org.gusdb.wdk.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * for now, just need to access stepTree, so won't fully render this class, which would otherwise
 * have a lot of properties
 * @author Steve
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class StepResponseBody extends StepRequestBody {


}
