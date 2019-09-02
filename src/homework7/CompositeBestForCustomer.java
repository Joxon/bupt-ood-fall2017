package homework7;

public class CompositeBestForCustomer extends CompositeStrategy {

  public double getSubTotal(SaleLineItem item) {
    if (pricingStrategies.isEmpty()) {
      return 0;
    }

    double minCost = Double.MAX_VALUE;
    for (IPricingStrategy pricingStrategy : pricingStrategies) {
      minCost = Math.min(minCost, pricingStrategy.getSubTotal(item));
    }
    return minCost;
  }

}
