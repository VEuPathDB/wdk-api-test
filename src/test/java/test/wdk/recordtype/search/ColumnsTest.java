package test.wdk.recordtype.search;

import groovy.lang.Tuple;
import org.gusdb.wdk.model.api.Attribute;
import org.gusdb.wdk.model.api.RecordType;
import org.gusdb.wdk.model.api.Search;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import test.support.util.*;
import test.wdk.TestBase;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static test.support.Category.PLASMO_TEST;
import static test.support.util.RecordTypeUtil.getRecordType;
import static test.support.util.SearchUtil.getSearch;

public class ColumnsTest extends TestBase {

  private final RequestFactory fac;

  public ColumnsTest(GuestRequestFactory fac) {
    this.fac = fac;
  }

  @ParameterizedTest
  @Tag(PLASMO_TEST)
  @MethodSource("plasmoSearches")
  void matchesSearchColumnSet(String recordName, String searchName) {
    RecordType record = getRecordType(fac, recordName);
    Search search = getSearch(fac, recordName, searchName);

    Set<String> result = Stream.of(fac.jsonSuccessRequest()
      .when()
      .get(ColumnUtil.BASE_URI, recordName, searchName)
      .as(String[].class))
      .collect(Collectors.toSet());


    Set<String> all = Stream.concat(
      Stream.of(record.attributes),
      Stream.of(search.searchData.dynamicAttributes)
    )
      .map(Attribute::getName)
      .collect(Collectors.toSet());

    // Confirm that the set of columns returned from the
    // endpoint matches the joined list of record and
    // dynamic attributes.
    assertEquals(all.size(), result.size());
    all.forEach(col -> assertTrue(result.contains(col)));
  }

  @ParameterizedTest
  @Tag(PLASMO_TEST)
  @MethodSource("plasmoSearches")
  void matchesExpandedSearchColumnSet(String recordName, String searchName) {
    RecordType record = getRecordType(fac, recordName);
    Search search = getSearch(fac, recordName, searchName);

    Map<String, Attribute> result = Stream.of(fac.jsonSuccessRequest()
      .when()
      .get(ColumnUtil.EXPANDED_URI, recordName, searchName)
      .as(Attribute[].class))
      .collect(Collectors.toMap(Attribute::getName, Function.identity()));

    Map<String, Attribute> all = Stream.concat(
      Stream.of(record.attributes),
      Stream.of(search.searchData.dynamicAttributes)
    )
      .collect(Collectors.toMap(Attribute::getName, Function.identity()));

    // Confirm that the set of columns returned from the
    // endpoint matches the joined list of record and
    // dynamic attributes.
    assertEquals(all.size(), result.size());
    all.forEach((k, v) -> {
      assertTrue(result.containsKey(k));
      AttributeUtil.attributesEqual(v, result.get(k));
    });
  }

  @ParameterizedTest
  @Tag(PLASMO_TEST)
  @MethodSource("plasmoAttributes")
  void columnByName(
    final String record,
    final String search,
    final Attribute expect
  ) {
    AttributeUtil.attributesEqual(expect, fac.jsonSuccessRequest()
      .when()
      .get(ColumnUtil.KEYED_URI, record, search, expect.name)
      .as(Attribute.class));
  }

  static Stream<Arguments> plasmoAttributes() {
    RequestFactory rFac = GuestRequestFactory.getInstance();
    return plasmoSearches()
      .map(Arguments::get)
      .flatMap(o -> pa1(rFac, (String) o[0], (String) o[1]))
      .map(Arguments::of);
  }

  static Stream<Arguments> plasmoSearches() {
    return Stream.of(Arguments.of("transcript", "GenesByExonCount"));
  }

  static Stream<Object[]> pa1(RequestFactory rFac, String a, String b) {
    RecordType rec = getRecordType(rFac, a);
    Search sea = getSearch(rFac, a, b);

    return Stream.concat(
      Stream.of(rec.attributes),
      Stream.of(sea.searchData.dynamicAttributes)
    )
      .map(c -> new Object[]{a, b, c});
  }
}
