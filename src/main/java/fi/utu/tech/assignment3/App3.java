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

        // Luodaan uusi GradingTask ja välitetään arvioitavat palautukset
        GradingTask gradingTask = new GradingTask(ungradedSubmissions);

        // Luodaan uusi säie GradingTask-instanssista ja käynnistetään se
        Thread gradingThread = new Thread(gradingTask);
        gradingThread.start();

        try {
            // Odotetaan, että arviointisäie on valmis ennen kuin jatketaan
            gradingThread.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread was interrupted while waiting for grading thread.");
        }

        // Arviointisäie on nyt valmis, joten voidaan turvallisesti hakea arvioidut palautukset
        System.out.println("------------ CUT HERE ------------");
        List<Submission> gradedSubmissions = gradingTask.getGradedSubmissions();
        for (var gs : gradedSubmissions) {
            System.out.println(gs);
        }

        // Lasketaan funktion suoritusaika
        System.out.printf("Total time for grading: %d ms%n", System.currentTimeMillis() - startTime);
  }
}
