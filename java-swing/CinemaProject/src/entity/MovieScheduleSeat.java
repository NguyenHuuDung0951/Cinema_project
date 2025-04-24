package entity;

import java.util.Objects;

public class MovieScheduleSeat {
    private MovieSchedule schedule;
    private Seat seat;
    private boolean isAvailable;

    public MovieScheduleSeat(MovieSchedule schedule, Seat seat, boolean isAvailable) {
        this.schedule = schedule;
        this.seat = seat;
        this.isAvailable = isAvailable;
    }

    public MovieSchedule getSchedule() { return schedule; }
    public void setSchedule(MovieSchedule schedule) { this.schedule = schedule; }

    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieScheduleSeat)) return false;
        MovieScheduleSeat other = (MovieScheduleSeat) o;
        return Objects.equals(schedule.getScheduleID(), other.schedule.getScheduleID())
            && Objects.equals(seat.getSeatID(), other.seat.getSeatID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(schedule.getScheduleID(), seat.getSeatID());
    }
}