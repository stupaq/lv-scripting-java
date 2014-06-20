package stupaq.labview.scripting.tools;

import com.google.common.base.Function;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

import stupaq.labview.Application;
import stupaq.labview.RefNum;
import stupaq.labview.VI;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIName;

public class CreateBlock extends VI {
  public CreateBlock(Application application) {
    super(application, new VIName("CreateBlock"));
  }

  public RefNum apply(final VIName targetVi) throws VIErrorException {
    return run(new Function<ActiveXComponent, RefNum>() {
      @Override
      public RefNum apply(ActiveXComponent vi) {
        vi.invoke("SetControlValue", new Variant("targetVi"), targetVi.toVariant());
        vi.invoke("Run");
        return new RefNum(vi.invoke("GetControlValue", new Variant("blockUUID")));
      }
    });
  }
}
