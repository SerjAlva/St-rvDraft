package com.example.starvdraft_v1.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.starvdraft_v1.R;
import com.example.starvdraft_v1.activities.DishListActivity;
import com.example.starvdraft_v1.holders.DishItemViewHolder;
import com.example.starvdraft_v1.models.DishItem;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class DishItemAdapter extends RecyclerView.Adapter<DishItemViewHolder> {

    private Context context;
    private ArrayList<DishItem> items;
    private DishListActivity.OnItemClickListener listenner;

    public DishItemAdapter(ArrayList<DishItem> items) {
        this.items = items;
    }

    /*public void setOnItemClickListener(DishListActivity.OnItemClickListener listener){
        this.listenner = listener;
    }*/

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
                int porciones = Integer.parseInt(items.get(position).porciones);
                if(porciones == 1){
                    Toast.makeText(context, context.getString(R.string.retirar_porcion_error), Toast.LENGTH_SHORT).show();
                }else{
                    items.get(position).porciones = String.valueOf(porciones-1);
                    items.get(position).precioMostrador = items.get(position).precio*porciones;
                    holder.tvPrecio.setText(String.valueOf(items.get(position).precioMostrador));
                    notifyItemChanged(position);
                }
            }
        });

        holder.ivMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int porciones = Integer.parseInt(items.get(position).porciones);
                items.get(position).porciones = String.valueOf(porciones + 1);
                items.get(position).precioMostrador = items.get(position).precio*porciones;
                holder.tvPrecio.setText(String.valueOf(items.get(position).precioMostrador));
                notifyItemChanged(position);
            }
        });

        holder.ibRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pregunta = context.getString(R.string.onRemove1);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(pregunta + items.get(position).titulo + "?")
                        .setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                items.remove(position);
                                notifyItemRemoved(position);
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                //AlertDialog dialog = builder.create();
                try {
                    builder.show();
                }catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
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


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}

