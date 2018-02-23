import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RuleOneVerifier implements Runnable {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new RuleOneVerifier()).start();


        }
    }

    public void run() {

        try {
            File folder = new File("C:\\Users\\jehad\\Desktop\\Dax\\");
            File[] listOfFiles = folder.listFiles();
            FileWriter f0 = new FileWriter("C:\\Users\\jehad\\Desktop\\Dax\\results\\output.csv");
            String newLine = System.getProperty("line.separator");


            for (File file : listOfFiles) {
                if (file.isFile()) {
                    long startTime = System.nanoTime();
                    ArrayList<Jobs> jobs;
                    jobs = DaxParser.getDax(file.getAbsolutePath());
                    Jobs mainTweet = jobs.get(0);
                    int numberOfTocuhes = 0;

                    for (int index = 1; index < jobs.size(); index++) {
                        Jobs temp = jobs.get(index);
                        if (temp.getTweetAffected() != null && temp.getTweetAffected().equals(mainTweet.getTweetID()) &&
                                !mainTweet.existInFollowersList(temp.getUsername())) {
                            System.out.println(temp.getUsername() + " Has violated rule one policy");
                            numberOfTocuhes++;

                        }

                    }
                    System.out.println("Found " + numberOfTocuhes + " Rule one privacy policy violations");
                    long endTime = System.nanoTime();
                    long totalTime = endTime - startTime;
                    System.out.println("Execution time " + totalTime);
                    f0.write(file.getName() + ", " + totalTime + newLine);

                }

            }
            f0.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
