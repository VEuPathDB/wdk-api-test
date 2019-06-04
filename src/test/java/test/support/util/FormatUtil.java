package test.support.util;

import org.gusdb.wdk.model.api.Format;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatUtil {
  public static void formatsEqual(Format a, Format b) {
    assertEquals(a.isInReport, b.isInReport);
    assertEquals(a.displayName, b.displayName);
    assertEquals(a.name, b.name);
    assertEquals(a.description, b.description);
    assertArrayEquals(a.scopes, b.scopes);
    assertEquals(a.type, b.type);
  }
}
