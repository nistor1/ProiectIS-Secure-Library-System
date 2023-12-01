package service.user;

import model.Book;

import java.util.List;

public interface EmployeeService {
    List<Book> viewAllBooks();//

    Book sellBook(Long id, Long stock);//

    Book findBookById(Long id);

    Book addBook(Book book);

    boolean updateStockById(Long id, Long stock);

    boolean deleteBookById(Long id);

    boolean logout();//

}
