package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            //Fullscreen
            if(gp.fullScreenOn == true){
                bw.write("on");
            }
            if(gp.fullScreenOn == false){
                bw.write("off");
            }
            bw.newLine();

            //Volume Music
            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            //Volume SE
            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();

            bw.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader("config.txt"));
            String line = br.readLine();

            //Fullscreen
            if (line.equals("on")) {
                gp.fullScreenOn = true;
            }
            if (line.equals("off")) {
                gp.fullScreenOn = false;
            }

            //Volume Music
            line = br.readLine();
            gp.music.volumeScale = Integer.parseInt(line);

            //Volume SE
            line = br.readLine();
            gp.se.volumeScale = Integer.parseInt(line);

            br.close();

        } catch (Exception e) {
           
        }
    }
}
