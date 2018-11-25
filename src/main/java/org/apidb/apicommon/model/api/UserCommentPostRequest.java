package org.apidb.apicommon.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCommentPostRequest {
  private String headline;
  private String content;
  private Target target;

  public String getHeadline() {
    return headline;
  }

  public UserCommentPostRequest setHeadline(String headline) {
    this.headline = headline;
    return this;
  }

  public String getContent() {
    return content;
  }

  public UserCommentPostRequest setContent(String content) {
    this.content = content;
    return this;
  }

  public Target getTarget() {
    return target;
  }

  public UserCommentPostRequest setTarget(Target target) {
    this.target = target;
    return this;
  }
}
