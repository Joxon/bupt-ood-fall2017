package homework7;

// the interface of all strategies
public interface IPricingStrategy {

  double getSubTotal(SaleLineItem item);

  double getDiscount();

}
