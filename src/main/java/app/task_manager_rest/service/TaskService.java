package app.task_manager_rest.service;

import app.task_manager_rest.dto.TaskRequest;
import app.task_manager_rest.dto.TaskResponse;
import app.task_manager_rest.model.Task;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final Map<Long, Task> taskStorage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public TaskResponse createTask(TaskRequest request) {
        Long id = idGenerator.getAndIncrement();
        Task task = new Task(id, request.getTitle(), request.getDescription(), request.getIsCompleted());
        taskStorage.put(id,task);
        return mapToResponse(task);
    }

    public List<TaskResponse> getAllTasks(){
        List<TaskResponse> responses = new ArrayList<>();
        for(Task task : taskStorage.values()){
            responses.add(mapToResponse(task));
        }
        return responses;
    }

    public TaskResponse getTaskById(Long id){
        Task task = taskStorage.get(id);
        if(task == null){
            throw new NoSuchElementException("Task not found");
        }
        return mapToResponse(task);
    }

    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = taskStorage.get(id);
        if(task == null){
            throw new NoSuchElementException("Task not found");
        }
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setIsCompleted(request.getIsCompleted());
        return mapToResponse(task);
    }

    public void deleteTaskById(Long id){
        if(taskStorage.remove(id) == null){
            throw new NoSuchElementException("Task not found");
        }
    }

    public List<TaskResponse> sortingByStatus(){
        return taskStorage.values().stream()
                .sorted(Comparator.comparing(Task::getIsCompleted))
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    private TaskResponse mapToResponse(Task task){
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setIsCompleted(task.getIsCompleted());
        return response;
    }
}