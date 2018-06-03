public interface MusicPlayer {
    void play();
    void stopSong();
    void skipSong();
    void previousSong();
    void shuffleList();
    void dispose();
    void setVolume(double volume);
}
