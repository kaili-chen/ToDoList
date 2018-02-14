package com.is416.assignment1.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("=================== addtaskactivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_activity);

        Intent intent = getIntent();
        EditText et = findViewById(R.id.new_task);

    }

    public void addNewTask (View view) throws FileNotFoundException {
        EditText newTaskInput = findViewById(R.id.new_task);
        String newTask = newTaskInput.getText().toString();

        try {
            PrintStream ps = new PrintStream(openFileOutput("tasks.txt", MODE_PRIVATE | MODE_APPEND));
            ps.println(newTask);
            ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //go back to home page
        Intent returnToHome = new Intent();
        returnToHome.putExtra("newTask", newTask);
        setResult(RESULT_OK, returnToHome);
        finish();
    }

}
