package com.example.whatsappandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.whatsappandroid.Activities.ChatListActivity;
import com.example.whatsappandroid.Activities.RegisterActivity;
import com.example.whatsappandroid.CreatedClasses.User;
import com.example.whatsappandroid.api.UserAPI;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppUserDB db;
    private com.example.whatsappandroid.Dao.UserDao UserDao;
    private List<User> users;
    private UserAPI userAPI;

    private void errorValidation(String Title,String messageError)
    {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainActivity.this);
        builder.setMessage(messageError);
        builder.setTitle(Title);
        builder.setCancelable(false);
        builder
                .setNegativeButton(
                        "OK",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),
                AppUserDB.class, "UsersDB").allowMainThreadQueries().build();
        UserDao = db.userDao();
        users = new ArrayList<User>();
        userAPI = new UserAPI(users, UserDao);
        Button btnRegister = findViewById(R.id.RegisterButton);
        btnRegister.setOnClickListener(v -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });
        Button btnLogin = findViewById(R.id.LoginButton);
        btnLogin.setOnClickListener(v -> {
            userAPI.get();
            users.addAll(UserDao.index());
            Intent i = new Intent(this, ChatListActivity.class);
            EditText editTextUsername = findViewById(R.id.editTextUsernameLogin);
            String username = editTextUsername.getText().toString();
            EditText editTextPassword = findViewById(R.id.editTextPasswordLogin);
            String password = editTextPassword.getText().toString();
            if (username.equals("")){
                editTextPassword.setText("");
                errorValidation(getString(R.string.payAttenrion),getString(R.string.UsernameRequired));
                return;
            }
            if(password.equals("")){
                editTextUsername.setText("");
                errorValidation(getString(R.string.payAttenrion),getString(R.string.PasswordRequired) );
                return;
            }

            for(User user : users){
                if(user.getUserName()!=null && user.getUserUserName().equals(username)  && user.getPassword().equals(password)){
                    i.putExtra("Username", username);
                    startActivity(i);
                    return;
                }
            }
            //else, user or password are incorrect
            editTextPassword.setText("");
            editTextUsername.setText("");
            errorValidation(getString(R.string.payAttenrion), getString(R.string.IncorrectInPut));

        });

    }
}