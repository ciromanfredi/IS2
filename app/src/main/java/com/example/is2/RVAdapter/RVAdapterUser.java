package com.example.is2.RVAdapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.is2.R;
import com.example.is2.javaclass.User;
import java.util.ArrayList;

public class RVAdapterUser extends RecyclerView.Adapter<RVAdapterUser.UserViewHolder>{

    public ArrayList<User> users;

    public RVAdapterUser(ArrayList<User> users){
        this.users = users;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_list_element, viewGroup, false);
        UserViewHolder pvh = new UserViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(UserViewHolder userViewHolder,final int i) {
        //userViewHolder.imageview.setImageBitmap(users.get(i).getBitmap());
        System.out.println("Sono qui"+users.get(i).getBitmap());
/*        userViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle b= new Bundle();
                b.putString("idevento", EventsFragment.getDati().get(i));
                Navigation.findNavController(v).navigate(R.id.action_navigation_event_singolo,b);


            }
        });*/
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView imageview;

        UserViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageview=itemView.findViewById(R.id.immagine_user);
        }

    }

}