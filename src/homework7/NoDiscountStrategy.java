package homework7;

public class NoDiscountStrategy implements IPricingStrategy {

  public double getSubTotal(SaleLineItem Item) {
    int num = Item.getAmount();
    double price = Item.getProduct().getPrice();
    return (double) num * price;
  }

  public double getDiscount() {
    return 0;
  }

}
