/**
 * Activity responsible for Registration of users front end
 *
 * Design Features
 * -----------------------
 * Separation of front end and back end
 * Single Responsibility
 *
 */
package com.example.cs_game.LoginSystem.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs_game.R;

import static com.example.cs_game.LoginSystem.Presenter.LoginPresenter.logInUser;
import static com.example.cs_game.LoginSystem.Presenter.RegisterPresenter.RegisterChecks;
import static com.example.cs_game.LoginSystem.Presenter.RegisterPresenter.RegisterUser;

public class RegisterActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    /**
     * Checks validity and availability of username and password. If legitimate a new user is created
     * Redirects to main menu
     *
     * @param view
     */
    public void RegisterAttempt(View view) {
        String usernameText = ((EditText) findViewById(R.id.usernameText)).getText().toString();
        String passwordText = ((EditText) findViewById(R.id.passwordText)).getText().toString();
        String confirmedPasswordText = ((EditText) findViewById(R.id.confirmPasswordText)).getText().toString();


        if (RegisterChecks(usernameText, passwordText, confirmedPasswordText, this)){
            RegisterUser(usernameText, passwordText, this);
            // Log in logic
            startActivity(logInUser(usernameText, RegisterActivity.this));
        }
    }

    /**
     * Redirects to the log in page
     *
     * @param view
     */
    public void loginProcedure(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

    }


}
