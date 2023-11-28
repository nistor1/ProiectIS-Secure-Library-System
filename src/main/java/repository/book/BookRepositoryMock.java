package repository.book;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository {

    private List<Book> books;

    public BookRepositoryMock() {
        books = new ArrayList<>();
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(Book book) {
        return books.add(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }

    @Override
    public boolean deleteById(Long id) {
        for(Book b : books) {
            if(b.getId().equals(id)) {

                return books.remove(b);
            }
        }
       return false;
    }
    @Override
    public boolean updateStockById(Long id, Long stock) {
        for(Book b : books) {
            if(b.getId().equals(id)) {
                if(stock >= 1) {
                    b.setStock((stock - 1));
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
