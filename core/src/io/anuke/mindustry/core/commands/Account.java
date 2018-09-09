package io.anuke.mindustry.core.commands;

import io.anuke.mindustry.entities.Player;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Account {
    public String username;
    public volatile String debug = "init";
    ArrayList<String> msgQueue;

    public Account() {
        msgQueue = new ArrayList<String>();
    }

    public boolean signedIn(){
        if (debug == "success") return true;
        return false;
    }

    public void tryLogin(final String name, final String pass){
        System.out.println("starting login thread");
       // new Thread(new Runnable() {
       //     @Override
       //     public void run() {
                System.out.println("login thread started");
                String []launchArgs = {"python3", "login.py", name, pass};
                System.out.println(launchArgs.toString());
                try {
                    Process p = Runtime.getRuntime().exec(launchArgs);
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                        if (line.contains("loginSuccess")) {
                            debug = "success";
                            username = name;
                            System.out.println(username + " signed in");
                            msgQueue.add("You signed in as " + name);
                        }
                    }
                    input.close();
                    if (debug == "init"){
                        debug = "fail";
                        System.out.println(username + "failed to signed in");
                        msgQueue.add("Sign in failed");
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
