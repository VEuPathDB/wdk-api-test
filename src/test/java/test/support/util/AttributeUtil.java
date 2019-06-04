package test.support.util;

import org.gusdb.wdk.model.api.Attribute;
import org.gusdb.wdk.model.api.AttributeToolBundle;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AttributeUtil {
  public static void attributesEqual(Attribute a, Attribute b) {
    assertEquals(a.help, b.help);
    assertEquals(a.isInReport, b.isInReport);
    assertEquals(a.formats.length, b.formats.length);
    for (int i = 0; i < a.formats.length; i++)
      FormatUtil.formatsEqual(a.formats[i], b.formats[i]);
    assertEquals(a.truncateTo, b.truncateTo);
    assertEquals(a.displayName, b.displayName);
    assertEquals(a.getName(), b.getName());
    assertEquals(a.isSortable, b.isSortable);
    assertEquals(a.isDisplayable, b.isDisplayable);
    assertEquals(a.isRemovable, b.isRemovable);
    assertEquals(a.columnDataType, b.columnDataType);
    toolBundlesEqual(a.tools, b.tools);
    assertEquals(a.properties, b.properties);
    assertEquals(a.align, b.align);
    assertEquals(a.type, b.type);
  }

  private static void toolBundlesEqual(AttributeToolBundle ...a) {
    for (int i = 1; i < a.length; i++) {
      assertArrayEquals(a[0].filters, a[i].filters);
      assertArrayEquals(a[0].reporters, a[i].reporters);
    }
  }
}
