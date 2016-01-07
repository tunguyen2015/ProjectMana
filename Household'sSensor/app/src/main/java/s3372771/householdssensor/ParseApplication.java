package s3372771.householdssensor;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

/**
 * Created by Tan on 30/12/15.
 */
public class ParseApplication extends Application {
    public void onCreate()
    {
        super.onCreate();
        Parse.initialize(this, "h60XKIwHycMWhQXHM0zaE4rIhoWwboxk72idTtAN", "7BfwAHjZFKTFVbfAuSValzVU4mxamSRoBoLvWIwR");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("temboo");

    }
}
