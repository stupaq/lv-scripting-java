package stupaq.labview.scripting;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.tools.CreateBlock;
import stupaq.labview.scripting.tools.CreateVI;
import stupaq.labview.scripting.tools.CreateWire;
import stupaq.labview.scripting.tools.RemoveGObject;

public class EditableVI {
  private final ScriptingTools tools;
  private final VIPath viPath;

  public EditableVI(ScriptingTools tools, VIPath viPath) {
    this.tools = tools;
    this.viPath = viPath;
  }

  public void create() throws VIErrorException {
    tools.getTool(CreateVI.class).apply(viPath);
  }

  public UID insertBlock() throws VIErrorException {
    return tools.getTool(CreateBlock.class).apply(viPath);
  }

  public UID insertWire(UID uidSource, UID uidDestination) throws VIErrorException {
    return tools.getTool(CreateWire.class).apply(viPath, uidSource, uidDestination);
  }

  public void removeObject(UID uid) throws VIErrorException {
    tools.getTool(RemoveGObject.class).apply(viPath, uid);
  }
}
