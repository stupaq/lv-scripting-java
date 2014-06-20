package stupaq.labview.scripting.tools;

import com.google.common.base.Function;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

import stupaq.labview.Application;
import stupaq.labview.RefNum;
import stupaq.labview.VI;
import stupaq.labview.VIErrorException;
import stupaq.labview.VIName;

public class CreateWire extends VI {
  public CreateWire(Application application) {
    super(application, new VIName("CreateWire"));
  }

  public RefNum apply(final VIName targetVi, final RefNum first, final RefNum second)
      throws VIErrorException {
    return run(new Function<ActiveXComponent, RefNum>() {
      @Override
      public RefNum apply(ActiveXComponent vi) {
        vi.invoke("SetControlValue", new Variant("targetVi"), targetVi.toVariant());
        vi.invoke("SetControlValue", new Variant("firstUUID"), first.toVariant());
        vi.invoke("SetControlValue", new Variant("secondUUID"), second.toVariant());
        vi.invoke("Run");
        return new RefNum(vi.invoke("GetControlValue", new Variant("wireUUID")));
      }
    });
  }
}
