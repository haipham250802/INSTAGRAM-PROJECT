package com.example.instagram.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.Model.User;
import com.example.instagram.R;
import com.example.instagram.fragment.ProfilePragment;
import com.example.instagram.fragment.postfragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {


    private Context m_Context;
    private List<User> L_User;
    FirebaseUser firebaseUser;

    public UserAdapter(Context m_Context, List<User> L_User) {
        this.m_Context = m_Context;
        this.L_User = L_User;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(m_Context).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final User user = L_User.get(position);
        holder.FollowButton.setVisibility(View.VISIBLE);
        holder.username.setText(user.getUsername());
        holder.fullname.setText(user.getFullname());
        Glide.with(m_Context).load(user.getImgUrl()).into(holder.image_Profile);

        isFollowing(user.getId(),holder.FollowButton);

        if (user.getId().equals(firebaseUser.getUid())) {
            holder.FollowButton.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = m_Context.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("profiled",user.getId());
                editor.apply();

                ((FragmentActivity)m_Context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfilePragment()).commit();
            }
        });

        holder.FollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.FollowButton.getText().toString().equals("follow"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(user.getId()).setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId())
                            .child("followers").child(firebaseUser.getUid()).setValue(true);
                }
                else
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(user.getId()).removeValue();

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(user.getId())
                            .child("followers").child(firebaseUser.getUid()).removeValue();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return L_User.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView fullname;
        public CircleImageView image_Profile;
        public Button FollowButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameItem);
            fullname = itemView.findViewById(R.id.fullnameItem);
            image_Profile = itemView.findViewById(R.id.image_Profile);
            FollowButton = itemView.findViewById(R.id.buttonFollowItem);
        }
    }

    ;

    public void isFollowing(final String userid, Button button) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("follow")
                .child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(userid).exists())
                    button.setText("following");
                else
                    button.setText("follow");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
