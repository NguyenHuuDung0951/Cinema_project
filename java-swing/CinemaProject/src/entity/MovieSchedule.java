package entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class MovieSchedule {

    private String scheduleID;
    private Movie movie;       // FK object
    private Room room;         // FK object
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public MovieSchedule(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public MovieSchedule(String scheduleID, Movie movie, Room room,
            LocalDateTime startTime, LocalDateTime endTime) {
        this.scheduleID = scheduleID;
        this.movie = movie;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieSchedule)) {
            return false;
        }
        MovieSchedule other = (MovieSchedule) o;
        return Objects.equals(scheduleID, other.scheduleID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleID);
    }
}
