package homework7;

// each book gets x yuan off

public class FlatRateStrategy implements IPricingStrategy {

  private double discountPerBook;

  FlatRateStrategy(double discount) {
    discountPerBook = discount;
  }

  public double getSubTotal(SaleLineItem item) {
    int num = item.getAmount();
    double price = item.getProduct().getPrice();
    return (price - discountPerBook) * num;
  }

  public double getDiscount() {
    return discountPerBook;
  }

}
