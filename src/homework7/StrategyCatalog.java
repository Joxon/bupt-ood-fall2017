package homework7;

import java.util.ArrayList;
import java.util.HashMap;

public class StrategyCatalog {

  // singleton
  private static StrategyCatalog catalog = new StrategyCatalog();

  private ArrayList<StrategyMode> strategyModeArrayList;

  private HashMap<Integer, IPricingStrategy> strategyHashMap;

  StrategyCatalog() {
    strategyHashMap = new HashMap<>();
    strategyModeArrayList = new ArrayList<>();

    addSimpleStrategy("绝对值优惠策略1", BookSpecification.TextBook, StrategyMode.AbsoluteDiscount, 1);

    addSimpleStrategy("百分比折扣策略1", BookSpecification.ComicBook, StrategyMode.PercentageDiscount,
        7);

    addSimpleStrategy("百分比折扣策略2", BookSpecification.ComputerBook,
        StrategyMode.PercentageDiscount, 3);

    addCompositeStrategy("顾客最优策略1", BookSpecification.HealthBook, 1, 3); // the 1st + the 3rd
  }

  public ArrayList<StrategyMode> getStrategyList() {
    return strategyModeArrayList;
  }

  public static StrategyCatalog getInstance() {
    if (catalog == null)
      catalog = new StrategyCatalog();
    return catalog;
  }

  public void addSimpleStrategy(String name, int bookType, int strategyType, double discount) {
    if (strategyType == StrategyMode.PercentageDiscount) {
      IPricingStrategy strategy = new PercentageStrategy(discount);

      int row = strategyHashMap.size() + 1;

      StrategyMode mode = new StrategyMode(row, name, strategyType, bookType, 0, 0, strategy);

      strategyHashMap.put(row, strategy);
      strategyModeArrayList.add(mode);
    } else if (strategyType == StrategyMode.AbsoluteDiscount) {
      FlatRateStrategy strategy = new FlatRateStrategy(discount);

      int row = strategyHashMap.size() + 1;

      StrategyMode mode = new StrategyMode(row, name, strategyType, bookType, 0, 0, strategy);

      strategyHashMap.put(row, strategy);
      strategyModeArrayList.add(mode);
    }
  }

  public void addCompositeStrategy(String name, int bookType, int sType1, int sType2) {
    CompositeStrategy strategy = new CompositeBestForCustomer();
    strategy.addStrategy(strategyHashMap.get(sType1));
    strategy.addStrategy(strategyHashMap.get(sType2));

    for (int i = 0; i < strategyHashMap.size(); ++i) {
      if (!strategyHashMap.containsKey(i)) {
        strategyHashMap.put(i, strategy);
        strategyModeArrayList.add(new StrategyMode(i, name, StrategyMode.CompositeDiscount,
            bookType, sType1, sType2, strategy));

        return;
      }
    }
  }

  public IPricingStrategy getPricingStrategy(int bookType) {
    for (StrategyMode strategyMode : strategyModeArrayList) {
      if (strategyMode.bookType == bookType) {
        return strategyMode.strategy;
      }
    }
    System.out.println("getPricingStrategy: strategy not found");
    return null;
  }

  public void deleteStrategy(int num) {
    strategyHashMap.remove(num);
    strategyModeArrayList.remove(num);
  }

  public void updateSimpleStrategy(int number, String name, int bookType, int strategyType, double discount) {

    if (strategyType == StrategyMode.PercentageDiscount) {
      IPricingStrategy strategy = new PercentageStrategy(discount);

      StrategyMode mode = new StrategyMode(number, name, strategyType, bookType, 0, 0,
          strategy);

      strategyHashMap.remove(number);
      strategyHashMap.put(number, strategy);
      strategyModeArrayList.set(number, mode);
    }

    else if (strategyType == StrategyMode.AbsoluteDiscount) {
      FlatRateStrategy strategy = new FlatRateStrategy(discount);

      StrategyMode mode = new StrategyMode(number, name, strategyType, bookType, 0, 0,
          strategy);

      strategyHashMap.remove(number);
      strategyHashMap.put(number, strategy);
      strategyModeArrayList.set(number, mode);
    }
  }

  public void updateCompositeStrategy(int i, String name, int bookType, int sType1, int sType2) {
    CompositeStrategy strategy = new CompositeBestForCustomer();
    strategy.addStrategy(strategyHashMap.get(sType1));
    strategy.addStrategy(strategyHashMap.get(sType2));

    StrategyMode mode = new StrategyMode(i, name, StrategyMode.CompositeDiscount,
        bookType, sType1, sType2, strategy);

    strategyHashMap.remove(i);
    strategyHashMap.put(i, strategy);
    strategyModeArrayList.set(i, mode);
  }
}
