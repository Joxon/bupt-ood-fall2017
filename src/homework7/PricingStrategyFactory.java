package homework7;

public class PricingStrategyFactory {

  // singleton
  private static PricingStrategyFactory instance = new PricingStrategyFactory();

  private StrategyCatalog catalog;

  PricingStrategyFactory() {
    catalog = StrategyCatalog.getInstance();
  }

  public static PricingStrategyFactory getInstance() {
    if (instance == null)
      instance = new PricingStrategyFactory();
    return instance;
  }

  public StrategyCatalog getCatalog() {
    return catalog;
  }

  public void setCatalog(StrategyCatalog cat) {
    catalog = cat;
  }

  public IPricingStrategy getPricingStrategy(int type) {
    return catalog.getPricingStrategy(type);
  }
}
