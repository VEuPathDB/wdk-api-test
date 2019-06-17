package util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class Json {

  public static final ObjectMapper MAPPER = new ObjectMapper()
    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
    .registerModule(new ParameterNamesModule())
    .registerModule(new Jdk8Module())
    .registerModule(new JavaTimeModule());

  public static final ObjectWriter WRITER = MAPPER.writerWithDefaultPrettyPrinter();

  public static ObjectNode object() {
    return MAPPER.createObjectNode();
  }

  public static ArrayNode array() {
    return MAPPER.createArrayNode();
  }

  public static String stringify(Object obj) {
    try {
      return WRITER.writeValueAsString(obj);
    }
    catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
