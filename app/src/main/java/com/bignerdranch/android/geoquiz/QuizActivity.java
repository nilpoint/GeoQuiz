package com.bignerdranch.android.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
  private Button mTrueButton;
  private Button mFalseButton;
  private ImageButton mNextButton;
  private ImageButton mPrevButton;
  private TextView mQuestionTextView;
  private Question[] mQuestionBank = new Question[] {
    new Question(R.string.question_oceans, true),
    new  Question(R.string.question_mideast, false),
    new  Question(R.string.question_africa, false),
    new  Question(R.string.question_americas, true),
    new  Question(R.string.question_asia, true),
  };
  private int mCurrentIndex = 0;

  private void updateQuestion(){
    int question = mQuestionBank[mCurrentIndex].getTextResId();
    mQuestionTextView.setText(question);
  }

  private void checkAnswer(boolean userPressedTrue){
    boolean isTrueAnswer = mQuestionBank[mCurrentIndex].isAnswerTrue();
    int messageId;
    if (isTrueAnswer == userPressedTrue){
      messageId = R.string.correct_toast;
    } else {
      messageId = R.string.incorrect_toast;
    }
    Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
    mQuestionTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.i("TextView Clicked", "Question text view is clicked.");
        mCurrentIndex = ++mCurrentIndex % mQuestionBank.length;
        updateQuestion();
      }
    });

    mTrueButton = (Button) findViewById(R.id.true_button);
    mTrueButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.i("Button Clicked", "True button is clicked.");
        checkAnswer(true);
      }
    });

    mFalseButton = (Button) findViewById(R.id.false_button);
    mFalseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.i("Button Clicked", "False button is clicked.");
        checkAnswer(false);
      }
    });

    mNextButton = (ImageButton) findViewById(R.id.next_button);
    mNextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = ++mCurrentIndex % mQuestionBank.length;
        updateQuestion();
      }
    });

    mPrevButton = (ImageButton) findViewById(R.id.prev_button);
    mPrevButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = --mCurrentIndex > 0 ? mCurrentIndex : 0;
        mCurrentIndex = mCurrentIndex % mQuestionBank.length;
        updateQuestion();
      }
    });

    updateQuestion();
  }
}
