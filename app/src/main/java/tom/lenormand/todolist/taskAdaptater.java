package tom.lenormand.todolist;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tom.lenormand.todolist.db.Task;

/**
 * Created by tomle on 08/12/2017.
 */

public class taskAdaptater extends ArrayAdapter<Task>
{
    public taskAdaptater(Context context, List<Task> tasks)
    {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.Title = (TextView) convertView.findViewById(R.id.task_title);
            viewHolder.task = (TextView) convertView.findViewById(R.id.task_task);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Task task = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.Title.setText(task.getIntitule());
        viewHolder.task.setText(task.getTask());

        return convertView;
    }

    private class TweetViewHolder{
        public TextView Title;
        public TextView task;
    }
}
