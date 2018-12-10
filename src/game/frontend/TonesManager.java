package game.frontend;




import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class TonesManager {

    private static final String TONE_PATH = "/tones/";
    private Map<String, Media> tones = new HashMap<>();
    private MediaPlayer mediaPlayer;

    public TonesManager() {
        try {
            tones.put("intro", new Media(getClass().getResource(TONE_PATH + "intro.mp3").toURI().toString()));
            tones.put("celebration", new Media(getClass().getResource(TONE_PATH + "celebration.mp3").toURI().toString()));
            tones.put("tasty", new Media(getClass().getResource(TONE_PATH + "tasty.mp3").toURI().toString()));
            tones.put("divine", new Media(getClass().getResource(TONE_PATH + "divine.mp3").toURI().toString()));
            tones.put("delicious", new Media(getClass().getResource(TONE_PATH + "delicious.mp3").toURI().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void playTone(String tone) {
        try {
            mediaPlayer = new MediaPlayer(tones.get(tone));
            mediaPlayer.play();
        } catch (MediaException e){
            System.out.println(tone);
        }
    }

    public void playTone(String tone, double volume, int cycles) {
        try {
            mediaPlayer = new MediaPlayer(tones.get(tone));
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setVolume(volume);
            mediaPlayer.setCycleCount(cycles);
            mediaPlayer.play();
        } catch (MediaException e) {
            System.out.println(tone);
        }
    }

}
