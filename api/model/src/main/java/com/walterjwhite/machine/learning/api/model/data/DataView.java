package com.walterjwhite.machine.learning.api.model.data;

import com.walterjwhite.datastore.api.model.entity.AbstractNamedEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class DataView extends AbstractNamedEntity {
  @OneToMany(cascade = CascadeType.ALL)
  protected List<DataElement> dataElements = new ArrayList<>();

  public DataView(String name, String description) {
    super(name, description);
  }

  public DataView() {
    super();
  }

  public List<DataElement> getDataElements() {
    return dataElements;
  }

  public void setDataElements(List<DataElement> dataElements) {
    this.dataElements = dataElements;
  }
}
