package com.walterjwhite.machine.learning.api.model.data;

import com.walterjwhite.datastore.api.model.entity.AbstractNamedEntity;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.annotations.PersistenceCapable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(doNotUseGetters = true, callSuper = true)
@PersistenceCapable
public class DataView extends AbstractNamedEntity {

  protected List<DataElement> dataElements = new ArrayList<>();

  public DataView(String name, String description) {
    super(name, description);
  }

  public DataView() {
    super();
  }
}
