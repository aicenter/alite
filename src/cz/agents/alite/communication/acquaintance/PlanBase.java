package cz.agents.alite.communication.acquaintance;

import cz.agents.alite.communication.acquaintance.Task.TaskListener;

/**
 *  Interface for the Plan Base definition
 *  The Plan Base is persistent entity able to maintain plans for the tasks.
 *
 * @author vokrinek
 */
public interface PlanBase {

    /**
     * Evaluates potential task insertion.
     *
     * @param task
     * @return the cost increase of the new potential plan, null if plan not found
     */
    PlanCost evaluateInsertion(Task task);

    /**
     * Evaluates potential task removal.
     * The cost of the potential task removal cannot be lower than
     * cost of subsequent insertion of the same task.
     *
     * @param task
     * @return the cost equivalent of savings when the task will be removed, null if task cannod be removed (e.g. is under execution)
     */
    PlanCost evaluateRemoval(Task task);

    /**
     * Adds the task to the PlanBase.
     * The plan is created and the respective plan cost increase is computed.
     *
     * @param task
     * @param taskListener callback for task execution feedback
     * @return total plan cost increase, null if plan not found
     */
    PlanCost insertTask(Task task, TaskListener taskListener);

    /**
     * Remove the task from the PlanBase.
     * The plan is updated and respective plan cost savings is computed.
     *
     * @param task
     * @return total plan cost decrease.
     */
    PlanCost removeTask(Task task);

    /**
     * Computes the total cost of overall plan to fullfill all the inserted tasks.
     *
     * @return PlanCost of the total plan.
     */
    PlanCost getTotalCost();

    /**
     * Registeres the Executor to this PlanBase.
     * PlanBase is responsible for creating the appropriate ExecutorFeedback and registration using Executor.registerExecutorFeedback
     *
     * @param executor
     */
    public void registerExecutor(Executor executor);

    /**
     * Exception for planning failure
     */
    class TaskPlanError extends Exception {

        private static final long serialVersionUID = 416794555267004753L;

        TaskPlanError(String string) {
            super(string);
        }
    }

    /**
     * Executor interface.
     * Enrich the API of your implementation for capturing the specifics of your PlanBase to Executor interactions.
     */
    public interface Executor {

        /**
         * Binds the executor to the specific feedback (usually implemented by PlanBase).
         * @param feedback
         */
        public void registerExecutorFeedback(ExecutorFeedback feedback);

        /**
         * Executes given plan.
         *
         * @param plan
         */
        public void executePlan(Plan plan);

        /**
         * Extends actual plan by given plan.
         *
         * @param plan
         */
        public void addPlan(Plan plan);

        /**
         * Invalidates curen plan and stops the execution.
         */
        public void scratchPlan();
    }

    /**
     * The encapsulating interface for feedback from executor to PlanBase.
     * Enrich the API of your implementation for capturing the specifics of your Executor to PlanBase interactions.
     */
    public interface ExecutorFeedback {
    }

    /**
     * The encapsulating interface for Plan representation.
     * Enrich the API of your implementation for capturing the specifics of your PlanBase and Executor.
     */
    public interface Plan {
    }
}
