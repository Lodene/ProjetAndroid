package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import java.util.concurrent.ArrayBlockingQueue;
import android.view.View;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity implements UdpClient.OnDataReceivedListener {
    private Button bt;
    private EditText tp;

    private UdpClient udpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        udpClient = new UdpClient(this, this); // Instanciation de UdpClient avec l'interface en tant que listener

        bt = findViewById(R.id.sendButton);
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        MyThread thread = new MyThread(queue, udpClient);
        thread.start();

        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                tp = findViewById(R.id.editTextId);
                String command = tp.getText().toString();

                if (command.equals("getData()")) {
                    // Si la commande est "getData()", vous pouvez réagir en conséquence
                    // Par exemple, vous pourriez vouloir envoyer la commande au serveur UDP
                    // et attendre la réponse, puis mettre à jour l'UI avec les données reçues.
                    udpClient.sendMessage(command);
                } else {
                    // Si la commande n'est pas "getData()", ajoutez-la à la file d'attente comme avant.
                    queue.add(command);
                }
            }
        });

        udpClient.startListening(); // Démarrer l'écoute UDP
    }

    // Méthode de l'interface pour traiter les données reçues
    @Override
    public void onDataReceived(String data) {
        Log.d("UDP", "Received data: " + data); // Ajoutez cette ligne
        runOnUiThread(() -> {
            TextView receivedDataTextView = findViewById(R.id.receivedDataTextView);
            receivedDataTextView.setText(data);
        });
    }
}
