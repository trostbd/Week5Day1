package com.example.lawre.week5day1.view.messaging;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lawre.week5day1.R;
import com.example.lawre.week5day1.model.Message;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Messaging extends AppCompatActivity
{
    public static final String MESSAGES = "messages";
    EditText etInputMessage;
    Button btSend;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference(MESSAGES);
        etInputMessage = findViewById(R.id.etInputMessage);
        btSend = findViewById(R.id.btSend);
        user = getIntent().getParcelableExtra("user");
    }

    public void onClick(View view)
    {
        String message = etInputMessage.getText().toString();
        Date today = new Date();
        String format = "hh:mm a";
        DateFormat formatForDate = new SimpleDateFormat(format);
        String time = formatForDate.format(today);
        Message newMessage = new Message(user.getEmail(),time,message);
        saveMessageToDB(newMessage);
        pushMessage(newMessage);
    }

    private void saveMessageToDB(Message message)
    {
        String key = message.getKey() != null ? message.getKey() : myRef.getKey();
        message.setKey(key);
        myRef.child(key).setValue(message);
    }

    private void pushMessage(Message message)
    {
        NotificationManager notificationManager;
        Notification.Builder notificationBuild;
        String username = message.getUsername();
        Log.d("TAG_", "pushMessage: user " + username);
        String timePlusMessage = message.getTime() + ": " + message.getText();
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,new Intent(this, Messaging.class),PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuild = new Notification.Builder(this,"413")
                    .setContentTitle(message.getUsername())
                    .setContentText(timePlusMessage)
                    .setSmallIcon(R.drawable.sburb)
                    .setContentIntent(pendingIntent);
            Log.d("TAG_", "pushMessage: " + timePlusMessage);
        }
        else
        {
            notificationBuild = new Notification.Builder(this)
                    .setContentTitle(message.getUsername())
                    .setContentText(timePlusMessage)
                    .setSmallIcon(R.drawable.sburb)
                    .setContentIntent(pendingIntent);
            Log.d("TAG_", "pushMessage: " + timePlusMessage);
        }
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("413","channel",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(413,notificationBuild.build());
    }
}
