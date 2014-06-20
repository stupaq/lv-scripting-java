package stupaq.labview.scripting;

import stupaq.labview.RefNum;
import stupaq.labview.VIName;

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

  public RefNum insertBlock() {
    return tools.blockCreator().apply(viName);
  }

  public RefNum insertWire(RefNum first, RefNum second) {
    return tools.wireCreator().apply(viName, first, second);
  }
}
