    package com.example.proyectofinal;

    import android.app.Activity;
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

    public class TaskAdapter extends ArrayAdapter<Task> {

        private Context context;
        private ArrayList<Task> tasks;
        private int viewResourceId;

        public TaskAdapter(Context context, int viewResourceId, ArrayList<Task> tasks) {
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

            Button buttonSeeDetails = convertView.findViewById(R.id.buttonSeeDetails);
            Button buttonEdit = convertView.findViewById(R.id.buttonEdit);
            Button buttonComplete = convertView.findViewById(R.id.buttonComplete);

            name.setText(task.getNombre());
            materia.setText(task.getMateria());
            fecha.setText(task.getFormattedFecha());
            hora.setText(task.getFormattedHora());
            checkBox.setChecked(task.isPrioridad());

            buttonSeeDetails.setOnClickListener(v -> {
                Intent intent = new Intent(context, TaskDetailsActivity.class);
                intent.putExtra("id", task.getId());

                context.startActivity(intent);
            });

            buttonEdit.setOnClickListener(v -> {
                Intent intent = new Intent(context, EditTaskActivity.class);
                intent.putExtra("id", task.getId());

                context.startActivity(intent);
            });

            buttonComplete.setOnClickListener(v -> {
                Task updatedTask = new Task(task.getId(), task.getNombre(), task.getMateria(), task.getDescripcion(), task.getFecha(), task.getHora(), !task.isPrioridad(), true);
                TaskBDD taskBDD = new TaskBDD(context);
                taskBDD.openForWrite();
                taskBDD.updateTask(task.getId(), updatedTask);
                taskBDD.close();

                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    Intent intent = new Intent(activity, PendientesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                    activity.finish();
                }
            });

            return convertView;
        }
    }
