package incubator.communication.ext.protocols.tests;

import cz.agents.alite.communication.acquaintance.AbstractTask;
import cz.agents.alite.communication.acquaintance.CNPTaskBase;
import cz.agents.alite.communication.acquaintance.DefaultPlanBase;
import cz.agents.alite.communication.acquaintance.Task;
import cz.agents.alite.communication.acquaintance.Task.TaskListener;
import cz.agents.alite.communication.acquaintance.TaskBase.AllocationCallback;
import cz.agents.alite.communication.acquaintance.TaskBase.UnknownTaskTypeException;
import cz.agents.alite.communication.acquaintance.iterative.CNPTaskBaseSyncIterRA;
import cz.agents.alite.common.capability.CapabilityRegister;
import cz.agents.alite.communication.DefaultCommunicator;
import cz.agents.alite.communication.channel.DirectCommunicationChannelAsync;
import cz.agents.alite.communication.directory.DirectoryFacilitatorSingleton;
import cz.agents.alite.vis.VisManager;
import cz.agents.alite.vis.Vis;
import cz.agents.alite.vis.layer.common.ColorLayer;
import incubator.vis.TextLayer;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.vecmath.Point3d;

/**
 *
 * @author vokrinek
 */
public class AcqTest1 {

    static CNPTaskBase tb1;
    static CNPTaskBase tb2;
    static DefaultPlanBase.MyExecutor ex1;
    static DefaultPlanBase pb1;
    static DefaultPlanBase pb2;
    static DefaultPlanBase.MyExecutor ex2;
    static Task t1;
    static Task t2;
    static Task t3;
    static boolean proceed = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        CapabilityRegister directory = DirectoryFacilitatorSingleton.getInstance();

        DefaultCommunicator c1 = new DefaultCommunicator("A1");
        c1.addChannel(new DirectCommunicationChannelAsync(c1));
        tb1 = new CNPTaskBaseSyncIterRA(c1, directory);

        DefaultCommunicator c2 = new DefaultCommunicator("A2");
        c2.addChannel(new DirectCommunicationChannelAsync(c2));
        tb2 = new CNPTaskBaseSyncIterRA(c2, directory);

        pb1 = new DefaultPlanBase();
        ex1 = new DefaultPlanBase.MyExecutor();
        pb1.registerExecutor(ex1);
        tb1.registerType(AbstractTask.getTaskTypeName(), pb1);

        pb2 = new DefaultPlanBase();
        ex2 = new DefaultPlanBase.MyExecutor();
        pb2.registerExecutor(ex2);
        tb2.registerType(AbstractTask.getTaskTypeName(), pb2);

        Vis.initWithBounds(new Rectangle(0, 0, 1000, 1024));
        VisManager.init();
        VisManager.registerLayer(ColorLayer.create(Color.BLUE));

        VisManager.registerLayer(TextLayer.create(new TextLayer.TextInfoBox() {

            public Point3d getPosition() {
                return new Point3d(50, 1450, 0);
            }

            public List<String> getTexts() {
                List<String> aaa = new LinkedList<String>();
                aaa.add("A1");
                aaa.add("----------");
                for (Task task : ex1.getPlan()) {
                    aaa.add(task.toString());
                }
                return aaa;
            }
        }));

        VisManager.registerLayer(TextLayer.create(new TextLayer.TextInfoBox() {

            public Point3d getPosition() {
                return new Point3d(400, 1450, 0);
            }

            public List<String> getTexts() {
                List<String> aaa = new LinkedList<String>();
                aaa.add("A2");
                aaa.add("----------");
                for (Task task : ex2.getPlan()) {
                    aaa.add(task.toString());
                }
                return aaa;
            }
        }));

        t1 = invoke(new AllocationCallback() {

            public void allocated() {
                print();
                System.out.println("T1 allocated");
                proceed = true;

            }

            public void failed() {
                System.out.println("something failed 1");
            }
        }); //A1

        waitHere();

        t2 = invoke(new AllocationCallback() {

            public void allocated() {
                print();
                System.out.println("T2 allocated");
                proceed = true;
            }

            public void failed() {
                System.out.println("something failed 2");
            }
        }); //A2        

        waitHere();

        t3 = invoke(new AllocationCallback() {

            public void allocated() {
                print();
                System.out.println("T3 allocated");
                proceed = true;
            }

            public void failed() {
                System.out.println("something failed 3");
            }
        }); //A1


        // we are directly accessing the execution that not needed to be prepared at this time
        // in real system this situation is not possible, so waitHere() is needed.
        waitHere();

        System.out.println("T2 is going to be finalized");
        ex2.finalize(t2); //
        waitHere();
        print();

        System.out.println("T1 is going to be failed");
        ex1.fail(t1);
        print();

    }

    private static void waitHere() {
        System.out.println("");
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(AcqTest1.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (!proceed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(AcqTest1.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.print(".");
        }
        proceed = false;
    }

    static Task invoke(AllocationCallback cb) {

        final Task task = new AbstractTask(tb1.generateNewTaskID(), null);
        try {
            tb1.invokeTask(task, new TaskListener() {

                public void taskCompleted() {
                    System.out.println("Task completed: " + task);
                    proceed = true;
                }

                public void taskUnreachable() {
                    System.out.println("Task unreachable: " + task);
                    proceed = true;
                }
            }, cb);
        } catch (UnknownTaskTypeException ex) {
            Logger.getLogger(AcqTest1.class.getName()).log(Level.SEVERE, null, ex);
        }

        return task;
    }

    static void print() {
        System.out.println("--------------------------------------");
        System.out.println("Total cost A1: " + pb1.getTotalCost());
        System.out.println("Total cost A2: " + pb2.getTotalCost());

    }
}
