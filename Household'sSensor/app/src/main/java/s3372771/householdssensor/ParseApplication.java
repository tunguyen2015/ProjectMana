package s3372771.householdssensor;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

import java.io.File;

/**
 * Created by Tan on 30/12/15.
 */
public class ParseApplication extends Application {
    @Override
    public void onCreate()
    {
        super.onCreate();
        deleteInstallationCache(this);
        Parse.initialize(this, "h60XKIwHycMWhQXHM0zaE4rIhoWwboxk72idTtAN", "7BfwAHjZFKTFVbfAuSValzVU4mxamSRoBoLvWIwR");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("temboo");
    }

    public static boolean deleteInstallationCache(Context context) {
        boolean deletedParseFolder = false;
        File cacheDir = context.getCacheDir();
        File parseApp = new File(cacheDir.getParent(),"app_Parse");
        File installationId = new File(parseApp,"installationId");
        File currentInstallation = new File(parseApp,"currentInstallation");
        if(installationId.exists()) {
            deletedParseFolder = deletedParseFolder || installationId.delete();
        }
        if(currentInstallation.exists()) {
            deletedParseFolder = deletedParseFolder && currentInstallation.delete();
        }
        return deletedParseFolder;
    }
}
