package com.example.quizapptask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar; // Import the toolbar widget
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Declare UI elements
    private TextInputEditText nameEditText;
    private MaterialButton startButton;
    private Toolbar toolbar; // Declare the toolbar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link UI components.
        nameEditText = findViewById(R.id.GetName);
        startButton = findViewById(R.id.startButton);
        toolbar = findViewById(R.id.toolbar);

        // Set the toolbar as the support action bar
        setSupportActionBar(toolbar);

        // Pre-populate name if coming from a "Take New Quiz" action.
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userName")) {
            String userName = intent.getStringExtra("userName");
            nameEditText.setText(userName);
        }

        // Start button click listener.
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = nameEditText.getText().toString().trim();
                if (userName.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                } else {
                    // Create an intent to start QuizQuestionActivity and pass the userName
                    Intent quizIntent = new Intent(MainActivity.this, QuizQuestionActivity.class);
                    quizIntent.putExtra("userName", userName);
                    startActivity(quizIntent);
                }
            }
        });
    }
}
