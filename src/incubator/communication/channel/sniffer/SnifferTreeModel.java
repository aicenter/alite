package incubator.communication.channel.sniffer;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * <p>Title: A-Globe</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Gerstner Laboratory</p>
 * @author David Sislak
 * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
 */
// TODO: complete the javadoc

public class SnifferTreeModel implements TreeModel {
  private final SnifferDataContainer dataContainer;

  List listeners= new LinkedList();

  static Icon AGENT_ICON = new ImageIcon(SnifferTreeModel.class.getResource("agent.gif"));;
  static Icon SERVICE_ICON = new ImageIcon(SnifferTreeModel.class.getResource("service.gif"));
  static Icon SELECTED_ICON = new ImageIcon(SnifferTreeModel.class.getResource("selected.gif"));
  static Icon NON_SELECTED_ICON = new ImageIcon(SnifferTreeModel.class.getResource("nonSelected.gif"));

  static String ROOT = "Containers";
  static String OTHERS = "Others";
  static String AGENTS = "Agents";
  static String SERVICES = "Services";
  private Object[] containers = new Object[]{};

  private JPopupMenu popupMenu = new JPopupMenu();
  private JMenuItem doSniffMenuItem = new JMenuItem();
  private JMenuItem dontSniffMenuItem = new JMenuItem();
  private SnifferDataContainer.Record selActor = null;


