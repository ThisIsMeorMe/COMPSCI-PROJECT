import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager
{
    private static Clip backgroundMusicClip = null;
    private static Thread backgroundMusicThread = null;

    private static File findSoundFile(String filename)
    {
        File f = new File(filename);
        if (f.exists()) return f;

        f = new File("/home/tejaswinipereira/FinaEDITPROB/" + filename);
        if (f.exists()) return f;

        f = new File("./" + filename);
        if (f.exists()) return f;

        return null;
    }

    public static void playBackgroundMusic()
    {
        stopBackgroundMusic();
        
        backgroundMusicThread = new Thread(() ->
        {
            try
            {
                File soundFile = findSoundFile("assets/sounds/BackgroundMusic.mp3");
                if (soundFile == null)
                {
                    System.err.println("Background music file not found");
                    return;
                }

                System.out.println("Found background music at: " + soundFile.getAbsolutePath());

                // Try using Java's audio system for WAV files
                File wavFile = findSoundFile("assets/sounds/BackgroundMusic.wav");
                if (wavFile != null)
                {
                    System.out.println("Using WAV file for background music");
                    playWAVLooping(wavFile);
                    return;
                }

                // Fallback to external players for MP3
                String[][] commands =
                {
                    {"powershell", "-NoProfile", "-Command", "& {Add-Type -AssemblyName PresentationCore; $player = [System.Media.SoundPlayer]::new('" + soundFile.getAbsolutePath() + "'); $player.PlayLooping(); while($true) { Start-Sleep -Seconds 1 }}"},
                    {"ffplay", "-nodisp", "-autoexit", "-loop", "0", soundFile.getAbsolutePath()},
                    {"mpg123", "-q", soundFile.getAbsolutePath()}
                };

                for (String[] cmd : commands)
                {
                    try
                    {
                        Process process = new ProcessBuilder(cmd).start();
                        System.out.println("Playing background music with: " + cmd[0]);
                        process.waitFor();
                        return;
                    }
                    catch (Exception e)
                    {
                    }
                }

                System.err.println("No suitable audio player found");
            }
            catch (Exception e)
            {
                System.err.println("Error playing background music: " + e.getMessage());
                e.printStackTrace();
            }
        });
        
        backgroundMusicThread.setDaemon(true);
        backgroundMusicThread.start();
    }

    private static void playWAVLooping(File wavFile) throws Exception
    {
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(wavFile);
        backgroundMusicClip = AudioSystem.getClip();
        backgroundMusicClip.open(audioStream);
        backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        System.out.println("Background music playing (looped)");
        
        // Keep the thread alive while music plays
        try
        {
            Thread.sleep(Long.MAX_VALUE);
        }
        catch (InterruptedException e)
        {
        }
    }

    public static void playClickSound()
    {
        playSound("assets/sounds/ClickSound.wav");
    }

    public static void playSellSound()
    {
        playSound("assets/sounds/SellSound.wav");
    }

    private static void playSound(String filePath)
    {
        new Thread(() ->
        {
            try
            {
                File soundFile = findSoundFile(filePath);
                if (soundFile == null)
                {
                    System.err.println("Sound file not found: " + filePath);
                    return;
                }

                System.out.println("Playing sound: " + soundFile.getAbsolutePath());

                // Try Java audio system first for WAV
                if (soundFile.getName().endsWith(".wav"))
                {
                    try
                    {
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioStream);
                        clip.start();
                        
                        // Wait for the clip to finish
                        Thread.sleep(clip.getMicrosecondLength() / 1000 + 100);
                        clip.close();
                        return;
                    }
                    catch (Exception e)
                    {
                        System.out.println("Could not play WAV with Java audio: " + e.getMessage());
                    }
                }

                // Fallback to external players
                String[][] commands =
                {
                    {"powershell", "-NoProfile", "-Command", "& {Add-Type -AssemblyName PresentationCore; [System.Media.SoundPlayer]::new('" + soundFile.getAbsolutePath() + "').PlaySync()}"},
                    {"ffplay", "-nodisp", "-autoexit", soundFile.getAbsolutePath()},
                    {"mpg123", "-q", soundFile.getAbsolutePath()}
                };

                for (String[] cmd : commands)
                {
                    try
                    {
                        Process process = new ProcessBuilder(cmd).start();
                        process.waitFor();
                        return;
                    }
                    catch (Exception e)
                    {
                    }
                }

                System.err.println("No suitable audio player found");
            }
            catch (Exception e)
            {
                System.err.println("Error playing sound: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    public static void stopBackgroundMusic()
    {
        if (backgroundMusicClip != null)
        {
            try
            {
                backgroundMusicClip.stop();
                backgroundMusicClip.close();
            }
            catch (Exception e)
            {
            }
            backgroundMusicClip = null;
        }
        
        if (backgroundMusicThread != null && backgroundMusicThread.isAlive())
        {
            try
            {
                backgroundMusicThread.interrupt();
            }
            catch (Exception e)
            {
            }
        }
    }
}
