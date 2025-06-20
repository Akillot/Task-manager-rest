package app.task_manager_rest.controller;

import app.task_manager_rest.dto.TaskRequest;
import app.task_manager_rest.dto.TaskResponse;
import app.task_manager_rest.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@RequestBody TaskRequest request){
        return taskService.createTask(request);
    }

    @GetMapping
    public List<TaskResponse> getAll(){
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id, @RequestBody TaskRequest request){
        return taskService.updateTask(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        taskService.deleteTaskById(id);
    }

    @GetMapping("/sort-by-status-false-first")
    public List<TaskResponse> getSort(){
        return taskService.sortingByStatusFalseFirst();
    }

    @GetMapping("/sort-by-status-true-first")
    public List<TaskResponse> getSortByStatusTrueFirst(){
        return taskService.sortingByStatusTrueFirst();
    }
}