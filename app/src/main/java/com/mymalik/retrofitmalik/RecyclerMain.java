package com.mymalik.retrofitmalik;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mymalik.retrofitmalik.databinding.RecyclerItemBinding;

import java.util.ArrayList;

public class RecyclerMain extends RecyclerView.Adapter<RecyclerMain.MyHolder> {

    ArrayList<MyJsonObject> data ;

    MyListener listener ;

    public RecyclerMain(ArrayList<MyJsonObject> data, MyListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        RecyclerItemBinding binding ;

        MyJsonObject object ;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            binding = RecyclerItemBinding.bind(itemView);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onListener(object);
                }
            });

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onListener(object);
                }
            });
        }

        void bind(MyJsonObject object){
            this.object = object ;
            //
            int dd= 1;
            //
            binding.etName.setText(object.getName());
            binding.etEmail.setText(object.getEmail());
        }
    }
}
