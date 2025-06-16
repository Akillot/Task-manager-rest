package app.task_manager_rest.service;

import app.task_manager_rest.dto.TaskRequest;
import app.task_manager_rest.dto.TaskResponse;
import app.task_manager_rest.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final Map<Long, Task> taskStorage = new ConcurrentMap<String, Task>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public TaskResponse createTask(TaskRequest task) {
        Long id = idGenerator.getAndIncrement();
        Task task = new Task(id, request.getTitle(), request.getDeskription(), request.getIsCompleted());
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

    public static TaskResponse updateTask(Long id, TaskRequest task) {
        Task task = taskStorage.get(id);
        if(task == null){
            throw new NoSuchElementException("Task not found");
        }
        task.setTitle(task.getTitle());
        task.setDescription(task.getDescription());
        task.setIsCompleted(task.getIsCompleted());
        return mapToResponse(task);
    }

    public void deleteTaskById(Long id){
        if(taskStorage.remove(id) == null){
            throw new NoSuchElementException("Task not found");
        }
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
