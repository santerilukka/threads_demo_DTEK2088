package fi.utu.tech.common;

import java.util.ArrayList;
import java.util.List;

/**
 * You need to modify this file
 */


public class TaskAllocator {

    /**
     * Allocator that creates list of two (2) GradingTask objects with each having half of the given submissions
     * @param submissions The submissions to be allocated
     * @return The two GradingTask objects in a list, each having half of the submissions
     */
    public static List<GradingTask> sloppyAllocator(List<Submission> submissions) {
        int half = submissions.size() / 2;

        // jaetaan palautukset kahtia
        List<Submission> firstHalf = submissions.subList(0, half);
        List<Submission> secondHalf = submissions.subList(half, submissions.size());

        // Luodaan kaksi GradingTask oliota ja annetaan puoliskot niille
        GradingTask task1 = new GradingTask(firstHalf);
        GradingTask task2 = new GradingTask(secondHalf);

        // palautetaan yksi lista GradingTask olioista
        List<GradingTask> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        return tasks;
    }


    
    /**
     * Allocate List of ungraded submissions to tasks
     * @param submissions List of submissions to be graded
     * @param taskCount Amount of tasks to be generated out of the given submissions
     * @return List of GradingTasks allocated with some amount of submissions (depends on the implementation)
     */
    public static List<GradingTask> allocate(List<Submission> submissions, int taskCount) {
        // TODO: Tehtävä 5
        // Retruns null for now to suppress warnings
        return null;
    }
}
