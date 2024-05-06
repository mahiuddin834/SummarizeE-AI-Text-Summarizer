package com.itnation.summarizee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.itnation.summarizee.AiModel.AiModel;
import com.itnation.summarizee.AiModel.ResponseCallback;

public class MainActivity extends AppCompatActivity {


    EditText maxSize, minSize, inputTxt;
    Button sumBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Window window = MainActivity.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.primary));

        maxSize = findViewById(R.id.maxSize);
        minSize = findViewById(R.id.minSize);
        inputTxt = findViewById(R.id.inputTxt);
        sumBtn = findViewById(R.id.sumBtn);
        progressBar=findViewById(R.id.progressBar);


        sumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (inputTxt.getText().toString().length() >= 5) {

                    minWord = minSize.getText().toString();
                    maxWord = maxSize.getText().toString();
                    mainTxt = inputTxt.getText().toString();

                    summarizeText();


                } else {
                    inputTxt.setError("Please Enter Your Text");
                }


            }
        });


    }// close onCreate ---------------------------------

    ProgressBar progressBar;

    String minWord;
    String maxWord;
    String mainTxt;

    void summarizeText() {


        progressBar.setVisibility(View.VISIBLE);

        String sumQuery = "Summarize the following text in " + minWord + " - " + maxWord + " words: \"" + mainTxt + "\" . ";

        Toast.makeText(this, sumQuery, Toast.LENGTH_SHORT).show();


        AiModel model = new AiModel();

        model.getResponse(sumQuery, new ResponseCallback() {
            @Override
            public void onResponse(String response) {


                progressBar.setVisibility(View.GONE);


                Intent intent = new Intent(MainActivity.this, OutputActivity.class);
                intent.putExtra("resultTxt", response);
                startActivity(intent);


            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(MainActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });


    }


}