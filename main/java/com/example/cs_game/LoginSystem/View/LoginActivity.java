/**
 * Activity for the Log In process in the game
 * Is the starting activity in the game
 *
 * Design Features
 * ----------------
 * Front end back end separation
 * Single Responsibility
 */
package com.example.cs_game.LoginSystem.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs_game.Utilities.FrontEndUtility;
import com.example.cs_game.R;

import static com.example.cs_game.LoginSystem.Presenter.LoginPresenter.authenticateCredentials;
import static com.example.cs_game.LoginSystem.Presenter.LoginPresenter.logInUser;
import static com.example.cs_game.LoginSystem.Presenter.LoginPresenter.loginStatusCheck;

public class LoginActivity extends AppCompatActivity {



    /**
     * If a user is already logged in then redirect to the main menu. If the user is trying to log
     * out handle that with the database.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Intent intent = getIntent();

        loginStatusCheck(intent, this);
    }

    /**
     * Redirects to the Register page
     */
    public void registerProcedure(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    /**
     * First checks the username, password combination for validity and availablility and either
     * logs the user in or reports the failure as a pop up
     *
     * @param view
     */
    public void loginProcedure(View view) {
        String usernameText = ((EditText) findViewById(R.id.usernameText)).getText().toString();
        String passwordText = ((EditText) findViewById(R.id.passwordText)).getText().toString();


        if (authenticateCredentials(usernameText, passwordText, this)) {
            // Do the log in process
            startActivity(logInUser(usernameText, LoginActivity.this));

        } else {
            FrontEndUtility.popUp("Username Password Combination Not Found! Try again or Sign Up", this);
        }

    }


}
