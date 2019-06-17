package org.gusdb.wdk.model.api.record.search.column.reporter;

import java.util.Collection;

public class Histogram<T> {
  private long totalValues;

  private long nullValues;

  private int uniqueValues;
  private boolean hasUnique;

  private Collection<Value<T>> values;

  private T maxValue;
  private boolean hasMax;

  private T minValue;
  private boolean hasMin;

  public long getTotalValues() {
    return totalValues;
  }

  public void setTotalValues(long totalValues) {
    this.totalValues = totalValues;
  }

  public long getNullValues() {
    return nullValues;
  }

  public void setNullValues(long nullValues) {
    this.nullValues = nullValues;
  }

  public int getUniqueValues() {
    return uniqueValues;
  }

  public boolean hasUnique() {
    return hasUnique;
  }

  public void setUniqueValues(int uniqueValues) {
    this.hasUnique = true;
    this.uniqueValues = uniqueValues;
  }

  public Collection<Value<T>> getValues() {
    return values;
  }

  public void setValues(Collection<Value<T>> values) {
    this.values = values;
  }

  public T getMaxValue() {
    return maxValue;
  }

  public boolean hasMax() {
    return hasMax;
  }

  public void setMaxValue(T maxValue) {
    this.hasMax = true;
    this.maxValue = maxValue;
  }

  public T getMinValue() {
    return minValue;
  }

  public boolean hasMin() {
    return hasMin;
  }

  public void setMinValue(T minValue) {
    this.hasMin = true;
    this.minValue = minValue;
  }

  public static final class Value<T> {
    private T value;
    private long count;

    public T getValue() {
      return value;
    }

    public void setValue(T value) {
      this.value = value;
    }

    public long getCount() {
      return count;
    }

    public void setCount(long count) {
      this.count = count;
    }
  }
}
