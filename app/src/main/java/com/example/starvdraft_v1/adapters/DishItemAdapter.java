package com.example.starvdraft_v1.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.holders.DishItemViewHolder;
import com.example.starvdraft_v1.models.DishItem;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.recyclerview.widget.RecyclerView;

public class DishItemAdapter extends RecyclerView.Adapter<DishItemViewHolder> {

    private Context context;
    private ArrayList<DishItem> items;
    private DishItemViewHolder.OnItemClickListener listenner;

    public DishItemAdapter(ArrayList<DishItem> items) {
        this.items = items;
    }

    public void setOnItemClickListener(DishItemViewHolder.OnItemClickListener listener){
        this.listenner = listener;
    }

    @Override
    public DishItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemview = LayoutInflater.from(context).inflate(R.layout.card_dish_element_test,parent,false);
        return new DishItemViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(DishItemViewHolder holder, int position) {
        holder.updateUI(items.get(position));
        //Y aquí, mi amigo, comienzan todos los listener's

        holder.ivMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listenner!=null){
                    int position = holder.getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listenner.onRetirePortionClick(position);
                    }
                }
            }
        });

        holder.ivMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listenner!=null){
                    int position = holder.getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listenner.onAddPortionClick(position);
                    }
                }
            }
        });

        holder.ivRecordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                final Calendar calendario = Calendar.getInstance();
                int dia = calendario.get(Calendar.DAY_OF_MONTH);
                int mes = calendario.get(Calendar.MONTH);
                int anio = calendario.get(Calendar.YEAR);
                final String titulo = items.get(holder.getAdapterPosition()).titulo;


                //Toast.makeText(context, dia+"/"+mes+"/"+anio, Toast.LENGTH_SHORT).show();

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendario.set(year, month, dayOfMonth);
                        //Toast.makeText(context, calendario.get(Calendar.DAY_OF_MONTH) + "/" + calendario.get(Calendar.MONTH) + "/" + calendario.get(Calendar.YEAR), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_INSERT)
                                .setData(CalendarContract.Events.CONTENT_URI)
                                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendario.getTimeInMillis())
                                .putExtra(CalendarContract.Events.TITLE, titulo);
                        context.startActivity(intent);
                    }
                }, anio, mes, dia);

                datePickerDialog.show();

            }
        });

        holder.ibRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listenner!=null){
                    int position = holder.getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listenner   .onRemoveClick(position);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}

