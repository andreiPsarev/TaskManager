package ua.edu.sumdu.j2se.psaryov.tasks.Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.SortedMap;

public class TaskManagerModel {
    private AbstractTaskList taskList;
    private SortedMap<LocalDateTime, Set<Task>> calendar;
    private File dataFile;

    public TaskManagerModel() {
        dataFile = new File(System.getProperty("user.dir") + "\\data.txt");
        taskList = new ArrayTaskList();
        try {
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            } else {
                readTaskListFromRes();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        calendar = null;
    }

    public AbstractTaskList getTaskList() {
        return taskList;
    }

    public SortedMap<LocalDateTime, Set<Task>> getCalendar() {
        return calendar;
    }

    public void setCalendar(SortedMap<LocalDateTime, Set<Task>> calendar) {
        this.calendar = calendar;
    }



    public void readTaskListFromRes() throws IOException {
        TaskIO.readText(taskList, dataFile);
    }

    public void writeTaskListFromRes() throws IOException {
        TaskIO.writeText(taskList, dataFile);
    }

    public Task createTask() {
        Task createdTask = null;
        String title;
        LocalDateTime start;
        LocalDateTime end;
        boolean isRepeated;
        int interval;
        boolean active;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Введите название задачи: ");
            title = reader.readLine();
            System.out.print("Повторяется ли задача (true/false): ");
            isRepeated = Boolean.parseBoolean(reader.readLine());
            if (!isRepeated) {
                System.out.print("Введите время (dd-MM-yyyy HH:mm:ss): ");
                start = LocalDateTime.parse(reader.readLine(), formatter);
                createdTask = new Task(title, start);
            } else {
                System.out.print("Время начала (dd-MM-yyyy HH:mm:ss): ");
                start = LocalDateTime.parse(reader.readLine(), formatter);
                System.out.print("Время конца (dd-MM-yyyy HH:mm:ss): ");
                end = LocalDateTime.parse(reader.readLine(), formatter);
                System.out.print("Введите интервал (sec): ");
                interval = Integer.parseInt(reader.readLine());
                createdTask = new Task(title, start, end, interval);
            }
            System.out.print("Активна ли задача (true or false): ");
            active = Boolean.parseBoolean(reader.readLine());
            createdTask.setActive(active);
        } catch (Exception e) {
            System.out.print("Вы ввели что-то неверно. Хотите попробовать снова? (true/false): ");
            boolean answer;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                answer = Boolean.parseBoolean(reader.readLine());
            } catch (Exception ex) {
                return null;
            }

            if(answer) {
                return createTask();
            } else {
                return null;
            }
        }
        return createdTask;
    }

    public void addToTaskList(Task task) throws IOException {
        if(task == null)
            return;

        taskList.add(task);
        writeTaskListFromRes();
    }

    public void changeTask(int index) throws IOException {
        Task changedTask = createTask();
        if(changedTask == null)
            return;
        taskList.setTask(index, changedTask);
        writeTaskListFromRes();
    }

    public void removeTask(int index) throws IOException {
        taskList.remove(taskList.getTask(index));
        writeTaskListFromRes();
    }
}
