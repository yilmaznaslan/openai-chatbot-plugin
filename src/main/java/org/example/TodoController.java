package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "https://chat.openai.com")
@RestController
@RequestMapping("/todos")
public class TodoController {

    private final Map<String, List<String>> todos = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @PostMapping("/{username}")
    public ResponseEntity<String> addTodo(@PathVariable String username, @RequestBody Map<String, String> body) {
        logger.info(String.format("Adding todo for:%s. Body: %s", username, body));

        todos.putIfAbsent(username, new ArrayList<>());
        todos.get(username).add(body.get("todo"));
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<String>> getTodos(@PathVariable String username) {
        logger.info(String.format("Getting todo is added for %s", username));
        return ResponseEntity.ok(todos.getOrDefault(username, new ArrayList<>()));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteTodo(@PathVariable String username, @RequestBody Map<String, Integer> body) {
        int todoIdx = body.get("todo_idx");
        List<String> userTodos = todos.get(username);
        if (userTodos != null && todoIdx >= 0 && todoIdx < userTodos.size()) {
            userTodos.remove(todoIdx);
        }
        return ResponseEntity.ok("OK");
    }
}

