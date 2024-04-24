package com.example.todo_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todo_app.R;
import com.example.todo_app.TaskAdapter;

import java.util.ArrayList;
import java.util.List;

public class ToDoArrayAdapter extends ArrayAdapter<TaskAdapter> {

    private Context mContext;
    int mResource;

    public ToDoArrayAdapter(Context context, int resource, ArrayList<TaskAdapter> objects) {
        super(context, resource, (List<TaskAdapter>) objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String heading = getItem(position).getHeading();
        String description = getItem(position).getDescription();
        String datedo = getItem(position).getTaskdatedo();
        Boolean done = getItem(position).getTaskdone();

        LayoutInflater inflater =LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvHeading = (TextView) convertView.findViewById(R.id.GroupHeadText);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.DescTextView);
        TextView tvDate = (TextView) convertView.findViewById(R.id.DateTextView);
        CheckBox cbDone = (CheckBox) convertView.findViewById(R.id.checkBox);

        tvHeading.setText(heading);
        tvDescription.setText(description);
        tvDate.setText(datedo);
        cbDone.setChecked(done);

        return convertView;
    }
}
