package com.is416.assignment1.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_ADD_TASK = 0000;
    private List<String> tasks;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView tasksView = findViewById(R.id.tasks_view);
        tasks = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
        readFromFile();

        tasksView.setAdapter(adapter);

    }

    public void newTask(View view) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivityForResult(intent, REQ_CODE_ADD_TASK);
    }
    
    private void readFromFile() {

        Scanner rd = null;

        try {
            rd = new Scanner(openFileInput("tasks.txt"));

            while (rd.hasNextLine()) {
                String curLn = rd.nextLine();
                tasks.add(curLn);
            }
        } catch (Exception e) {

        } finally {
            rd.close();
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == REQ_CODE_ADD_TASK) {
            String newTask = data.getStringExtra("newTask");
//            tasks.add(newTask);
            Toast.makeText(this, "new task added", Toast.LENGTH_SHORT).show();
            adapter.add(newTask);
            adapter.notifyDataSetChanged();
        }
    }
}
