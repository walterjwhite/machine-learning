package com.walterjwhite.machine.learning.providers.mahout.service.mahout;

import java.util.List;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;

public class MahoutUtil {
  private MahoutUtil() {}

  public static FastIDSet build(List<Long> ids) {
    final FastIDSet fastIDSet = new FastIDSet();
    ids.forEach(i -> fastIDSet.add(i));

    return (fastIDSet);
  }
}
