package org.gusdb.wdk.model.api;

/**
 * used for the Isolate summmary view reporter
 * @author Steve
 *
 */
public class Isolate {

    private String country;
    private String gaz;
    private String type;
    private int total;
    private double lat;
    private double lng;
    
    public String getCountry() {
      return country;
    }
    public void setCountry(String country) {
      this.country = country;
    }
    public String getGaz() {
      return gaz;
    }
    public void setGaz(String gaz) {
      this.gaz = gaz;
    }
    public String getType() {
      return type;
    }
    public void setType(String type) {
      this.type = type;
    }
    public int getTotal() {
      return total;
    }
    public void setTotal(int total) {
      this.total = total;
    }
    public double getLat() {
      return lat;
    }
    public void setLat(double lat) {
      this.lat = lat;
    }
    public double getLng() {
      return lng;
    }
    public void setLng(double lng) {
      this.lng = lng;
    }


}
