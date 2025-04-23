package com.backendproject.taskmanager.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backendproject.taskmanager.domain.Task;
import com.backendproject.taskmanager.domain.TaskStatus;
import com.backendproject.taskmanager.domain.User;
import com.backendproject.taskmanager.domain.service.TaskService;
import com.backendproject.taskmanager.domain.service.UserService;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    // Näytä kaikki käyttäjän tehtävät prioriteetin mukaan lajiteltuna
    @GetMapping
    public String listTasks(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Task> tasks = taskService.getTasksForUserSortedByPriority(user);
        model.addAttribute("tasks", tasks);
        return "tasklist";
    }

    // Näytä lomake uuden tehtävän luomiseksi
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("statuses", TaskStatus.values());
        return "addtask";
    }

    // Käsittele uuden tehtävän luominen
    @PostMapping
    public String createTask(@ModelAttribute Task task, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        task.setUser(user);
        taskService.save(task);
        return "redirect:/tasks";
    }

    // Näytä lomake olemassa olevan tehtävän muokkaamiseksi
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Principal principal) {
        Task task = taskService.findById(id);
        User user = userService.findByUsername(principal.getName());

        if (!task.getUser().equals(user)) {
            return "redirect:/tasks"; // estetään toisen käyttäjän tehtävien muokkaus
        }

        model.addAttribute("task", task);
        model.addAttribute("statuses", TaskStatus.values());
        return "edittask";
    }

    // Käsittele muokattu tehtävä
    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Task existingTask = taskService.findById(id);

        if (!existingTask.getUser().equals(user)) {
            return "redirect:/tasks";
        }

        task.setId(id);
        task.setUser(user);
        taskService.save(task);

        return "redirect:/tasks";
    }

    // Poista tehtävä
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, Principal principal) {
        Task task = taskService.findById(id);
        User user = userService.findByUsername(principal.getName());

        if (task.getUser().equals(user)) {
            taskService.deleteById(id);
        }

        return "redirect:/tasks";
    }
}