package joe2k01.superdevelopers.keyswitch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;


public class MainActivity extends Activity
{
    Button reboot;
    int out;
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
                BufferedReader  buffered_reader=null;
                try
                {
                    buffered_reader = new BufferedReader(new FileReader("/system/build.prop"));
                    String line;

                    while ((line = buffered_reader.readLine()) != null)
                    {
                        System.out.println(line);
                        if(line.matches("qemu.hw.mainkeys=0"))
                        {
                            out = 1;
                        }
                        else
                        {
                            out = 0;
                        }
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    try
                    {
                        if (buffered_reader != null)
                        {
                            buffered_reader.close();
                        }
                        if(out == 1)
                        {
                            Log.d("key", "qemu");
                            p = Runtime.getRuntime().exec("su");
                            run = new DataOutputStream(p.getOutputStream());
                            run.writeBytes("mount -o rw,remount,rw /system\n");
                            run.flush();
                            run.writeBytes("sed -i 's/qemu.hw.mainkeys=0/#disabled/g' /system/build.prop\n");
                            run.flush();
                            run.writeBytes("reboot\n");
                            run.flush();
                        }
                        if(out == 0)
                        {
                            Log.d("key", "#disabled");
                            Log.d("key", "qemu");
                            p = Runtime.getRuntime().exec("su");
                            run = new DataOutputStream(p.getOutputStream());
                            run.writeBytes("mount -o rw,remount,rw /system\n");
                            run.flush();
                            run.writeBytes("sed -i 's/#disabled/qemu.hw.mainkeys=0/g' /system/build.prop\n");
                            run.flush();
                            run.writeBytes("reboot\n");
                            run.flush();
                        }
                    }
                    catch (IOException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            }
        });


    }


}
