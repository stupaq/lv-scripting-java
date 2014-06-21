package stupaq.labview;

import com.google.common.collect.ImmutableMap;

import com.jacob.com.SafeArray;
import com.jacob.com.Variant;

import stupaq.activex.ActiveXType;

public class StdCallVI extends VirtualInstrument {
  public static final String ARGS_CONTROL = "__args";
  public static final String ERR_STATUS_CONTROL = "__errStatus";
  public static final String ERR_CODE_CONTROL = "__errCode";
  public static final String ERR_SOURCE_CONTROL = "__errSource";
  public static final String RETURN_CONTROL = "__return";

  public StdCallVI(Application application, VIPath viPath) {
    super(application, viPath);
  }

  protected void checkErrors() throws VIErrorException {
    boolean errStatus = getControlValue(ERR_STATUS_CONTROL).getBoolean();
    if (errStatus) {
      int errCode = getControlValue(ERR_CODE_CONTROL).getInt();
      String errSource = getControlValue(ERR_SOURCE_CONTROL).getString();
      throw new VIErrorException(errCode, errSource);
    }
  }

  public Variant stdCall(Variant... args) throws VIErrorException {
    SafeArray args1 = new SafeArray(Variant.VariantVariant, args.length);
    args1.fromVariantArray(args);
    call(ImmutableMap.of(ARGS_CONTROL, new Variant(args1)));
    checkErrors();
    return getControlValue(RETURN_CONTROL);
  }

  public Variant stdCall(ActiveXType... args) throws VIErrorException {
    Variant[] args1 = new Variant[args.length];
    int index = 0;
    for (ActiveXType arg : args) {
      args1[index++] = arg.toVariant();
    }
    return stdCall(args1);
  }
}
