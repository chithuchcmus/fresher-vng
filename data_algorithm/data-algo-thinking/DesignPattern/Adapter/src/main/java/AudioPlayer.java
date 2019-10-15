public class AudioPlayer implements MediaPlayer {
    MediaAdapter mediaAdapter;

    public void play(String audioType) {

        if(audioType.toLowerCase().equals("mp3")){
            System.out.println("Playing mp3 file");
        }

        else if(audioType.toLowerCase().equals("mp4")){
            mediaAdapter = new MediaAdapter(audioType);
            mediaAdapter.play(audioType);
        }

      else{
            System.out.println("Invalid media. " + audioType + " format not supported");
        }
    }
}