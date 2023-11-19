package com.jose.mymusicaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class MenuInicial extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    Button cambiarCancion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);
        cambiarCancion = findViewById(R.id.button_cambiar);
        mediaPlayer = MediaPlayer.create(this, R.raw.juego);
        AudiMediaPlayer();

        cambiarCancion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                mediaPlayer.stop();
                //
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verificar si la selección de música fue exitosa
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Obtener la URI de la canción seleccionada
            String selectedSong = data.getData().toString();

            // Actualizar el MediaPlayer con la nueva canción
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer = MediaPlayer.create(this, Uri.parse(selectedSong));
            AudiMediaPlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Pausar la reproducción cuando la aplicación pasa a segundo plano
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
    public void AudiMediaPlayer(){
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


    }
    @Override
    protected void onResume() {
        super.onResume();
        // Reanudar la reproducción cuando la aplicación vuelve al primer plano
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    //liberar recursos
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.release();
        }
    }

}