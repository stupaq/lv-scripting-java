package stupaq.labview.scripting;

import stupaq.labview.UID;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIPath;
import stupaq.labview.scripting.tools.CreateBlock;
import stupaq.labview.scripting.tools.CreateVI;
import stupaq.labview.scripting.tools.CreateWire;
import stupaq.labview.scripting.tools.RemoveGObject;

public class EditableVI implements AutoCloseable {
  private final ScriptingTools tools;
  private final VIPath viPath;

  public EditableVI(ScriptingTools tools, VIPath viPath) {
    this.tools = tools;
    this.viPath = viPath;
  }

  @Override
  public void close() throws Exception {
  }

  public void create() throws VIErrorException {
    tools.getTool(CreateVI.class).apply(viPath);
  }

  public UID insertBlock() throws VIErrorException {
    return tools.getTool(CreateBlock.class).apply(viPath);
  }

  public UID insertWire(UID first, UID second) throws VIErrorException {
    return tools.getTool(CreateWire.class).apply(viPath, first, second);
  }

  public void removeObject(UID object) throws VIErrorException {
    tools.getTool(RemoveGObject.class).apply(viPath, object);
  }
}
