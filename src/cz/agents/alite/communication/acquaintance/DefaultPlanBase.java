package cz.agents.alite.communication.acquaintance;

import cz.agents.alite.communication.acquaintance.Task.TaskListener;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *  Default PlanBase for testing and basic operations.
 *  The plan cost is equal to number of assigned tasks.
 *  PlanCost of any partial plan (for a task) is equal to 1.
 *  This PlanBase can be used for balancing the number of tasks between agents
 *  in case the cost of any task execution is equal and constant.
 *
 *  The attached executor has to accept the Plan in the form of MyPlan extends LinkedList<Task> defind in this class.
 *
 * @author vokrinek
 */
public class DefaultPlanBase implements PlanBase {

    private final MyPlan tasks = new MyPlan();
    private final HashMap<Task, TaskListener> listeners = new HashMap<Task, TaskListener>();
    private Executor executor;

    public PlanCost evaluateInsertion(Task task) {
        return new DefaultPlanCost(tasks.size());
    }

    public PlanCost evaluateRemoval(Task task) {
        return new DefaultPlanCost(tasks.size() - 1);
    }

    public PlanCost insertTask(Task task, TaskListener taskListener) {
        tasks.add(task);
        listeners.put(task, taskListener);
        executor.executePlan(tasks);
        return new DefaultPlanCost(tasks.size() - 1);
    }

    public PlanCost removeTask(Task task) {
        tasks.remove(task);
        listeners.remove(task);
        executor.executePlan(tasks);
        return new DefaultPlanCost(tasks.size());
    }

    public PlanCost getTotalCost() {
        return new DefaultPlanCost(tasks.size());
    }

    public void registerExecutor(Executor executor) {
        this.executor = executor;
        executor.registerExecutorFeedback(new MyExecutorFeedback(this));
    }

    void taskDone(Task task) {
        tasks.remove(task);
        listeners.remove(task).taskCompleted();
    }

    void taskFailed(Task task) {
        tasks.remove(task);
        listeners.remove(task).taskUnreachable();
    }

    /**
     * Execution feedback for DefaultPlanBase
     */
    public static class MyExecutorFeedback implements ExecutorFeedback {

        private final DefaultPlanBase pb;

        /**
         *
         * @param pb
         */
        public MyExecutorFeedback(DefaultPlanBase pb) {
            this.pb = pb;
        }

        /**
         * Call when Task execution is done.
         *
         * @param task
         */
        public void done(Task task) {
            pb.taskDone(task);
        }

        /**
         * Call when Task execution fails.
         *
         * @param task
         */
        public void failed(Task task) {
            pb.taskFailed(task);
        }
    }

    /**
     * Basic Plan implementation.
     * Only encaptulates LinkedList<Task>.
     */
    public static class MyPlan extends LinkedList<Task> implements Plan {

        private static final long serialVersionUID = -5734327377766938273L;

    }

    /**
     * Executor for DefaultPlanBase.
     */
    public static class MyExecutor implements Executor {

        private DefaultPlanBase.MyExecutorFeedback feedback;
        private DefaultPlanBase.MyPlan plan;

        public void registerExecutorFeedback(ExecutorFeedback feedback) {
            this.feedback = (MyExecutorFeedback) feedback;
        }

        public void executePlan(Plan plan) {
            this.plan = (MyPlan) plan;
        }

        public void addPlan(Plan plan) {
            this.plan = (MyPlan) plan;
        }

        public void scratchPlan() {
            plan.clear();
        }

        /**
         * Gets the current plan.
         *
         * @return plan
         */
        public MyPlan getPlan() {
            return plan;
        }

        /**
         * Reports successfull execution of the task.
         *
         * @param task
         */
        public void finalize(Task task) {
            feedback.done(task);
        }

        /**
         * Reports unsuccessfull execution of the task.
         *
         * @param task
         */
        public void fail(Task task) {
            feedback.failed(task);
        }
    }
}

