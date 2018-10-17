package test.support.util;

import org.gusdb.wdk.model.api.AnswerFormatting;
import org.gusdb.wdk.model.api.DefaultJsonAnswerFormatConfig;
import org.gusdb.wdk.model.api.Pagination;

public class AnswerUtil {
  
  public static AnswerFormatting getDefaultFormattingOneRecord() {
    AnswerFormatting formatting = new AnswerFormatting("wdk-service-json");
    Pagination paging = new Pagination();
    paging.setNumRecords(1);
    DefaultJsonAnswerFormatConfig formatConfig = new DefaultJsonAnswerFormatConfig();
    formatConfig.setPagination(paging);
    formatting.setFormatConfig(formatConfig);
    return formatting;
  }

}
