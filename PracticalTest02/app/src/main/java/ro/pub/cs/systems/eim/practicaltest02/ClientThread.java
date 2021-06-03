package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.practicaltest02.Constants;
import ro.pub.cs.systems.eim.practicaltest02.Utilities;

public class ClientThread extends Thread {

    private String address;
    private int port;
    private String currency;
    private TextView conversionTextView;

    private Socket socket;

    public ClientThread(String address, int port, String currency, TextView conversionTextView) {
        this.address = address;
        this.port = port;
        this.currency = currency;
        this.conversionTextView = conversionTextView;
    }

    public ClientThread(String currency, TextView conversionTextView) {
        this.currency = currency;
        this.conversionTextView = conversionTextView;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            Log.d(Constants.TAG, "[CLIENT THREAD] Writing Currency..." + currency);
            printWriter.println(currency);
            printWriter.flush();
            String conversionResult;
            while ((conversionResult = bufferedReader.readLine()) != null) {
                final String finalizedConversionResult = conversionResult;
                conversionTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        conversionTextView.setText(finalizedConversionResult);
                    }
                });
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                }
            }
        }
    }

}
