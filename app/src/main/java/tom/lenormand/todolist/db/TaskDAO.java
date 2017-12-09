package tom.lenormand.todolist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by tomle on 07/12/2017.
 */

public class TaskDAO {
    private DAOBase mDb = null;
    public static final String TABLE_NAME = "tasks";
    public static final String KEY = "id";
    public static final String INTITULE = "intitule";
    public static final String TASK = "task";
    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INTITULE + " TEXT, " + TASK + " REAL);";
    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public TaskDAO(Context context)
    {
        mDb = new DAOBase(context);
    }

    /**
     * @param m le métier à ajouter à la base
     */
    public void ajouter(Task m)
    {
        mDb.open();
        ContentValues value = new ContentValues();
        value.put(TaskDAO.INTITULE, m.getIntitule());
        value.put(TaskDAO.TASK, m.getTask());
        mDb.mDb.insert(TaskDAO.TABLE_NAME, null, value);
        mDb.close();
    }

    /**
     * supprimer dans la base de donner (utliser pour le boutton Done)
     * @param taskTitle titre du todo
     */
    public void supprimer(String taskTitle)
    {
        mDb.open();
        mDb.mDb.delete(TABLE_NAME, INTITULE + " = ?", new String[]{taskTitle});
        mDb.close();
    }

    /**
     * @param m le métier modifié
     */
    public void modifier(Task m)
    {
        mDb.open();
        ContentValues value = new ContentValues();
        value.put(TASK, m.getTask());
        mDb.mDb.update(TABLE_NAME, value, INTITULE + " = ?", new String[]{String.valueOf(m.getIntitule())});
        mDb.close();
    }
    public boolean checkTitles(String title)
    {
        mDb.open();

        Cursor cursor = mDb.mDb.rawQuery("select * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext())
        {
            int idxIntitule = cursor.getColumnIndex(INTITULE);

            if (title.equals(cursor.getString(idxIntitule)))
            {
                mDb.close();
                return (true);
            }
        }
        mDb.close();
        return (false);
    }

    public ArrayList<Task> getTaks()
    {
        ArrayList<Task>       taskList = new ArrayList<>();
        Task task;

        mDb.open();

        Cursor cursor = mDb.mDb.rawQuery("select * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext())
        {
            int idx = cursor.getColumnIndex(KEY);
            int idxIntitule = cursor.getColumnIndex(INTITULE);
            int idxTask = cursor.getColumnIndex(TASK);
            task = new Task(cursor.getLong(idx), cursor.getString(idxIntitule), cursor.getString(idxTask));
            //task = new Pair<String, String>(cursor.getString(idxIntitule), cursor.getString(idxTask));
            taskList.add(task);
        }
        cursor.close();
        mDb.close();

        return (taskList);
    }
}
