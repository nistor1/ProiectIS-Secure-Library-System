package model;

import java.time.LocalDate;

public class AudioBook extends Book{
    int runTime;

    public int getRunTime() {
        return runTime;
    }
    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }
    @Override
    public String toString(){
        return String.format("Book ID: %d | Author: %s | Title: %s | Published Date: %s | Run Time: %d", super.getId(), super.getAuthor(), super.getTitle(), super.getPublishedDate(), this.runTime);
    }
}
