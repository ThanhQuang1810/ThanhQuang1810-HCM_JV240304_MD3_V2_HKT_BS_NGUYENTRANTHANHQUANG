package ra.model;

import ra.util.InputMethods;

public class Catalog {
  private  static int idCount = 0;
  private  int catalogId;
    private String catalogName,description;

  public Catalog() {
  this.catalogId=++idCount;
  }

  public Catalog(int catalogId, String catalogName, String description) {
    this.catalogId = catalogId;
    this.catalogName = catalogName;
    this.description = description;
  }

  public int getCatalogId() {
    return catalogId;
  }

  public void setCatalogId(int catalogId) {
    this.catalogId = catalogId;
  }

  public String getCatalogName() {
    return catalogName;
  }

  public void setCatalogName(String catalogName) {
    this.catalogName = catalogName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }





  @Override
  public String toString() {
    return "Catalog{" +
            "catalogId=" + catalogId +
            ", catalogName='" + catalogName + '\'' +
            ", description='" + description + '\'' +
            '}';
  }
}
