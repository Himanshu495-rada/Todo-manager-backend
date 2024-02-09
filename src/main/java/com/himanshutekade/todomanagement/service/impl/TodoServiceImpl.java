package com.himanshutekade.todomanagement.service.impl;

import com.himanshutekade.todomanagement.TodoManagementApplication;
import com.himanshutekade.todomanagement.dto.TodoDto;
import com.himanshutekade.todomanagement.entity.Todo;
import com.himanshutekade.todomanagement.exception.ResourceNotFoundException;
import com.himanshutekade.todomanagement.repo.TodoRepo;
import com.himanshutekade.todomanagement.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private TodoRepo todoRepo;
    private ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {

        //convert dto into jpa
        Todo todo = modelMapper.map(todoDto, Todo.class);

        //save jpa to database
        Todo savedTodo = todoRepo.save(todo);

        //convert saved jpa to dto
        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);

        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long id) {

        Todo todo = todoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found for given id " + id));

        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {

        List<Todo> todos = todoRepo.findAll();

        return todos.stream().map((todo) -> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());
    }

    @Override
    public TodoDto setTodo(TodoDto todoDto,Long id) {

        Todo todo = todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found id: " + id));

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.getCompleted());

        Todo updatedTodo = todoRepo.save(todo);

        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public Void deleteTodo(Long id) {
        Todo todo = todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        todoRepo.deleteById(id);
        return null;
    }

    @Override
    public TodoDto completeTodo(Long id) {

        Todo todo = todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        todo.setCompleted(Boolean.TRUE);

        Todo updatedTodo = todoRepo.save(todo);

        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {

        Todo todo = todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        todo.setCompleted(Boolean.FALSE);

        Todo updatedTodo = todoRepo.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }


}
