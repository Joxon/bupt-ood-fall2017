package homework7;

import java.util.ArrayList;

public class Sale implements Subject {

  private ArrayList<SaleLineItem> items;
  private ArrayList<Observer> observers;

  Sale() {
    items = new ArrayList<>();
    observers = new ArrayList<>();
  }

  public void addItem(SaleLineItem item)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    // duplicate item
    for (SaleLineItem i : items) {
      if (i.getProduct().getIsbn().equals(item.getProduct().getIsbn())) {
        i.setAmount(item.getAmount() + i.getAmount());
        notifyObservers();
        return;
      }
    }
    // new item
    items.add(item);
    notifyObservers();
  }

  public double getTotal() {
    double total = 0;
    for (SaleLineItem item : items) {
      total += item.getStrategy().getSubTotal(item);
    }
    return total;
  }

  public void resetSale() {
    items.clear();
  }

  public ArrayList<SaleLineItem> getItemList() {
    return items;
  }

  public void registerObserver(Observer ob) {
    observers.add(ob);
  }

  public void notifyObservers() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    for (Observer ob : observers) {
      ob.update(this);
    }
  }

  public void removeObserver(Observer ob) {
    int num = observers.indexOf(ob);
    if (num > 0) {
      observers.remove(num);
    }

  }
}
