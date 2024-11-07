# Threads demonstration exercise for the Distributed Software Systems -course (DTEK2088)

This Java demonstration project was done as a part of the Distributed Software Systems course. It simulates a grading system where multiple reviewers grade student submissions concurrently.

The system automates grading for a set of submissions using a multithreaded approach. It allocates submissions to grading tasks, each processed by a separate thread.

**The solutions** can be found [src/main/java/fi/utu/tech/](src/main/java/fi/utu/tech/) and are named respectively for each assignment. 

Each App Class (`App1`, `App2`, etc.) builds upon the previous one, refining the grading logic.

Package `fi.utu.tech.common` includes reusable classes such as `GradingTask` and `TaskAllocator` available for all assignments. These classes are modified in certain assignments.

**The assignment details** are available in Finnish at [doc/tehtavananto.md](doc/tehtavananto.md). 

TLDR assignment details **in english:**
- Demonstrate Multi-threading: Progressively transform a single-threaded grading application into a multi-threaded one.
- Practice Task Division: Allocate grading tasks among multiple threads, using concepts of task and thread separation.
- Explore Concurrency: Enable simultaneous grading tasks to improve efficiency.
