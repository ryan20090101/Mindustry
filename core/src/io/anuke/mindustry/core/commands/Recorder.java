package io.anuke.mindustry.core.commands;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Recorder {
    BufferedOutputStream bufferedOutputStream=null;
    int id = -10;
    int type = -10;
    String arg1 = "";
    int CONSTRUCT = 8, DECONSTRUCT = 9;

    public void initOutputStream(String filename) {
        SimpleDateFormat ft =
                new SimpleDateFormat("MM-dd_hh-ss");
        Date dNow = new Date();
        System.out.println("Current Date: " + ft.format(dNow));

        File file=new File(ft.format(dNow) + "_" +filename  +  ".txt");
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=new FileOutputStream(file);
            bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void eventSave(int _type, int _id, String _arg1, int _x, int _y){
        String sid = "";
        if (_id!=id) {
            id = _id;
            sid = id + "";
        }

        String stype = "";
        if (type != _type) {
            type = _type;
            stype = type + "";
        }

        String sarg = "";
        if (_arg1!=arg1) {
            arg1 = _arg1;
            sarg = arg1 + "";
        }
        String outLine = String.format("%s,%s,%s,%s,%s", String.valueOf(sid), String.valueOf(stype), sarg, String.valueOf(_x), String.valueOf(_y));
        saveLine(outLine);
    }

    private void saveLine(String s) {
        try {
            bufferedOutputStream.write((s+"\n").getBytes());
            bufferedOutputStream.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConstruct(int _id, String _arg1, int _x, int _y){
        eventSave(CONSTRUCT, _id,_arg1,_x,_y);
    }

    public void saveDeconstruct(int _id, String _arg1, int _x, int _y){
        eventSave(DECONSTRUCT, _id,_arg1,_x,_y);
    }

}
