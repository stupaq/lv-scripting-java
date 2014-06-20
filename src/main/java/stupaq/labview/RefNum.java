package stupaq.labview;

import com.jacob.com.Variant;

import stupaq.activex.ActiveXType;

public class RefNum implements ActiveXType {
  private final String uuid;

  public RefNum(Variant controlValue) {
    uuid = controlValue.getString();
  }

  @Override
  public Variant toVariant() {
    return new Variant(uuid);
  }
}
