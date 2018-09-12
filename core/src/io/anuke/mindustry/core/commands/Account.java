package io.anuke.mindustry.core.commands;

import io.anuke.mindustry.entities.Player;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Account {
    public String username;
    public volatile String debug = "init";
    Player player;
    public Account(Player pself) {
        player = pself;
    }

    public boolean signedIn(){
        if (debug.contains("loginSuccess")) return true;
        return false;
    }

    public void tryLogin(final String name, final String pass, final String uuid, final String address){
        System.out.println("starting login thread");
       // new Thread(new Runnable() {
       //     @Override
       //     public void run() {
                System.out.println("login thread started");
                String []launchArgs = {"python3", "login.py", name, pass, address, uuid};
                System.out.println("python3 login.py " + name + " " + pass + " " + address + " " + uuid);
                System.out.println(launchArgs.toString());
                try {
                    Process p = Runtime.getRuntime().exec(launchArgs);
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                        if (line.contains("loginSuccess")) {
                            //debug = "success";
                            debug = line;
                            String []split = line.split(" ");
                            if (split.length>1){
                                username = split[1];
                            }
                            else
                                username = name;
                            System.out.println(username + " signed in");
                            //player.setTeam(Team);
                            //msgQueue.add("You signed in as " + name);
                        }
                    }
                    input.close();
                    if (debug == "init"){
                        debug = "fail";
                        System.out.println(username + "failed to signed in");
                        //msgQueue.add("Sign in failed");
                        //Call.sendMessage(player.con.id, "Login failed, bad password?");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            //}
       // }
       // ).start();
    }
}
