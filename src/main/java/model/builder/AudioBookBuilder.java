package model.builder;

import model.AudioBook;
import model.EBook;

import java.time.LocalDate;

public class AudioBookBuilder extends BookBuilder {
    private AudioBook audioBook;

    public AudioBookBuilder() {
        this.audioBook = new AudioBook();
    }
    public AudioBookBuilder(AudioBook audioBook){
        this.audioBook = audioBook;
    }
    public AudioBookBuilder setRunTime(int runTime){
        audioBook.setRunTime(runTime);
        return this;
    }


    @Override
    public AudioBook build(){
        return audioBook;
    }
}
