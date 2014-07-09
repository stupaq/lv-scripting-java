package stupaq.labview.scripting.tools;

import com.jacob.com.Variant;

import stupaq.activex.ActiveXType;

public enum ConnectorPanePattern implements ActiveXType {
  P4800(4800),
  P4835(4835);

  private final int pattern;

  private ConnectorPanePattern(int pattern) {
    this.pattern = pattern;
  }

  public static ConnectorPanePattern choosePattern(int connectorsCount) {
    return P4835;
  }

  @Override
  public Variant toVariant() {
    return new Variant(pattern);
  }
}
