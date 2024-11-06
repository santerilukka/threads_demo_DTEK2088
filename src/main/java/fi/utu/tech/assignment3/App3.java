package fi.utu.tech.assignment3;

import java.util.ArrayList;
import java.util.List;

// Käytetään tehtässä 1 muokattua GradingTask-oliota
import fi.utu.tech.common.GradingTask;
import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;
import fi.utu.tech.common.TaskAllocator;

public class App3 {
    public static void main( String[] args )
    {
        // Otetaan funktion aloitusaika talteen suoritusajan laskemista varten
        long startTime = System.currentTimeMillis();

        // Generoidaan kasa esimerkkitehtäväpalautuksia
        List<Submission> ungradedSubmissions = SubmissionGenerator.generateSubmissions(21, 200, Strategy.STATIC);

        // Tulostetaan tiedot esimerkkipalautuksista ennen arviointia
        for (var ug : ungradedSubmissions) {
            System.out.println(ug);
        }

        // saadaan allokaattorilta GradingTask-lista
        List<GradingTask> tasks = TaskAllocator.sloppyAllocator(ungradedSubmissions);

        // Luodaan säikeet tehtäville ja käynnistetään ne
        List<Thread> threads = new ArrayList<>();
        for (GradingTask task : tasks) {
            Thread thread = new Thread(task);
            threads.add(thread);
            thread.start();
        }


        // odotetaan että kaikki säikeet ovat valmiit ennen kuin jatketaan
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Main thread was interrupted while waiting");
            }
        }

        // kun kaikki säikeet ovat valmiita, kerätään arvioidut palautukset
        System.out.println("------------ CUT HERE ------------");
        List<Submission> allGradedSubmissions = new ArrayList<>();
        for (GradingTask task : tasks) {
            allGradedSubmissions.addAll(task.getGradedSubmissions());
        }

        // tulostetaan arvioidut palautukset
        for (var gs : allGradedSubmissions) {
            System.out.println(gs);
        }

        // Lasketaan funktion suoritusaika
        System.out.printf("Total time for grading: %d ms%n", System.currentTimeMillis()-startTime);

    }
}
