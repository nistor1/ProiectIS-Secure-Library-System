package model;

import java.time.LocalDate;

public class EBook extends Book{
    String format;
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString(){
        return String.format("Book ID: %d | Author: %s | Title: %s | Published Date: %s | Format: %s", super.getId(), super.getAuthor(), super.getTitle(), super.getPublishedDate(), this.format);
    }
}
