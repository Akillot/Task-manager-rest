package app.task_manager_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class TaskManagerRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerRestApplication.class, args);
    }
}
