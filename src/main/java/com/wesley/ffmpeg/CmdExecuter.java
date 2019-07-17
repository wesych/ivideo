package com.wesley.ffmpeg;
  
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.util.List;  
  
  
/** 
 * CmdExecuter 
 */  
public class CmdExecuter {  
      
    static public void exec( List<String> cmd, IStringGetter getter ){  
        try {  
            ProcessBuilder builder = new ProcessBuilder();    
            builder.command(cmd);  
            builder.redirectErrorStream(true);  
            Process proc = builder.start();  
            BufferedReader stdout = new BufferedReader(  
                    new InputStreamReader(proc.getInputStream()));  
            String line;  
            while ((line = stdout.readLine()) != null) {  
                if( getter != null )  
                    getter.dealString(line);  
            }  
            proc.waitFor();     
            stdout.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  