package com.walterjwhite.machine.learning.api.model.data.item;

import com.walterjwhite.datastore.api.model.entity.EntityReference;
import javax.persistence.*;

@Entity
public class MappedEntityReferenceItem extends DataElementItem {

  @ManyToOne(optional = false)
  @JoinColumn(nullable = false, updatable = false)
  protected EntityReference entityReference;

  public MappedEntityReferenceItem(EntityReference entityReference) {
    super();
    this.entityReference = entityReference;
  }

  public MappedEntityReferenceItem() {
    super();
  }

  public EntityReference getEntityReference() {
    return entityReference;
  }

  public void setEntityReference(EntityReference entityReference) {
    this.entityReference = entityReference;
  }

  @PrePersist
  public void setItemId() {
    itemId = (long) hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MappedEntityReferenceItem that = (MappedEntityReferenceItem) o;

    return entityReference != null
        ? entityReference.equals(that.entityReference)
        : that.entityReference == null;
  }

  @Override
  public int hashCode() {
    return entityReference != null ? entityReference.hashCode() : 0;
  }
}
