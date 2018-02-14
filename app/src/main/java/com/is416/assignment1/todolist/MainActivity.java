package com.is416.assignment1.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_ADD_TASK = 0000;
    private static final int REQ_CODE_VIEW_SINGLE_TASK = 0001;
    private static final int REQ_CODE_DELETE_TASK = 0002;
    private List<String> tasks;
    private ArrayAdapter<String> adapter;
    private Context con = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView tasksView = findViewById(R.id.tasks_view);
        tasks = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks);
        readFromFile();

        tasksView.setAdapter(adapter);

        tasksView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedTask = adapterView.getItemAtPosition(i).toString();
                Intent intent = new Intent(con, SingleTaskActivity.class);
                intent.putExtra("taskSelected", selectedTask);
                startActivityForResult(intent, REQ_CODE_VIEW_SINGLE_TASK);
            }
        });

        tasksView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String toDelete = adapterView.getItemAtPosition(i).toString();
                Intent intent = new Intent(con, DeleteTaskActivity.class);
                intent.putExtra("toDelete", toDelete);
                startActivityForResult(intent, REQ_CODE_DELETE_TASK);
                return false;
            }
        });


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
        } else if (reqCode == REQ_CODE_VIEW_SINGLE_TASK) {
            String original = data.getStringExtra("original");
            String edited = data.getStringExtra("edited");
            Toast.makeText(this, "task edited", Toast.LENGTH_SHORT).show();
            adapter.remove(original);
            adapter.add(edited);
            adapter.notifyDataSetChanged();

             try {
                 PrintStream ps = new PrintStream(openFileOutput("tasks.txt", MODE_PRIVATE));
                 for (String s : tasks) {
                     ps.println(s);
                 }
                 ps.close();
             } catch (FileNotFoundException e) {
                 e.printStackTrace();
             }

            adapter.notifyDataSetChanged();
        } else if (reqCode == REQ_CODE_DELETE_TASK) {
             String deleted = data.getStringExtra("deletedTask");

             if(deleted.trim().length() != 0) {
                 Toast.makeText(this, "task deleted", Toast.LENGTH_SHORT).show();
                 adapter.remove(deleted);
                 adapter.notifyDataSetChanged();

                 try {
                     PrintStream ps = new PrintStream(openFileOutput("tasks.txt", MODE_PRIVATE));
                     for (String s : tasks) {
                         ps.println(s);
                     }
                     ps.close();
                 } catch (FileNotFoundException e) {
                     e.printStackTrace();
                 }
             }
        }
    }
}
