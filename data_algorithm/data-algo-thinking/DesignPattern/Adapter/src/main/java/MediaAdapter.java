public class MediaAdapter implements MediaPlayer {

    NewMediaPlayer mediaPlayer;

    public MediaAdapter(String audioType){
        if (audioType.toLowerCase().equals("mp4"))
        {
            mediaPlayer = new Mp4Player();
        }

    }
    public void play(String audioType)
    {
        if (audioType.toLowerCase().equals("mp4"))
        {
            mediaPlayer.playMp4();
        }
    }
}