package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
  private static final String TAG = "QuizActivity";
  private static final String KEY_INDEX = "index";
  private static final int REQUEST_CODE_CHEAT = 0;

  private Button mTrueButton;
  private Button mFalseButton;
  private Button mNextButton;
  private Button mCheatButton;
  private TextView mQuestionTextView;
  private Question[] mQuestionBank = new Question[] {
    new Question(R.string.question_oceans, true),
    new  Question(R.string.question_mideast, false),
    new  Question(R.string.question_africa, false),
    new  Question(R.string.question_americas, true),
    new  Question(R.string.question_asia, true),
  };
  private int mCurrentIndex = 0;
  private boolean mIsCheater;

  private void updateQuestion(){
    int question = mQuestionBank[mCurrentIndex].getTextResId();
    mQuestionTextView.setText(question);
  }

  private void checkAnswer(boolean userPressedTrue){
    boolean isTrueAnswer = mQuestionBank[mCurrentIndex].isAnswerTrue();
    int messageId = 0;
    if (mIsCheater) {
      messageId = R.string.judgment_toast;
    } else {
      if (isTrueAnswer == userPressedTrue) {
        messageId = R.string.correct_toast;
      } else {
        messageId = R.string.incorrect_toast;
      }
    }
    Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // Calling the superclass implementation before you do anything else is critical in
    // onCreate(...) ; the order is less important in the other methods.
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate(Bundle) called");

    setContentView(R.layout.activity_quiz);

    mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

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

    mNextButton = (Button) findViewById(R.id.next_button);
    mNextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = ++mCurrentIndex % mQuestionBank.length;
        mIsCheater = false;
        updateQuestion();
      }
    });

    mCheatButton = (Button) findViewById(R.id.cheat_button);
    mCheatButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        // Start CheatActivity
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
        startActivityForResult(i, REQUEST_CODE_CHEAT);
      }
    });

    if (savedInstanceState != null){
      mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
    }
    updateQuestion();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    Log.i(TAG, "onSaveInstanceState");
    outState.putInt(KEY_INDEX, mCurrentIndex);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != RESULT_OK) return;
    if (REQUEST_CODE_CHEAT == requestCode) {
      if (data == null) return;
      mIsCheater = CheatActivity.wasAnswerShown(data);
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    Log.d(TAG, "onStart() called");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "onPause() called");
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "onResume() called");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.d(TAG, "onStop() called");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy() called");
  }
}
