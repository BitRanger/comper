
package com.caibowen.comper.feature.impl;

import com.caibowen.comper.feature.IRandomGenerator;
import com.caibowen.comper.util.Pair;
import com.caibowen.comper.util.Assert;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator implements IRandomGenerator {

  public Random tlrand() {
    return ThreadLocalRandom.current();
  }

  @Override
  public int pickInt(int limit) {
    return ThreadLocalRandom.current().nextInt(limit);
  }

  @Override
  public <T> T pickSingle(List<T> list) {

    ThreadLocalRandom random = ThreadLocalRandom.current();
    return list.get(random.nextInt(list.size()));
  }

  @Override
  public <T> T pickSingle(Set<T> set) {
    ThreadLocalRandom random = ThreadLocalRandom.current();
    int idx = random.nextInt(set.size());
    int i = 0;
    for (T t : set) {
      if (idx == i) {
        return t;
      }
      i++;
    }
    return set.iterator().next();
  }

  @Override
  public <T> List<Integer> pickIdexes(List<T> groupList, float ratio) {

    ThreadLocalRandom random = ThreadLocalRandom.current();
    final int $ = groupList.size();
    final int sz = (int) ($ * ratio);
    Set<Integer> set = new HashSet<>($);
    while (set.size() < sz) {
      set.add(random.nextInt($));
    }
    return new ArrayList<>(set);
  }

  /**
   * pick a pair
   */
  @Override
  public <T> Pair<T, T> pickFrom(List<T> groupList) {
    Assert.notEmpty(groupList);

    ThreadLocalRandom random = ThreadLocalRandom.current();
    int idx1 = random.nextInt(groupList.size());
    int idx2 = idx1;
    while (idx2 == idx1) {
      idx2 = random.nextInt(groupList.size());
    }
    return new Pair<T, T>(groupList.get(idx1), groupList.get(idx2));
  }

  public <T> Set<T> pickFrom(List<T> groupList, float ratio) {
    final int newSz = (int) (ratio * groupList.size());
    return pickFrom(groupList, newSz);
  }

  /**
   * pick a number of objects from the list, no redundant
   */
  public <T> Set<T> pickFrom(List<T> groupList, int newSz) {
    Assert.notEmpty(groupList);

    ThreadLocalRandom random = ThreadLocalRandom.current();
    Set<T> newSet = new HashSet<T>(newSz * 2);
    while (newSet.size() < newSz) {
      newSet.add(groupList.get(random.nextInt(groupList.size())));
    }
    return newSet;
  }

}
