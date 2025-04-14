package com.example.quizapptask;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

/**
 * This activity displays the quiz screen.
 * It shows a welcome message, a progress bar with question number, the question and answer options,
 * and a button that works as "Submit" and then "Next" after checking the answer.
 */
public class QuizQuestionActivity extends AppCompatActivity {

    // UI elements used in this activity.
    private TextView welcomeTextView, questionTextView, questionNumberTextView;
    private ProgressBar progressBar;
    private MaterialButton answerButton1, answerButton2, answerButton3, submitNextButton;

    // Data for quiz questions.
    private List<Question> questionList;
    // To track which question is currently displayed.
    private int currentQuestionIndex = 0;
    // To track the user's score.
    private int score = 0;
    // To know which answer was selected by the user. -1 means no answer selected.
    private int selectedAnswerIndex = -1;
    // To store the user's name received from MainActivity.
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        // Link our variables with the UI components in our XML file.
        welcomeTextView = findViewById(R.id.welcomeTextView);
        questionTextView = findViewById(R.id.questionTextView);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);
        progressBar = findViewById(R.id.progressBar);
        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);
        submitNextButton = findViewById(R.id.submitNextButton);

        // Retrieve the user name from the intent that started this activity.
        userName = getIntent().getStringExtra("userName");
        if (userName != null) {
            // Display a welcome message with the user's name.
            welcomeTextView.setText("Welcome " + userName + "!");
        }

        // Load our list of quiz questions.
        loadQuestions();
        // Show the first question.
        showQuestion();

        // Set click listeners for answer buttons so that the user can select an answer.
        answerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 0 is the index for the first answer.
                selectAnswer(0);
            }
        });
        answerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1 is the index for the second answer.
                selectAnswer(1);
            }
        });
        answerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 2 is the index for the third answer.
                selectAnswer(2);
            }
        });

        // Set the click listener for the Submit/Next button.
        submitNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check what the button currently says.
                String buttonText = submitNextButton.getText().toString();
                if (buttonText.equalsIgnoreCase("Submit")) {
                    if (selectedAnswerIndex == -1) {
                        // If no answer has been selected, show a message.
                        Toast.makeText(QuizQuestionActivity.this, "Please select an answer!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // If an answer is selected, check if it is correct.
                    checkAnswer();
                } else { // When button text is "Next"
                    // Move to the next question.
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questionList.size()) {
                        // Reset button colors.
                        resetAnswerButtons();
                        // Display the next question.
                        showQuestion();
                        // Change the button text back to "Submit" for the new question.
                        submitNextButton.setText("Submit");
                        // Reset the selected answer index.
                        selectedAnswerIndex = -1;
                    } else {
                        // If there are no more questions, go to the results screen.
                        Intent resultIntent = new Intent(QuizQuestionActivity.this, ResultActivity.class);
                        // Pass the user's name, score, and total questions to the results screen.
                        resultIntent.putExtra("userName", userName);
                        resultIntent.putExtra("score", score);
                        resultIntent.putExtra("totalQuestions", questionList.size());
                        startActivity(resultIntent);
                        // Close this activity so the user cannot go back.
                        finish();
                    }
                }
            }
        });
    }

    /**
    Questions & answers for the quiz
     */
    private void loadQuestions() {
        questionList = new ArrayList<>();
        // Create sample questions. The last number indicates the correct answer's index (0, 1, or 2).
        questionList.add(new Question("What is the unit code for Mobile Application Development?", "MOB305", "SIT305", "SIT726", 1));
        questionList.add(new Question("What is 3 + 2?", "5", "22", "2", 0));
        questionList.add(new Question("Which language is used for Android development?", "Ruby", "Swift", "Kotlin", 2));
        questionList.add(new Question("What do you call the “brain” of the computer?", "CPU", "RAM", "System Software", 0));
        questionList.add(new Question("What year was the first smartphone running Android released?", "2012", "2008", "1998", 1));
    }

    /**
     * Displays the current question on the screen.
     * It updates the question text, answer buttons, progress bar, and question number.
     */
    private void showQuestion() {
        // Get the current question from the list.
        Question currentQuestion = questionList.get(currentQuestionIndex);
        // Set the question text.
        questionTextView.setText(currentQuestion.getQuestionText());
        // Set the text for each answer button.
        answerButton1.setText(currentQuestion.getAnswer1());
        answerButton2.setText(currentQuestion.getAnswer2());
        answerButton3.setText(currentQuestion.getAnswer3());

        // Update the progress bar.
        progressBar.setMax(questionList.size());
        progressBar.setProgress(currentQuestionIndex + 1);

        // Display the question number as "current/total".
        questionNumberTextView.setText((currentQuestionIndex + 1) + "/" + questionList.size());

        // Show the welcome message only on the first question.
        welcomeTextView.setVisibility(currentQuestionIndex == 0 ? View.VISIBLE : View.GONE);
    }

    /**
     * Handles the user's answer selection.
     * Resets all answer buttons to their default state and then highlights the selected button.
     *
     * @param answerIndex The index (0, 1, or 2) of the answer the user selected.
     */
    private void selectAnswer(int answerIndex) {
        // Reset the background tint on all answer buttons to remove previous selection.
        resetAnswerButtons();
        // Save the user's selected answer index.
        selectedAnswerIndex = answerIndex;

        // Highlight the selected answer.
        if (answerIndex == 0) {
            answerButton1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#BBDEFB")));
        } else if (answerIndex == 1) {
            answerButton2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#BBDEFB")));
        } else if (answerIndex == 2) {
            answerButton3.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#BBDEFB")));
        }
    }


    /**
     * Resets the background tint of all answer buttons to remove any selection or feedback.
     */
    private void resetAnswerButtons() {
        answerButton1.setBackgroundTintList(null);
        answerButton2.setBackgroundTintList(null);
        answerButton3.setBackgroundTintList(null);
    }

    /**
     * Checks whether the selected answer is correct.
     * If correct, highlights the selected answer green.
     * If incorrect, highlights the selected answer red and the correct answer green.
     * Then, changes the button text to "Next".
     */
    private void checkAnswer() {
        // Get the current question.
        Question currentQuestion = questionList.get(currentQuestionIndex);
        int correctAnswerIndex = currentQuestion.getCorrectAnswerIndex();
        if (selectedAnswerIndex == correctAnswerIndex) {
            // Selected answer is correct: change its color to green.
            highlightButton(selectedAnswerIndex, Color.parseColor("#4CAF50")); // green color
            // Increase the score.
            score++;
        } else {
            // Selected answer is incorrect: change its color to red.
            highlightButton(selectedAnswerIndex, Color.parseColor("#F44336")); // red color
            // Also highlight the correct answer in green.
            highlightButton(correctAnswerIndex, Color.parseColor("#4CAF50"));
        }
        // Change the button text to "Next" so the user can go to the next question.
        submitNextButton.setText("Next");
    }

    /**
     * Changes the background tint of the answer button at the given index.
     * @param index Which answer button (0, 1, or 2) to change.
     * @param color The color to apply.
     */
    private void highlightButton(int index, int color) {
        if (index == 0) {
            answerButton1.setBackgroundTintList(ColorStateList.valueOf(color));
        } else if (index == 1) {
            answerButton2.setBackgroundTintList(ColorStateList.valueOf(color));
        } else if (index == 2) {
            answerButton3.setBackgroundTintList(ColorStateList.valueOf(color));
        }
    }

    /**
     * Inner class to store each quiz question.
     */
    static class Question {
        // Fields for question and answer choices.
        private String questionText;
        private String answer1;
        private String answer2;
        private String answer3;
        // The index (0-based) of the correct answer.
        private int correctAnswerIndex;

        // Constructor to initialise the question.
        public Question(String questionText, String answer1, String answer2, String answer3, int correctAnswerIndex) {
            this.questionText = questionText;
            this.answer1 = answer1;
            this.answer2 = answer2;
            this.answer3 = answer3;
            this.correctAnswerIndex = correctAnswerIndex;
        }

        // Getter methods.
        public String getQuestionText() { return questionText; }
        public String getAnswer1() { return answer1; }
        public String getAnswer2() { return answer2; }
        public String getAnswer3() { return answer3; }
        public int getCorrectAnswerIndex() { return correctAnswerIndex; }
    }
}
