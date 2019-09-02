package homework7;

public class StrategyMode {

  // public enum StrategyType{
  // PercentageDiscount,
  // AbsoluteDiscount,
  // CompositeDiscount,
  // NoDiscount
  // }

  public static final int PercentageDiscount = 0;
  public static final int AbsoluteDiscount = 1;
  public static final int CompositeDiscount = 2;
  public static final int NoDiscount = 3;

  private String name;
  private String typeName;

  private int number;
  private int strategyType;
  int bookType;

  IPricingStrategy strategy;

  private int sType1;
  private int sType2;

  StrategyMode(int number, String name, int strategyType, int bookType, int stype1, int stype2,
      IPricingStrategy strategy) {
    this.number = number;
    this.name = name;
    this.strategyType = strategyType;
    this.bookType = bookType;
    this.strategy = strategy;

    if (strategyType == PercentageDiscount) {
      typeName = "Percentage Discount";
    } else if (strategyType == AbsoluteDiscount) {
      typeName = "Absolute Discount";
    } else if (strategyType == CompositeDiscount) {
      typeName = "Composite Discount";
      sType1 = stype1;
      sType2 = stype2;
    } else {
      typeName = "No Discount";
    }
  }

  public IPricingStrategy getStrategy() {
    return strategy;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getName() {
    return name;
  }

  public void setName(String s) {
    name = s;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String s) {
    typeName = s;
  }

  public int getStrategyType() {
    return strategyType;
  }

  public void setStrategyType(int Type) {
    strategyType = Type;
  }

  public int getBookType() {
    return bookType;
  }

  public void setBookType(int Type) {
    bookType = Type;
  }

  public int getsType1() {
    return sType1;
  }

  public void setsType1(int type) {
    sType1 = type;
  }

  public int getsType2() {
    return sType2;
  }

  public void setsType2(int type) {
    sType2 = type;
  }
}
