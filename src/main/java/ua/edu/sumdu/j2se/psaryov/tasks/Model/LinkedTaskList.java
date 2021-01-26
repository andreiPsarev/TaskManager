package ua.edu.sumdu.j2se.psaryov.tasks.Model;

import java.util.Iterator;

public class LinkedTaskList extends AbstractTaskList {
    private NodeTask first;
    private NodeTask last;

    public LinkedTaskList() {
        first = null;
        last = null;
        size = 0;
        type = ListTypes.types.LINKED;
    }

    public void add(Task task) {
        if (task == null) {
            return;
        }

        NodeTask newLast = new NodeTask(task);
        if (first == null) {
            first = newLast;
            last = newLast;
        } else {
            last.setNext(newLast);
            last = newLast;
        }
        size++;
    }

    public boolean remove(Task task) {
        if (task == null || first == null) {
            return false;
        } else {
            NodeTask previous = first;
            for (NodeTask i = first; i != null; i = i.getNext()) {
                if (task.equals(i.getValue())) {
                    if (i.equals(first)) {
                        first = first.getNext();
                        if (size == 1) {
                            last = first;
                        }
                    } else if (i.equals(last)) {
                        previous.setNext(null);
                        last = previous;
                    } else {
                        previous.setNext(i.getNext());
                    }
                    size--;
                    return true;
                }
                previous = i;
            }
            return false;
        }
    }

    public Task getTask(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("uncorrect index of list");
        } else {
            NodeTask element = first;
            for (int i = 0; i != index; i++) {
                element = element.getNext();
            }
            return element.getValue();
        }
    }

    public void setTask(int index, Task task) {
        System.out.println("Method don't has realisation");
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (!(otherObject instanceof LinkedTaskList)) return false;

        LinkedTaskList tasks = (LinkedTaskList) otherObject;
        if (size != tasks.size) return false;
        if (first == null && tasks.first == null) return true;

        NodeTask i;
        NodeTask j;
        for (i = first, j = tasks.first; i != null && j != null; i = i.getNext(), j = j.getNext()) {
            if (!i.getValue().equals(j.getValue())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        if (first == null) {
            return hash;
        }
        NodeTask i;
        int j;
        for (i = first, j = 0; i != null && j != size; i = i.getNext(), j++) {
            hash += (j + 1) * i.getValue().hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("LinkedTaskList ( size = " + size + " ){\n");
        NodeTask i = first;
        if (i != null) {
            str.append(i.getValue());
            i = i.getNext();
        }
        while (i != null) {
            str.append(",\n").append(i.getValue());
            i = i.getNext();
        }
        str.append("\n};");
        return str.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        LinkedTaskList clone = (LinkedTaskList) super.clone();
        clone.first = null;
        clone.last = null;
        clone.size = 0;
        for (NodeTask i = first; i != null; i = i.getNext()) {
            clone.add((Task) i.getValue().clone());
        }
        return clone;
    }

    @Override
    public Iterator<Task> iterator() {
        return new LinkedListTaskIterator();
    }

    private class LinkedListTaskIterator implements Iterator<Task> {

        private NodeTask next = first;
        private NodeTask lastRet = null;

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Task next() {
            Task nextTask = next.getValue();
            lastRet = next;
            next = next.getNext();
            return nextTask;
        }

        @Override
        public void remove() {
            if (lastRet == null) {
                throw new IllegalStateException("Out of list interval!");
            } else {
                LinkedTaskList.this.remove(lastRet.value);
                lastRet = null;
            }
        }
    }

    private static class NodeTask {
        private Task value;
        private NodeTask next;

        public NodeTask(Task value) {
            this.value = value;
            next = null;
        }

        public Task getValue() {
            return value;
        }

        public NodeTask getNext() {
            return next;
        }

        public void setNext(NodeTask next) {
            this.next = next;
        }
    }
}
