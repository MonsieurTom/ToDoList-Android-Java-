package tom.lenormand.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tom.lenormand.todolist.db.Task;
import tom.lenormand.todolist.db.TaskDAO;

public class MainActivity extends AppCompatActivity {

    private TaskDAO taskDAO = null;
    private static final String TAG = "MainActivity";
    private ListView taskListView = null;
    private taskAdaptater adapter = null;
    private Button bottom_add_task_button = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDAO = new TaskDAO(this);
        taskListView = (ListView) findViewById(R.id.todo_list);
        bottom_add_task_button = (Button) findViewById(R.id.bottom_add_task_button);

        bottom_add_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
                final View cambioView = layoutInflater.inflate(R.layout.custom_alert_dialog, null);
                dialog.setView(cambioView);

                final EditText titleEditText = (EditText) findViewById(R.id.editTextDialog);
                final EditText taskEditText = (EditText) findViewById(R.id.editTextDialog2);

                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        final EditText titleEditText = (EditText) cambioView.findViewById(R.id.editTextDialog);
                        final EditText taskEditText = (EditText) cambioView.findViewById(R.id.editTextDialog2);

                        String title = titleEditText.getText().toString();
                        String task = taskEditText.getText().toString();

                        taskDAO.ajouter(new Task(0, title, task));
                        updateUI();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogg, int id) {
                        dialogg.cancel();
                    }
                });
                dialog.show();
            }
        });

        updateUI();
    }

    private void updateUI()
    {
        ArrayList<Task> taskList = taskDAO.getTaks();

        adapter = new taskAdaptater(this, taskList);
        taskListView.setAdapter(adapter);
    }

    public void editTask(View view)
    {
        final View parent = (View) view.getParent();;
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_task);
        String firstTask = String.valueOf(taskTextView.getText());

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
        final View cambioView = layoutInflater.inflate(R.layout.custom_edit_alert_dialog, null);
        dialog.setView(cambioView);

        final EditText taskEditText = (EditText) findViewById(R.id.editTextDialog3);

        dialog.setPositiveButton("Edit", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                final EditText taskEditText = (EditText) cambioView.findViewById(R.id.editTextDialog3);
                String task = taskEditText.getText().toString();

                if (task.equals("")) {
                    popup_wrong();
                    dialogInterface.cancel();
                    return;
                }

                TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
                String titleTask = String.valueOf(taskTextView.getText());

                taskDAO.modifier(new Task(0, titleTask,task));
                updateUI();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogg, int id) {
                dialogg.cancel();
            }
        });
        dialog.show();
    }

    public void popup_wrong()
    {
        final TextView text = new TextView(this);
        text.setText("");

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Problem:").setMessage("something went wrong (already existing title or incomplete.")
                .setView(text).setPositiveButton("Close", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                }).create();
        dialog.show();
    }

    public void deleteTask(View view)
    {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String taskTitle = String.valueOf(taskTextView.getText());
        taskDAO.supprimer(taskTitle);
        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_add_task:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
                final View cambioView = layoutInflater.inflate(R.layout.custom_alert_dialog, null);
                dialog.setView(cambioView);

                final EditText titleEditText = (EditText) findViewById(R.id.editTextDialog);
                final EditText taskEditText = (EditText) findViewById(R.id.editTextDialog2);

                dialog.setPositiveButton("Add", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        final EditText titleEditText = (EditText) cambioView.findViewById(R.id.editTextDialog);
                        final EditText taskEditText = (EditText) cambioView.findViewById(R.id.editTextDialog2);

                        String title = titleEditText.getText().toString();
                        String task = taskEditText.getText().toString();

                        if (title.equals("") || task.equals("") || taskDAO.checkTitles(title))
                        {
                            popup_wrong();
                            dialogInterface.cancel();
                            return;
                        }
                        taskDAO.ajouter(new Task(0, title, task));
                        updateUI();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogg, int id) {
                        dialogg.cancel();
                    }
                });
                dialog.show();
                return true;

            case R.id.action_bar_about:
                Intent intent = new Intent(MainActivity.this, aboutActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
