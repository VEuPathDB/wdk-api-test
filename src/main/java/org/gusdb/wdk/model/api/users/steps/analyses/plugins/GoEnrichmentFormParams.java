package org.gusdb.wdk.model.api.users.steps.analyses.plugins;

import java.util.Arrays;

public class GoEnrichmentFormParams {
  private String[] goAssociationsOntologies = new String[0];
  private String[] goSubset = new String[0];
  private String[] organism = new String[0];
  private String[] pValueCutoff = new String[0];
  private String[] goEvidenceCodes = new String[0];

  public String[] getGoAssociationsOntologies() {
    return goAssociationsOntologies;
  }

  public GoEnrichmentFormParams setGoAssociationsOntologies(String[] goAssociationsOntologies) {
    this.goAssociationsOntologies = goAssociationsOntologies;
    return this;
  }

  public String[] getGoSubset() {
    return goSubset;
  }

  public GoEnrichmentFormParams setGoSubset(String[] goSubset) {
    this.goSubset = goSubset;
    return this;
  }

  public String[] getOrganism() {
    return organism;
  }

  public GoEnrichmentFormParams setOrganism(String[] organism) {
    this.organism = organism;
    return this;
  }

  public String[] getpValueCutoff() {
    return pValueCutoff;
  }

  public GoEnrichmentFormParams setpValueCutoff(String[] pValueCutoff) {
    this.pValueCutoff = pValueCutoff;
    return this;
  }

  public String[] getGoEvidenceCodes() {
    return goEvidenceCodes;
  }

  public GoEnrichmentFormParams setGoEvidenceCodes(String[] goEvidenceCodes) {
    this.goEvidenceCodes = goEvidenceCodes;
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof GoEnrichmentFormParams)) {
      return false;
    }

    if(obj == this) {
      return true;
    }

    final GoEnrichmentFormParams tst = (GoEnrichmentFormParams) obj;

    return Arrays.equals(goAssociationsOntologies, tst.goAssociationsOntologies)
        && Arrays.equals(goSubset, tst.goSubset)
        && Arrays.equals(organism, tst.organism)
        && Arrays.equals(pValueCutoff, tst.pValueCutoff)
        && Arrays.equals(goEvidenceCodes, tst.goEvidenceCodes);
  }
}
