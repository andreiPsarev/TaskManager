package ua.edu.sumdu.j2se.psaryov.tasks.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable<Task>, Cloneable, Serializable {

    protected int size;
    protected ListTypes.types type;
    private static final long serialVersionUID = 1;


    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public int size() {
        return size;
    }

    public abstract Task getTask(int index);

    public abstract void setTask(int index, Task task);

    public Stream<Task> getStream() {
        Stream.Builder<Task> streamBuilder = Stream.builder();
        for (Task task : this) {
            streamBuilder.add(task);
        }
        return streamBuilder.build();
    }

    public  final AbstractTaskList incoming(LocalDateTime from, LocalDateTime to) {

        if (from == null || to == null) {
            throw new NullPointerException("Time  cannot be null!");
        }

        if (to.compareTo(from) < 0) {
            throw new IllegalArgumentException("From time  cannot be less then to time!");
        }

        AbstractTaskList list = TaskListFactory.createTaskList(type);
        getStream().filter((t) -> t.nextTimeAfter(from) != null && t.nextTimeAfter(from).compareTo(to) <= 0)
                .forEach(list::add);
        return list;
    }
}
