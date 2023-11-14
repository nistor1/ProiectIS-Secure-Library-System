package repository;

import model.AudioBook;
import model.Book;

import java.util.List;
import java.util.Optional;

public class AudioBookCacheDecorator extends BookRepositoryDecorator  implements BookRepository<AudioBook>{
    private Cache<AudioBook> cache;

    public AudioBookCacheDecorator(BookRepository bookRepository, Cache<AudioBook> cache){
        super(bookRepository);
        this.cache = cache;
    }

    @Override
    public List<AudioBook> findAll() {
        if (cache.hasResult()){
            return cache.load();
        }

        List<AudioBook> books = decoratedRepository.findAll();
        cache.save(books);

        return books;
    }

    @Override
    public Optional<AudioBook> findById(Long id) {
        if (cache.hasResult()){
            return cache.load()
                    .stream()
                    .filter(it -> it.getId().equals(id))
                    .findFirst();
        }

        Optional<AudioBook> book = decoratedRepository.findById(id);

        return book;
    }

    @Override
    public boolean save(AudioBook book) {
        cache.invalidateCache();
        return decoratedRepository.save(book);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }
}

