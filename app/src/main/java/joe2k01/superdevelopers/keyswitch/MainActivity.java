package joe2k01.superdevelopers.keyswitch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity
{
    Button reboot;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reboot = (Button) findViewById(R.id.btn_reboot);
        reboot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Runtime.getRuntime().exec("su");
                    Runtime.getRuntime().exec("reboot");
                }
                catch (IOException e)
                {
                    Toast.makeText(getApplicationContext(),"Phone not Rooted",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
