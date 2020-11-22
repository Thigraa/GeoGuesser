package cat.itb.geoguesser;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {
    TextView question_text, progress_text;
    Button option1, option2,option3, option4, hint_button;
    ProgressBar timerBar;
    private int number = 0;
    private int i=0;
    int time = 20000;
    int hint = 3;
    private double score = 0;
    CountDownTimer timer;
    QuestionModel[] questionBank = {
            new QuestionModel(R.string.q1, "Central America", "Asia", "Africa", "South America", "Asia"),
            new QuestionModel(R.string.q2,"Slovenia", "Serbia", "Chipre", "Egipt", "Serbia"),
            new QuestionModel(R.string.q3, "Amazonas", "Nile", "Yellow River", "Yangtze","Nile" ),
            new QuestionModel(R.string.q4,"China", "Russia", "India", "Indonesia", "India"),
            new QuestionModel(R.string.q5, "Britain", "Canada", "Russia", "Mexico", "Britain"),
            new QuestionModel(R.string.q6, "5", "6", "7", "11", "6"),
            new QuestionModel(R.string.q7, "11", "5", "7", "6", "5"),
            new QuestionModel(R.string.q8, "Argelia", "Democratic Republic of Congo", "Tanzania", "Namibia", "Argelia"),
            new QuestionModel(R.string.q9, "North America", "South America", "Antartica", "Asia", "Antartica"),
            new QuestionModel(R.string.q10, "Sardinia", "Chipre", "Sicily", "Majorca", "Sicily"),
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hint_button = findViewById(R.id.hint_button);
        Collections.shuffle(Arrays.asList(questionBank));
        question_text = findViewById(R.id.question_text);
        progress_text = findViewById(R.id.progress_text);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        timerBar = findViewById(R.id.progressBar);
        progress_text.setText("Question " + (number+1) + " of " + questionBank.length);
        option1.setText(questionBank[0].getAnswer1());
        option2.setText(questionBank[0].getAnswer2());
        option3.setText(questionBank[0].getAnswer3());
        option4.setText(questionBank[0].getAnswer4());
        question_text.setText(questionBank[0].getQuestionText());


        timerBar.setProgress(i);

        timer=new CountDownTimer(time,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                i++;
                timerBar.setProgress((int)i*100/(time/1000));

            }

            @Override
            public void onFinish() {
                if(number+1 < questionBank.length){
                    refresh();
                }
                else{
                    restartQuiz().show();
                }
                score-=0.5;
            }
        };
        timer.start();
        option1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                comprovarResposta(option1.getText().toString());
                if(number+1<questionBank.length) {
                    refresh();
                }
                else{
                    restartQuiz().show();
                }
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                comprovarResposta(option2.getText().toString());
                if(number+1<questionBank.length) {
                    refresh();
                }else{
                    restartQuiz().show();

                }
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                comprovarResposta(option3.getText().toString());
                if(number+1<questionBank.length) {
                    refresh();
                }
                else{
                    restartQuiz().show();
                }
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                comprovarResposta(option4.getText().toString());
                if(number+1<questionBank.length) {
                    refresh();
                }
                else{
                    restartQuiz().show();
                }
            }
        });
        hint_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                hint--;
                Toast.makeText(MainActivity.this, "You still have "+hint+ " hints", Toast.LENGTH_SHORT).show();
                if(hint == 0) {
                    hint_button.setVisibility(View.INVISIBLE);
                }
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public void refresh () {
        number++;
        question_text.setText(questionBank[number].getQuestionText());
        option1.setText(questionBank[number].getAnswer1());
        option2.setText(questionBank[number].getAnswer2());
        option3.setText(questionBank[number].getAnswer3());
        option4.setText(questionBank[number].getAnswer4());
        progress_text.setText("Question " + (number+1) + " of " + questionBank.length);
        i = 0;
        timerBar.setProgress(i);
        timer.cancel();
        timer.start();

    }


    public Dialog restartQuiz(){
        timer.cancel();
        AlertDialog.Builder restart = new AlertDialog.Builder(this);
        restart.setTitle("Congratulations, you finished the quiz!");
        restart.setMessage("Score: "+score*10+"/100");
        restart.setPositiveButton(R.string.EXIT, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        restart.setNegativeButton(R.string.RESTART, new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                number = -1;
                hint = 3;
                hint_button.setVisibility(View.VISIBLE);
                score = 0;
                Collections.shuffle(Arrays.asList(questionBank));
                refresh();
            }
        });
        return restart.create();
    }

    public void comprovarResposta(String resposta){
        if (resposta.equals(questionBank[number].getAnswer()) ) {
            Toast.makeText(this, "CORRECT", Toast.LENGTH_SHORT).show();
            score++;
        } else {
            Toast.makeText(this, "INCORRECT", Toast.LENGTH_SHORT).show();
            score-= 0.5;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("number", number);
    }
}
