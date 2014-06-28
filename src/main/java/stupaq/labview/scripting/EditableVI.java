package stupaq.labview.scripting;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.tools.CleanUpDiagram;
import stupaq.labview.scripting.tools.ConnectWire;
import stupaq.labview.scripting.tools.DeleteGObject;
import stupaq.labview.scripting.tools.InlineCNodeAddIO;
import stupaq.labview.scripting.tools.InlineCNodeCreate;
import stupaq.labview.scripting.tools.VICreate;

public class EditableVI {
  private final ScriptingTools tools;
  private final VIPath viPath;

  public EditableVI(ScriptingTools tools, VIPath viPath) {
    this.tools = tools;
    this.viPath = viPath;
  }

  public void create() throws VIErrorException {
    tools.getTool(VICreate.class).apply(viPath);
  }

  public UID inlineCNodeCreate(String content) throws VIErrorException {
    return tools.getTool(InlineCNodeCreate.class).apply(viPath, content);
  }

  public UID inlineCNodeAddIO(UID uid, boolean isInput, String name) throws VIErrorException {
    return tools.getTool(InlineCNodeAddIO.class).apply(viPath, uid, isInput, name);
  }

  public UID connectWire(UID source, UID destination, String label) throws VIErrorException {
    return tools.getTool(ConnectWire.class).apply(viPath, source, destination, label);
  }

  public void removeObject(UID uid) throws VIErrorException {
    tools.getTool(DeleteGObject.class).apply(viPath, uid);
  }

  public void cleanUpDiagram() throws VIErrorException {
    tools.getTool(CleanUpDiagram.class).apply(viPath);
  }
}
