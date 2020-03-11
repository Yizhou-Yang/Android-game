/**
 * Utility class used to store all methods that are commonly used to display messages/ other front
 * end interactions
 * Design Features
 * --------------------
 * Single Responsibility
 * Keeping code cleaner and less dense
 */
package com.example.cs_game.Utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cs_game.R;

import java.io.IOException;


public abstract class FrontEndUtility {

    /**
     * Toast pop up message with a predetermined duration
     *
     * @param text    the message to show on the screen
     * @param context the screen we want to show on
     */
    public static void popUp(CharSequence text, Context context) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Alert Dialog
     *
     * @param title   Title of dialog
     * @param message The message to show on dialog
     * @param context the screen to show dialog on
     */
    public static void dialog(String title, String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        displayDialog(builder, title, message, true);
    }

    /**
     * Alert dialog with list
     *
     * @param title    Title of dialog
     * @param items    the list of selectable options to show on dialog
     * @param listener listener for when user selections one of the options on the list
     * @param context  the screen to show dialog on
     */
    public static void listDialog(String title, CharSequence[] items,
                                  DialogInterface.OnClickListener listener, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(items, listener);
        displayDialog(builder, title, null, true);

    }

    /**
     * Dialog with positive button option
     *
     * @param title    Title of dialog
     * @param message  The message to show on dialog
     * @param context  the screen to show dialog on
     * @param listener listener for when user presses the positive button
     */
    public static void dialogWithOption(String title, String message, Context context,
                                        DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Proceed", listener);
        displayDialog(builder, title, message, false);
    }

    /**
     * Dialog with different cancelable options
     *
     * @param builder    Dialog builder
     * @param title      Title of the dialog
     * @param message    The message to show on dialog
     * @param cancelable determines if user can escape dialog
     */
    private static void displayDialog(AlertDialog.Builder builder,
                                      String title, String message, boolean cancelable) {
        if (title != null) builder.setTitle(title);
        if (message != null) builder.setMessage(message);
        builder.setCancelable(cancelable);
        builder.create().show();
    }

    /**
     * As of now is only raw string equality, but can easily be expanded to other forms of verification
     *
     * @param password          the first password user types
     * @param confirmedPassword the second string to confirm password
     * @return true iff password is same as confirmedPassword
     */
    public static boolean passWordMatching(String password, String confirmedPassword) {
        return password.equals(confirmedPassword);
    }

    /**
     * A set of basic checks on the validity of the username and password (e.g length non 0)
     * <p>
     * Does not check the database for whether the username is available
     *
     * @param username
     * @param password
     * @return true iff valid username and password
     */
    public static boolean legitimateCredentials(String username, String password) {
        // Checks all the simple logic of being a valid password and a valid username.

        return (username.length() > 0 && password.length() > 0);
    }
}