  public SnifferTreeModel(SnifferDataContainer _owner) {
    dataContainer = _owner;

    doSniffMenuItem.setText("Add to filter");
    doSniffMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (selActor != null)
          dataContainer.doSniff(selActor,true);
      }
    }
    );
    dontSniffMenuItem.setText("Remove from filter");
    dontSniffMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        if (selActor != null)
          dataContainer.dontSniff(selActor,true);
      }
    }
        );
    popupMenu.add(doSniffMenuItem);
    popupMenu.add(dontSniffMenuItem);
  }

  public TreeCellRenderer getCellRenderer() {
    return new MyRenderer();
  }

  public MouseListener getMouseListener(JTree owner) {
    return new MyMouseListener(owner);
  }

  @SuppressWarnings(value={"unchecked"}) 
  void containersAdded(SnifferDataContainer.ContainerRecord _container) {
    synchronized (containers) {
      ArrayList _newContainer = new ArrayList(Arrays.asList(containers));
      Container _new = new Container(_container);
      _newContainer.add(_new);
      containers = _newContainer.toArray();
      Arrays.sort(containers);
      fireRootChange();
    }
  }

  @SuppressWarnings(value={"unchecked"}) 
  void containersRemoved(SnifferDataContainer.ContainerRecord _container) {
    synchronized (containers) {
      ArrayList _newContainer = new ArrayList(Arrays.asList(containers));
      for (Iterator iter = _newContainer.iterator(); iter.hasNext(); ) {
        Container item = (Container) iter.next();
        if (item.content == _container) {
          iter.remove();
          break;
        }
      }
      containers = _newContainer.toArray();
      fireRootChange();
    }
  }

  @SuppressWarnings(value={"unchecked"}) 
  void agentAdded(SnifferDataContainer.AgentRecord _agent) {
    synchronized (containers) {
      int i = -1;
      for (int k=0;k<containers.length;k++) {
        if (((Container)containers[k]).content == _agent.container) {
          i = k;
          break;
        }
      }
      if (i < 0) return;
      Entities which;
      if (_agent.isAgent)
        which = ((Container)containers[i]).agents;
      else
        which = ((Container)containers[i]).services;
      ArrayList _newAgents = new ArrayList(Arrays.asList(which.childs));
      _newAgents.add(new Agent(_agent));
      which.childs = _newAgents.toArray();
      Arrays.sort(which.childs);
      fireAgentsChange((Container)containers[i],which);
    }
  }

  private void fireEvent(TreeModelEvent event) {
    Iterator i= listeners.iterator();
    while(i.hasNext())
    {
      ((TreeModelListener)i.next()).treeStructureChanged(event);
    }
  }

  private void fireRootChange() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        final TreeModelEvent e = new TreeModelEvent(this, new Object[] {ROOT});
        fireEvent(e);
      }
    }
        );
  }

  private void fireAgentsChange(final Container _container,final Entities _agents) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        final TreeModelEvent e = new TreeModelEvent(this, new Object[] {ROOT,_container,_agents});
        fireEvent(e);
      }
    }
        );
  }

  public Object getRoot() {
    return ROOT;
  }

  public Object getChild(Object parent, int index) {
    if (parent == ROOT) {
      synchronized (containers) {
        return (Container)(containers[index]);
      }
    } else if (parent instanceof Container) {
      switch (index) {
        case 0:
          return ((Container)parent).others;
        case 1:
          return ((Container)parent).agents;
        case 2:
          return ((Container)parent).services;
        default:
          throw new IllegalArgumentException("Bad index passed to ContainerTreeModel");
      }
    } else if (parent instanceof Entities) {
      return (Agent)(((Entities)parent).childs[index]);
    }
    throw new IllegalArgumentException("Bad parent passed to ContainerTreeModel");
  }

  public int getChildCount(Object parent) {
    if (parent == ROOT) {
      synchronized (containers) {
        return containers.length;
      }
    } else if (parent instanceof Container) {
        return 3;
    } else if (parent instanceof Others) {
      return 0;
    } else if (parent instanceof Entities) {
      return ((Entities)parent).childs.length;
    }
    throw new IllegalArgumentException("Bad parent passed to ContainerTreeModel");
  }

  public boolean isLeaf(Object node) {
    return (node!=ROOT) && !(node instanceof Container) && !(node instanceof Entities);
  }

  public void valueForPathChanged(TreePath path, Object newValue) {
    throw new java.lang.UnsupportedOperationException("Method ContainerTreeModel.valueForPathChanged should have never been called.");
  }

  private int getIndexOf(Object[] array, Object node) {
    for (int k=0;k<array.length;k++) {
      if (array[k] == node) return k;
    }
    throw new IllegalArgumentException("Bad child node passed to ContainerTreeModel");
  }

  public int getIndexOfChild(Object parent, Object child) {
    if (parent == ROOT) {
      synchronized (containers) {
        return getIndexOf(containers,child);
      }
    } else if (parent instanceof Container) {
      if (((Container)parent).others == child) return 0;
      if (((Container)parent).agents == child) return 1;
      if (((Container)parent).services == child) return 2;
      throw new IllegalArgumentException("Bad child node passed to ContainerTreeModel");
    } else if (parent instanceof Entities) {
      return getIndexOf(((Entities)parent).childs,child);
    }
    throw new IllegalArgumentException("Bad parent passed to ContainerTreeModel");
  }

  @SuppressWarnings(value={"unchecked"}) 
  public void addTreeModelListener(TreeModelListener l) {
    if(!listeners.contains(l))
      listeners.add(l);
  }

  public void removeTreeModelListener(TreeModelListener l) {
    listeners.remove(l);
  }

  public SnifferDataContainer.Record getRecord(Object _object) {
    if (_object == null) return null;
    if (_object instanceof Others)
      return ((Others)_object).container.content;
    else if (_object instanceof Agent)
      return ((Agent)_object).content;
    return null;
  }

  private class Container implements Comparable {
    final SnifferDataContainer.ContainerRecord content;
    final Others others = new Others(this);
    final Entities agents = new Entities(AGENTS);
    final Entities services = new Entities(SERVICES);

    public Container(SnifferDataContainer.ContainerRecord _content) {
      content = _content;
    }

    public int compareTo(Object _to) {
      if (_to instanceof Container) {
        return (content.name.compareTo(((Container)_to).content.name));
      }
      return 0;
    }

    public String toString() {
      return content.name;
    }
  }

  private class Entities {
    final String name;
    Object[] childs = new Object[]{};

    public Entities(String _name) {
      name = _name;
    }

    public String toString() {
      return name;
    }
  }

  private class Others {
    final String name = OTHERS;
    final Container container;

    public Others(Container _container) {
      container = _container;
    }

    public String toString() {
      return name;
    }
  }

  private class Agent implements Comparable {
    final SnifferDataContainer.AgentRecord content;

    public Agent(SnifferDataContainer.AgentRecord _content) {
      content = _content;
    }

    public String toString() {
      return content.name;
    }

    public int compareTo(Object _to) {
      if (_to instanceof Agent) {
        return (content.name.compareTo(((Agent)_to).content.name));
      }
      return 0;
    }
  }

  private class MyRenderer extends DefaultTreeCellRenderer {
    private Font normalFont = null;
    private Font boldFont;

    public MyRenderer() {
      super();
    }

    private void setFonts() {
      normalFont = getFont();
      boldFont = getFont().deriveFont(Font.BOLD);
    }

    public Component getTreeCellRendererComponent(JTree tree,
                        Object value,boolean sel,boolean expanded,boolean leaf,
                        int row,boolean hasFocus) {
      super.getTreeCellRendererComponent(tree,value,sel,expanded,leaf,row,hasFocus);
      if (normalFont == null) setFonts();
      setFont(normalFont);
      if (value == ROOT) {
        setIcon(openIcon);
      } else if (value instanceof Container) {
        if (expanded) {
          setIcon(openIcon);
        } else {
          setIcon(closedIcon);
        }
        if (((Container)value).content.isLogged()) {
          setFont(boldFont);
        }
      } else if (value instanceof Others) {
        if (((Others)value).container.content.isSniffed()) {
          setIcon(SELECTED_ICON);
        } else {
          setIcon(NON_SELECTED_ICON);
        }
      } else if (value instanceof Entities) {
        if (((Entities)value).name == AGENTS)
          setIcon(AGENT_ICON);
        else
          setIcon(SERVICE_ICON);
      } else if (value instanceof Agent) {
        if (((Agent)value).content.isSniffed()) {
          setIcon(SELECTED_ICON);
        } else {
          setIcon(NON_SELECTED_ICON);
        }
      } else {
        setIcon(null);
      }
      return this;
    }

  }

  private class MyMouseListener implements MouseListener {
    final private JTree tree;

    public MyMouseListener(JTree _owner) {
      tree = _owner;
    }

    public void mouseClicked(MouseEvent mouseEvent) {
      if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseEvent.BUTTON1) {
        TreePath selPath = tree.getPathForLocation(mouseEvent.getX(),mouseEvent.getY());
        if (selPath == null) return;
        Object lastItem = selPath.getLastPathComponent();
        SnifferDataContainer.Record record = getRecord(lastItem);
        if (record != null)
          dataContainer.changeShowing(record,true);
      } else if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
        TreePath selPath = tree.getPathForLocation(mouseEvent.getX(),mouseEvent.getY());
        if (selPath == null) return;
        Object lastItem = selPath.getLastPathComponent();
        selActor = getRecord(lastItem);
        if (selActor!=null) {
          tree.setSelectionRow(tree.getRowForLocation(mouseEvent.getX(),mouseEvent.getY()));
          if (selActor.isSniffed()) {
            doSniffMenuItem.setEnabled(false);
            dontSniffMenuItem.setEnabled(true);
          } else {
            doSniffMenuItem.setEnabled(true);
            dontSniffMenuItem.setEnabled(false);
          }
          popupMenu.show(mouseEvent.getComponent(),mouseEvent.getX(),mouseEvent.getY());
        }
      }
    }

    public void mousePressed(MouseEvent mouseEvent) {
    }

    public void mouseReleased(MouseEvent mouseEvent) {
    }

    public void mouseEntered(MouseEvent mouseEvent) {
    }

    public void mouseExited(MouseEvent mouseEvent) {
    }
  }
}
