package stupaq.labview.scripting;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import stupaq.labview.Application;
import stupaq.labview.scripting.tools.CreateBlock;
import stupaq.labview.scripting.tools.CreateWire;

public class ScriptingTools extends Application {

  private final Supplier<CreateBlock> blockCreator;
  private final Supplier<CreateWire> wireCreator;

  public ScriptingTools() {
    blockCreator = Suppliers.memoize(new Supplier<CreateBlock>() {
      @Override
      public CreateBlock get() {
        return new CreateBlock(ScriptingTools.this);
      }
    });
    wireCreator = Suppliers.memoize(new Supplier<CreateWire>() {
      @Override
      public CreateWire get() {
        return new CreateWire(ScriptingTools.this);
      }
    });
  }

  public CreateBlock blockCreator() {
    return blockCreator.get();
  }

  public CreateWire wireCreator() {
    return wireCreator.get();
  }
}
