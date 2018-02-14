package com.is416.assignment1.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DeleteTaskActivity extends AppCompatActivity {

    private String taskToDelete = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_task);

        Intent intent = getIntent();
        taskToDelete = intent.getStringExtra("toDelete");
        TextView tv = findViewById(R.id.task_to_delete);
        tv.setText("Delete Task: " + taskToDelete + "?");

    }

    public void confirmDelete(View view) {
        Intent returnToHome = new Intent();
        returnToHome.putExtra("deletedTask", taskToDelete);
        setResult(RESULT_OK, returnToHome);
        finish();
    }

    public void goBackToMain(View view) {
        Intent returnToHome = new Intent();
        returnToHome.putExtra("deletedTask", "");
        setResult(RESULT_OK, returnToHome);
        finish();
    }

}
