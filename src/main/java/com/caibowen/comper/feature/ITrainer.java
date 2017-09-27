
package com.caibowen.comper.feature;

import com.caibowen.comper.misc.Predicate;
import com.caibowen.comper.model.BWQuestionMeta;
import com.caibowen.comper.util.Pair;
import com.caibowen.comper.feature.model.Group;

import java.util.List;
import java.util.Set;

/**
 * 训练器，对一组题进行过滤，交叉，变异等操作
 *
 * @author BowenCai
 *
 */
public interface ITrainer extends IRefreshable {

  /**
   * 增加一批新的group，新group符合config要求，即题数和Points一定
   * @param size
   * @return
   */
  public List<Group> recruit(int size);

  public List<Group> getInitGroupList(int size);

  /**
   *
   * @param t1
   * @param t2
   * @return 将两队组到一起，去除重复元素
   */
  public List<Group> teamUp(List<Group> t1, List<Group> t2);

  /**
   * @param predicate
   * @return remaining
   */
  public List<Group> filter(Predicate<BWQuestionMeta> predicate, List<Group> groups);

  /**
   * 交叉
   * @param pair
   */
  public void crossOver(Pair<Group, Group> pair);

  public void crossOver(Group group1, Group group2);


  /**
   * 变异
   * @param group
   * @param ratio
   */
  public void variate(Group group, float ratio);

  /**
   * 对表中每一组都按照设定的比率变异
   * @param groups
   * @param ratio
   */
  public void bulkVariate(Set<Group> groups, float ratio);


  public Config getConfig();

  public void setConfig(Config c);

  public IRandomGenerator getRandomGenerator();

  public void setRandomGenerator(IRandomGenerator randomGenerator);

  public void increase(List<Group> ls);

  public void reduct(List<Group> ls);
}
