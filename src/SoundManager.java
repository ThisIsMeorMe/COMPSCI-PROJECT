import java.io.File;

public class SoundManager
{
    private static Process backgroundMusicProcess = null;

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
        try
        {
            if (backgroundMusicProcess != null)
            {
                try
                {
                    backgroundMusicProcess.destroy();
                }
                catch (Exception e)
                {
                }
            }

            File soundFile = findSoundFile("assets/sounds/BackgroundMusic.mp3");
            if (soundFile == null)
            {
                System.err.println("Background music file not found");
                return;
            }

            System.out.println("Found background music at: " + soundFile.getAbsolutePath());

            String[][] commands =
            {
                {"pw-play", soundFile.getAbsolutePath()},
                {"mpg123", "-q", soundFile.getAbsolutePath()},
                {"ffplay", "-nodisp", "-autoexit", "-loop", "0", soundFile.getAbsolutePath()},
                {"paplay", soundFile.getAbsolutePath()}
            };

            for (String[] cmd : commands)
            {
                try
                {
                    backgroundMusicProcess = new ProcessBuilder(cmd).start();
                    System.out.println("Playing background music with: " + cmd[0]);
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
    }

    public static void playClickSound()
    {
        playSound("assets/sounds/ClickSound.mp3");
    }

    public static void playSellSound()
    {
        playSound("assets/sounds/SellSound.mp3");
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

                String[][] commands =
                {
                    {"pw-play", soundFile.getAbsolutePath()},
                    {"mpg123", "-q", soundFile.getAbsolutePath()},
                    {"ffplay", "-nodisp", "-autoexit", soundFile.getAbsolutePath()},
                    {"paplay", soundFile.getAbsolutePath()}
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
        if (backgroundMusicProcess != null)
        {
            try
            {
                backgroundMusicProcess.destroy();
            }
            catch (Exception e)
            {
            }
            backgroundMusicProcess = null;
        }
    }
}
