package model.builder;

import model.Book;
import model.EBook;

import java.time.LocalDate;

public class EBookBuilder extends BookBuilder{
    private EBook eBook;
    public EBookBuilder() {
        this.eBook = new EBook();
    }
    public EBookBuilder(EBook eBook) {
        this.eBook = eBook;
    }
    public EBookBuilder setFormat (String format){
        eBook.setFormat(format);
        return this;
    }


    @Override
    public EBook build(){
        return eBook;
    }
}
