package fi.utu.tech.assignment6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Käytetään tehtässä 1 muokattua GradingTask-oliota
import fi.utu.tech.common.GradingTask;
// Allokointifunktiot implementoidaan TaskAllocator-luokassa
import fi.utu.tech.common.TaskAllocator;

import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class App6 {
    public static void main(String[] args) {
        // Otetaan funktion aloitusaika talteen suoritusaikaa varten
        long startTime = System.currentTimeMillis();

        // Generoidaan esimerkkipalautukset
        List<Submission> ungradedSubmissions = SubmissionGenerator.generateSubmissions(20, 200, Strategy.STATIC);

        // Tulostetaan esimerkkipalautukset ennen arviointia
        for (var ug : ungradedSubmissions) {
            System.out.println(ug);
        }

        // Asetetaan haluttu säikeitten määrä
        int threadCount = 10;

        // Allokoidaan palautukset GradingTaskille
        List<GradingTask> tasks = TaskAllocator.allocate(ungradedSubmissions, threadCount);

        // Luodaan FixedThreadPool ExecutorService
        ExecutorService executor = newFixedThreadPool(threadCount);

        // Lähetetään kaikki GradingTask oliot suoritettavaksi
        for (GradingTask task : tasks) {
            executor.execute(task);
        }

        // ExecutorService odottaa tehtävien valmistumista
        executor.shutdown();
        try {
            // Odotetaan maksimissaan 60sek, että kaikki tehtävät valmistuvat
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.err.println("Some grading tasks did not complete in time!");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) { // exceptionit
            System.err.println("Main thread was interrupted while waiting for grading tasks to complete.");
            executor.shutdownNow();
        }

        // Säikeiden valmistuttua kerätään arvioidut palautukset
        System.out.println("------------ CUT HERE ------------");
        List<Submission> allGradedSubmissions = new ArrayList<>();
        for (GradingTask task : tasks) {
            allGradedSubmissions.addAll(task.getGradedSubmissions());
        }

        // Tulostetaan kaikki arvioidut palautukset
        for (var gs : allGradedSubmissions) {
            System.out.println(gs);
        }

        // Printataan suoritusaika
        System.out.printf("Total time for grading: %d ms%n", System.currentTimeMillis() - startTime);
    }
}
