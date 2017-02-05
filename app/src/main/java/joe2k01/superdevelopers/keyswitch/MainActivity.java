package joe2k01.superdevelopers.keyswitch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends Activity
{
    Button reboot;
    java.lang.Process p;
    DataOutputStream run;

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
                    p = Runtime.getRuntime().exec("su");
                    run = new DataOutputStream(p.getOutputStream());
                    run.writeBytes("mount -o rw,remount,rw /system\n");
                    run.flush();
                    run.writeBytes("sed -i 's/qemu.hw.mainkeys=0/#qemu.hw.mainkeys=0/g' /system/build.prop\n");
                    run.flush();
                    run.writeBytes("reboot\n");
                    run.flush();
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });


    }

}
