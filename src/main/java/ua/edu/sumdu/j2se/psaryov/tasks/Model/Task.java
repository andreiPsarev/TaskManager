package ua.edu.sumdu.j2se.psaryov.tasks.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Cloneable, Serializable {

    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean active;
    private static final long serialVersionUID = 1;

    public Task(String title, LocalDateTime time) {

        if (title == null) {
            throw new NullPointerException("Parameter title cannot be null!");
        }

        if (time == null) {
            throw new IllegalArgumentException("Parameter time cannot be null!");
        }

        start = time;
        end = time;
        interval = 0;
        active = false;
        this.title = title;
    }

    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) {

        if (title == null) {
            throw new NullPointerException("Parameter title cannot be null!");
        }

        if (start == null || end == null) {
            throw new NullPointerException("Parameters start and end cannot be null!");
        }

        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("Start time  cannot be less then end time!");
        }

        if (interval < 0) {
            throw new IllegalArgumentException("Period cannot be less then zero!");
        }

        if (interval == 0) {
            this.end = start;
        } else {
            this.end = end;
        }
        this.start = start;
        this.interval = interval;
        this.title = title;
        this.active = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            throw new NullPointerException("Parameter title cannot be null!");
        }

        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getTime() {
        return start;
    }

    public void setTime(LocalDateTime time) {

        if (time == null) {
            throw new NullPointerException("Time cannot be null!");
        }

        start = time;
        end = time;
        interval = 0;
    }

    public LocalDateTime getStartTime() {
        return start;
    }

    public LocalDateTime getEndTime() {
        return end;
    }

    public int getRepeatInterval() {
        return interval;
    }

    public void setTime(LocalDateTime start, LocalDateTime end, int interval) {

        if (start == null || end == null) {
            throw new NullPointerException("Parameter time cannot be null!");
        }

        if (interval < 0) {
            throw new IllegalArgumentException("Period cannot be less then zero!");
        }

        if (end.compareTo(start) < 0) {
            throw new IllegalArgumentException("Start time  cannot be less then end time!");
        }

        if (interval == 0) {
            this.end = start;
        } else {
            this.end = end;
        }

        this.start = start;
        this.interval = interval;
    }

    public boolean isRepeated() {
        return interval != 0;
    }

    public LocalDateTime nextTimeAfter(LocalDateTime current) {

        if (current == null) {
            throw new NullPointerException("Parameter current cannot be null!");
        }

        if (!active) {
            return null;
        }

        if (interval == 0) {
            if (end.compareTo(current) > 0) {
                return end;
            } else {
                return null;
            }
        } else {
            for (LocalDateTime i = start; i.compareTo(end) <= 0; i = i.plusSeconds(interval)) {
                if (i.compareTo(current) > 0) {
                    return i;
                }
            }
            return null;
        }
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (!(otherObject instanceof Task)) return false;
        Task task = (Task) otherObject;
        return start.equals(task.start)
                && end.equals(task.end)
                && interval == task.interval
                && active == task.active
                && title.equals(task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, start, end, interval, active);
    }

    @Override
    public String toString() {
        return "Task{"
                + "title='" + title + '\''
                + ", start=" + start
                + ", end=" + end
                + ", interval=" + interval
                + ", active=" + active
                + '}';
    }

    public Object clone() throws CloneNotSupportedException {
        Task clone = (Task) super.clone();
        clone.title = title;
        clone.start = start;
        clone.end = end;
        clone.interval = interval;
        clone.active = active;
        return clone;
    }

    public String showTask() {
        String strTask;
        if (interval == 0) {
            strTask = "\nTitle: " + title +
                    "\nTime: " + start +
                    "\nActive: " +  (active?"yes":"no")+"\n" ;
        } else {
            strTask = "\nTitle: " + title
                    + "\nStart: " + start
                    + "\nEnd: " + end
                    + "\nInterval (sec): " + interval
                    + "\nActive: " + (active?"yes":"no")+"\n";
        }
        return strTask;
    }
}
