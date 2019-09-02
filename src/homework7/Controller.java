package homework7;

import java.util.ArrayList;

public class Controller {

  private static Controller controller = new Controller();

  private BookCatalog bookCatalog;
  private StrategyCatalog strategyCatalog;

  private Sale sale;
  private PricingStrategyFactory pricingStrategyFactory;

  private Controller() {
    bookCatalog = BookCatalog.getInstance();
    strategyCatalog = StrategyCatalog.getInstance();

    sale = new Sale();
    pricingStrategyFactory = PricingStrategyFactory.getInstance();
  }

  public static Controller getInstance() {
    if (controller == null)
      controller = new Controller();
    return controller;
  }

  public Sale getSale() {
    return sale;
  }

  public BookCatalog getBookCatalog() {
    return bookCatalog;
  }

  public StrategyCatalog getStrategyCatalog() {
    return strategyCatalog;
  }

  // book actions

  public void buyBook(String isbn, int copies)
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    BookSpecification book = bookCatalog.getBookSpecification(isbn);

    if (book == null) {
      System.out.println("buyBook: book not found!");
      return;
    }

    sale.addItem(new SaleLineItem(copies, isbn, book.getTitle(), book.getPrice(), book.getType()));
  }

  public boolean addBook(String isbn, String title, double price, int type) {
    BookSpecification book = new BookSpecification(isbn, title, price, type);
    if (bookCatalog.addBook(book)) {
      System.out.println("Successfully added a book：" + " Title：" + book.getTitle() + " ISBN：" + book.getIsbn()
          + " Price：" + book.getPrice() + " Type：" + book.getType());
      return true;
    } else {
      System.out.println("Failed to add book " + isbn);
      return false;
    }
  }

  public boolean updateBook(String isbn, String title, double price, int type) {
    ArrayList<BookSpecification> books = bookCatalog.getBookSpecifications();
    for (BookSpecification book : books) {
      if (book.getIsbn().equals(isbn)) {
        book.setTitle(title);
        book.setPrice(price);
        book.setType(type);
        return true;
      }
    }
    return false;
  }

  public boolean removeBook(String isbn) {
    if (bookCatalog.removeBook(isbn)) {
      System.out.println("Successfully deleted " + isbn);
      return true;
    } else {
      System.out.println("Failed to delete " + isbn);
      return false;
    }
  }

  // strategy actions

  public void addCompositeStrategy(String name, int bookType, int sType1, int sType2) {
    strategyCatalog.addCompositeStrategy(name, bookType, sType1, sType2);
  }

  public void addSimpleStrategy(String name, int bookType, int stype, double discount) {
    strategyCatalog.addSimpleStrategy(name, bookType, stype, discount);
  }

  public void deleteStrategy(int num) {
    strategyCatalog.deleteStrategy(num);
  }

  public void updateSimpleStrategy(int i, String name, int btype, int stype, double price) {
    strategyCatalog.updateSimpleStrategy(i, name, btype, stype, price);
  }

  public void updateCompositeStrategy(int i, String name, int btype, int stype1, int stype2) {
    strategyCatalog.updateCompositeStrategy(i, name, btype, stype1, stype2);
  }

}
