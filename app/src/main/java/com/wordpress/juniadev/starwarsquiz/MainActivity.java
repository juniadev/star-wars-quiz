package com.wordpress.juniadev.starwarsquiz;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int userScore = 0;
    private static final String FIRST_MOVIE_RELEASE_YEAR = "1977";
    private static final int NUM_QUESTIONS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hideKeyboardIfFocusChanged((EditText) findViewById(R.id.first_movie_year));
        hideKeyboardIfFocusChanged((EditText) findViewById(R.id.last_movie_original_trilogy));
    }

    public void submitQuiz(View view) {
        checkQuizAnswers();
        displayMessage();
        userScore = 0;
    }

    private void checkQuizAnswers() {
        checkStarWarsCreatorAnswer();
        checkFirstMovieYearAnswer();
        checkCharactersAnswer();
        checkPoeDameronDroidAnswer();
        checkLastMovieNameAnswer();
    }

    private void displayMessage() {
        String message;
        if (userScore == NUM_QUESTIONS) {
            message = getString(R.string.true_jedi);
        } else if (userScore > 0) {
            message = getString(R.string.almost_there, userScore, NUM_QUESTIONS);
        } else {
            message = getString(R.string.try_again);
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void checkStarWarsCreatorAnswer() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_creator);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.creator_lucas) {
            userScore++;
        }
    }

    private void checkFirstMovieYearAnswer() {
        EditText editText = (EditText) findViewById(R.id.first_movie_year);
        String answer = editText.getText().toString();
        if (FIRST_MOVIE_RELEASE_YEAR.equals(answer)) {
            userScore++;
        }
    }

    private void checkCharactersAnswer() {
        CheckBox checkBoxHan = (CheckBox) findViewById(R.id.sw_character_han);
        CheckBox checkBoxLeia = (CheckBox) findViewById(R.id.sw_character_leia);
        CheckBox checkBoxMark = (CheckBox) findViewById(R.id.sw_character_mark);
        if (checkBoxHan.isChecked() && checkBoxLeia.isChecked() && !checkBoxMark.isChecked()) {
            userScore++;
        }
    }

    private void checkPoeDameronDroidAnswer() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_droid);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.droid_bb8) {
            userScore++;
        }
    }

    private void checkLastMovieNameAnswer() {
        EditText editText = (EditText) findViewById(R.id.last_movie_original_trilogy);
        String answer = editText.getText().toString();
        if (answer != null && getString(R.string.return_of_the_jedi).toLowerCase().equals(answer.toLowerCase())) {
            userScore++;
        }
    }

    public void resetQuiz(View view) {
        ((RadioGroup) findViewById(R.id.radio_group_creator)).clearCheck();
        ((EditText) findViewById(R.id.first_movie_year)).setText("");
        ((CheckBox) findViewById(R.id.sw_character_han)).setChecked(false);
        ((CheckBox) findViewById(R.id.sw_character_leia)).setChecked(false);
        ((CheckBox) findViewById(R.id.sw_character_mark)).setChecked(false);
        ((RadioGroup) findViewById(R.id.radio_group_droid)).clearCheck();
        ((EditText) findViewById(R.id.last_movie_original_trilogy)).setText("");
        userScore = 0;
    }

    /**
     * Only show the keyboard if the field has been focused.
     * http://stackoverflow.com/a/19828165
     * @param editText
     */
    private void hideKeyboardIfFocusChanged(EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
