package homework7;

public interface Subject {

  void removeObserver(Observer ob);

  void registerObserver(Observer ob);

  void notifyObservers() throws InstantiationException, IllegalAccessException, ClassNotFoundException;
}
