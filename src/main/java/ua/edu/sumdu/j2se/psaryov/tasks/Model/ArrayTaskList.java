package ua.edu.sumdu.j2se.psaryov.tasks.Model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTaskList extends AbstractTaskList {
    private Task[] array;
    private static final int START_LENGTH = 10;

    public ArrayTaskList() {
        array = new Task[START_LENGTH];
        size = 0;
        type = ListTypes.types.ARRAY;
    }

    public ArrayTaskList(ArrayTaskList list) {

        if (list == null) {
            throw new NullPointerException("Argument list cannot be null!");
        }

        array = new Task[list.array.length];
        size = list.size;
        System.arraycopy(list.array, 0, array, 0, size);
        type = ListTypes.types.ARRAY;
    }

    public void add(Task task) {
        if (task == null) {
            return;
        }

        if (size < array.length) {
            array[size++] = task;
        } else {
            Task[] newArray = new Task[array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
            array[size++] = task;
        }
    }

    public boolean remove(Task task) {
        if (task == null) {
            return false;
        } else {

            for (int i = 0; i != size; i++) {
                if (task.equals(array[i])) {
                    System.arraycopy(array, i + 1, array, i, size - (i + 1));
                    array[size--] = null;
                    return true;
                }
            }
            return false;
        }
    }

    public Task getTask(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Uncorrect index of list");
        } else {
            return array[index];
        }
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (!(otherObject instanceof ArrayTaskList)) return false;

        ArrayTaskList tasks = (ArrayTaskList) otherObject;
        return (size == tasks.size) && Arrays.equals(array, tasks.array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("ArrayTaskList ( size = " + size + " ){\n");
        int i = 0;
        if (i != size) {
            str.append(array[i++]);
        }
        while (i != size) {
            str.append(",\n").append(array[i++]);
        }
        str.append("\n};");
        return str.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        ArrayTaskList clone = (ArrayTaskList) super.clone();
        clone.array = Arrays.copyOf(array, size);
        clone.size = size;
        return clone;
    }

    ///added
    public void setTask(int index, Task task) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Uncorrect index of list");
        }

        if (task == null) {
            throw new NullPointerException("Cannot set null");
        }

        array[index] = task;
    }

    @Override
    public Iterator<Task> iterator() {
        return new ArrayListTaskIterator();
    }

    private class ArrayListTaskIterator implements Iterator<Task> {

        private int cursor = 0;
        private int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public Task next() {
            if (cursor >= size) {
                throw new NoSuchElementException("Out of array interval!");
            }
            lastRet = cursor;
            return array[cursor++];
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException("Out of array interval!");
            }
            ArrayTaskList.this.remove(array[lastRet]);
            cursor = lastRet--;
        }
    }
}
