package incubator.visprotocol.vis.protocol;

// TODO: this protocol will store the elements into a file
// for future use by the FileReadProtocol
// (it should enable replaying of the visualization)
public class FileWriterProtocol implements Protocol {

    public FileWriterProtocol() {
	// TODO Auto-generated constructor stub
    }
    
    @Override
    public <T> void push(T elements) {
	// TODO Auto-generated method stub

    }

    @Override
    public <T> T pull(Class<T> clazz) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void nextStep() {
	// TODO Auto-generated method stub

    }

}
