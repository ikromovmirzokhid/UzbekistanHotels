package com.example.imb.uzbekistanhotels.async;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSide extends AsyncTask<String, Void, Void> {
    private Socket s;
    private DataOutputStream out;
    @Override
    protected Void doInBackground(String... strings) {
        try {
            s = new Socket("192.168.21.254", 9090);
            out = new DataOutputStream(s.getOutputStream());
            out.writeUTF(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
