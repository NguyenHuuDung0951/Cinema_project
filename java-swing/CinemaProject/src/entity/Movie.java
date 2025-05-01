package entity;

import java.util.Objects;

public class Movie {
    private String movieID;
    private String movieName;
    private String status;
    private int duration;
    private String posterPath;

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }
    public Movie(String movieID) {
        this.movieID = movieID;
    }

    
    public Movie(String movieID, String movieName, String status, int duration, String posterPath) {
        this.movieID = movieID;
        this.movieName = movieName;
        this.status = status;
        this.duration = duration;
        this.posterPath = posterPath;
    }

  

    public String getMovieID() { return movieID; }
    public void setMovieID(String movieID) { this.movieID = movieID; }

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie other = (Movie) o;
        return Objects.equals(movieID, other.movieID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieID);
    }

    @Override
    public String toString() {
        return "Movie{" + "movieID=" + movieID + ", movieName=" + movieName + ", status=" + status + ", duration=" + duration + '}';
    }
}