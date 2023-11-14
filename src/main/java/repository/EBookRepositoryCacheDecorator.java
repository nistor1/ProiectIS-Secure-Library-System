package repository;

import model.AudioBook;
import model.EBook;

import java.util.List;
import java.util.Optional;

public class EBookRepositoryCacheDecorator  extends BookRepositoryDecorator  implements BookRepository<EBook>{
    private Cache<EBook> cache;

    public EBookRepositoryCacheDecorator(BookRepository bookRepository, Cache<EBook> cache){
        super(bookRepository);
        this.cache = cache;
    }

    @Override
    public List<EBook> findAll() {
        if (cache.hasResult()){
            return cache.load();
        }

        List<EBook> books = decoratedRepository.findAll();
        cache.save(books);

        return books;
    }

    @Override
    public Optional<EBook> findById(Long id) {
        if (cache.hasResult()){
            return cache.load()
                    .stream()
                    .filter(it -> it.getId().equals(id))
                    .findFirst();
        }

        Optional<EBook> book = decoratedRepository.findById(id);

        return book;
    }

    @Override
    public boolean save(EBook book) {
        cache.invalidateCache();
        return decoratedRepository.save(book);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }
}

