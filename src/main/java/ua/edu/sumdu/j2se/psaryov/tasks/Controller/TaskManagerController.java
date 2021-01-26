package ua.edu.sumdu.j2se.psaryov.tasks.Controller;

import ua.edu.sumdu.j2se.psaryov.tasks.Model.Task;
import ua.edu.sumdu.j2se.psaryov.tasks.Model.TaskManagerModel;
import ua.edu.sumdu.j2se.psaryov.tasks.Model.Tasks;
import ua.edu.sumdu.j2se.psaryov.tasks.View.Main;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.SortedMap;


public class TaskManagerController {

    private TaskManagerModel model;
    public static Logger logger = Logger.getLogger(TaskManagerController.class);


    public TaskManagerController(TaskManagerModel model) {
        this.model = model;
    }

    public void menu() throws IOException {

        int answer;
        boolean isWork = true;
        do{
            System.out.println("********************");
            System.out.println(" -------------------------------");
            System.out.println("|             Меню:             |");
            System.out.println("|   (1) Вывести список задач    |");
            System.out.println("|   (2) Добавить задачу         |");
            System.out.println("|   (3) Вывести календарь       |");
            System.out.println("|   (4) Изменить задачу         |");
            System.out.println("|   (5) Удалить задачу          |");
            System.out.println("|   (6) Выйти                   |");
            System.out.println("|_______________________________|");
            System.out.print("Выберите действие (1-6):      ");

            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                answer = Integer.parseInt(reader.readLine());
            } catch (Exception e) {
                System.out.println("Вы ввели недопустимое значение, попробуйте еще раз");
                logger.error("Введено недопустимое значение", e);
                continue;
            }

            if (answer < 1 || answer > 6) {
                System.out.println("Выберите от 1 до 6");
                continue;
            }
            System.out.println("********************\n");
            isWork = doAction(answer);
        }while(isWork);
    }

    public boolean doAction(int action) throws IOException {
        switch (action) {
            case 1:
                showTaskList();
                break;
            case 2:
                model.addToTaskList(model.createTask());
                break;
            case 3:
                showCalendar();
                break;
            case 4:
                changeTaskAction();
                break;
            case 5:
                removeTaskAction();
                break;
            case 6:
                return false;
        }
        return true;
    }

    private void changeTaskAction() throws IOException {

        if (model.getTaskList().size() == 0) {
            System.out.println("Нет задач для изменения");
            logger.info("Нет задач для изменения");
        } else {
            showTaskList();
            int index= -1;
            try {
                System.out.print("Выберите индекс задачи (1-" + model.getTaskList().size() + "):");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                index = Integer.parseInt(reader.readLine()) - 1;
                logger.info("Выбрана задача для изменения");
            } catch (Exception e) {
                System.out.print("Вы ввели что-то неверно. Хотите попробовать снова? (true or false): ");
                boolean answer;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    answer = Boolean.parseBoolean(reader.readLine());
                } catch (Exception ex) {
                    return;
                }

                if(answer) {
                    changeTaskAction();
                } else {
                    return;
                }
            }

            if (index >= 0 && index < model.getTaskList().size()) {
                System.out.println("Задача до изменения: "+model.getTaskList().getTask(index).showTask());
                model.changeTask(index);
                System.out.println("Задача изменена");
                logger.info("Задача изменена");
                System.out.println("Список после изменений:");
                logger.info("Вывод списка после изменений");
                showTaskList();
            } else {
                System.out.print("Вы ввели неверный индекс, попробуйте снова");
                logger.info("Введён неверный индекс");
                changeTaskAction();
            }

        }
    }

    private void removeTaskAction() throws IOException {
        if(model.getTaskList().size() == 0) {
            System.out.println("Нет задач для удаления");
            logger.info("Нет задач для удаления");
        } else {
            System.out.println("Список перед удалением: ");
            logger.info("Вывод списка перед удалением");
            showTaskList();
            int index = -1;
            try {
                System.out.print("Выберите индекс задачи (1-" + model.getTaskList().size() + "):");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                index = Integer.parseInt(reader.readLine()) - 1;
                logger.info("Выбор задачи для удаления");
            } catch (Exception e) {
                System.out.print("Вы ввели что-то неверно. Хотите попробовать снова? (true/false): ");
                boolean answer;
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    answer = Boolean.parseBoolean(reader.readLine());
                } catch (Exception ex) {
                    return;
                }

                if(answer) {
                    removeTaskAction();
                } else {
                    return;
                }
            }

            if (index >= 0 && index < model.getTaskList().size()) {
                model.removeTask(index);
                System.out.println("Задача удалена");
                logger.info("Задача удалена");
                System.out.println("Список после удаления: ");
                logger.info("Вывод списка задач после удаления");
                showTaskList();
            } else {
                System.out.print("Вы ввели неверный индекс, попробуйте снова");
                logger.info("Введён неверный индекс");
                removeTaskAction();
            }
        }
    }

    private void showTaskList() {
        int i =1;
        if(model.getTaskList().size() == 0)
        {
            System.out.println("Список задач пока пуст");
            logger.info("Список задач пуст");
        }
        for (Task task: model.getTaskList()) {
            System.out.println(("№"+(i++)+" Задача:")+task.showTask());
        }
        logger.info("Список задач");
    }

    private  void showCalendar() throws IOException {
        LocalDateTime start;
        LocalDateTime end;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        try {
            System.out.println("Введите промежуток времени:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Начальная дата (dd-MM-yyyy HH:mm:ss): ");
            logger.info("Ввод начальной даты");
            start = LocalDateTime.parse(reader.readLine(), formatter);

            System.out.print("Конечная дата (dd-MM-yyyy HH:mm:ss): ");
            logger.info("Ввод конечной даты");
            end = LocalDateTime.parse(reader.readLine(), formatter);

            model.setCalendar(Tasks.calendar(model.getTaskList(), start, end));
            }

        catch (Exception e) {
            System.out.print("Вы ввели что-то неверно. Хотите попробовать снова? (true/false): ");
            boolean answer;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                answer = Boolean.parseBoolean(reader.readLine());
            } catch (Exception ex) {
                return;
            }

            if(answer) {
                showCalendar();
            } else {
                return;
            }
        }

        System.out.println("Календарь:");
        System.out.println("****************");
        for (SortedMap.Entry<LocalDateTime, Set<Task>> entry: model.getCalendar().entrySet()) {
            System.out.println("Время: "+entry.getKey());
            for (Task task: entry.getValue()) {
                System.out.println("Название: "+ task.getTitle());
            }
        }
        logger.info("Вывод календаря");
        System.out.println("****************");
    }
}