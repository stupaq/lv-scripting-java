package stupaq.labview.scripting;

import stupaq.labview.RefNum;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIName;
import stupaq.labview.scripting.tools.CreateBlock;
import stupaq.labview.scripting.tools.CreateWire;

public class EditableVI implements AutoCloseable {
  private final ScriptingTools tools;
  private final VIName viName;

  public EditableVI(ScriptingTools tools, VIName viName) {
    this.tools = tools;
    this.viName = viName;
  }

  @Override
  public void close() throws Exception {
  }

  public RefNum insertBlock() throws VIErrorException {
    return tools.getTool(CreateBlock.class).apply(viName);
  }

  public RefNum insertWire(RefNum first, RefNum second) throws VIErrorException {
    return tools.getTool(CreateWire.class).apply(viName, first, second);
  }
}
