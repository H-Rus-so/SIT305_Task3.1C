package com.example.quizapptask;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.button.MaterialButton;
import android.widget.TextView;

/**
 * This activity shows the final results of the quiz.
 * It displays a congratulatory message with the user's name and the final score.
 * It also offers two buttons: "Take New Quiz" and "Finish".
 */
public class ResultActivity extends AppCompatActivity {

    // Declare UI elements.
    private TextView congratsTextView, finalScoreTextView;
    private MaterialButton newQuizButton, finishButton;

    // Variables to store incoming data.
    private String userName;
    private int score;
    private int totalQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for the results screen.
        setContentView(R.layout.activity_result);

        // Find UI components by their IDs.
        congratsTextView = findViewById(R.id.congratsTextView);
        finalScoreTextView = findViewById(R.id.finalScoreTextView);
        newQuizButton = findViewById(R.id.newQuizButton);
        finishButton = findViewById(R.id.finishButton);

        // Retrieve the extras from the intent.
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        score = intent.getIntExtra("score", 0);
        totalQuestions = intent.getIntExtra("totalQuestions", 0);

        // Set the congratulatory message and the final score display.
        if (userName != null) {
            congratsTextView.setText("Congratulations " + userName + "!");
        } else {
            congratsTextView.setText("Congratulations!");
        }
        finalScoreTextView.setText("Your Final Score: " + score + "/" + totalQuestions);

        // Set a click listener for the "Take New Quiz" button.
        newQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start MainActivity again, sending the userName so that it's pre-populated.
                Intent mainIntent = new Intent(ResultActivity.this, MainActivity.class);
                mainIntent.putExtra("userName", userName);
                startActivity(mainIntent);
                // End this activity.
                finish();
            }
        });

        // Set a click listener for the "Finish" button.
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Close all activities and exit the app.
                finishAffinity();
            }
        });
    }
}
