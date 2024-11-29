    package com.example.proyectofinal;

    import android.content.Context;
    import android.content.Intent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.CheckBox;
    import android.widget.TextView;

    import java.util.ArrayList;

    public class TaskAdapterComp extends ArrayAdapter<Task> {

        private Context context;
        private ArrayList<Task> tasks;
        private int viewResourceId;

        public TaskAdapterComp(Context context, int viewResourceId, ArrayList<Task> tasks) {
            super(context, viewResourceId, tasks);
            this.context = context;
            this.tasks = tasks;
            this.viewResourceId = viewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if (convertView == null){
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(viewResourceId, parent, false);
            }

            Task task = tasks.get(position);

            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView materia = (TextView) convertView.findViewById(R.id.subject);
            TextView fecha = (TextView) convertView.findViewById(R.id.date);
            TextView hora = (TextView) convertView.findViewById(R.id.time);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

            name.setText(task.getNombre());
            materia.setText(task.getMateria());
            fecha.setText(task.getFecha());
            hora.setText(task.getHora());
            checkBox.setChecked(task.isPrioridad());

            return convertView;
        }
    }
