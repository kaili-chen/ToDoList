package com.is416.assignment1.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SingleTaskActivity extends AppCompatActivity {

    private String originalTask = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);

        Intent intent = getIntent();
        String passedTask = intent.getStringExtra("taskSelected");
        originalTask = passedTask;

        EditText et = findViewById(R.id.task_name);
        et.setText(passedTask);    }

    public void editTask(View view) {

        EditText et = findViewById(R.id.task_name);

        String edited = et.getText().toString();
        if (!edited.equals(originalTask)) {
            //rewrite file
            //add new edit

            //go back to home page
            Intent returnToHome = new Intent();
            returnToHome.putExtra("original", originalTask);
            returnToHome.putExtra("edited", edited);
            setResult(RESULT_OK, returnToHome);
            finish();
        }
    }
}
