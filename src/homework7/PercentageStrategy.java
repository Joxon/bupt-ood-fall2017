package homework7;

// each book gets x% off

public class PercentageStrategy implements IPricingStrategy {

  private double discountPercentage; // ex. 90.0

  PercentageStrategy(double discount) {
    discountPercentage = discount;
  }

  public double getSubTotal(SaleLineItem Item) {
    int num = Item.getAmount();
    double price = Item.getProduct().getPrice();
    return (double) num * (1 - discountPercentage / 100) * price;
  }

  public double getDiscount() {
    return discountPercentage;
  }

}
