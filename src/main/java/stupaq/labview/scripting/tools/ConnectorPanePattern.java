package stupaq.labview.scripting.tools;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import com.jacob.com.Variant;
import com.jacob.extensions.ActiveXType;

import java.util.Map.Entry;
import java.util.TreeMap;

public enum ConnectorPanePattern implements ActiveXType {
  P4800(4800),
  P4801(4801),
  P4802(4802),
  P4803(4803),
  P4805(4805),
  P4835(4835);
  private static final TreeMap<Integer, ConnectorPanePattern> countToPattern = Maps.newTreeMap();

  static {
    countToPattern.put(1, P4800);
    countToPattern.put(2, P4801);
    countToPattern.put(3, P4803);
    countToPattern.put(4, P4805);
    countToPattern.put(28, P4835);
  }

  public static int DO_NOT_CONNECT = -1;
  private final int pattern;

  private ConnectorPanePattern(int pattern) {
    this.pattern = pattern;
  }

  public static ConnectorPanePattern choosePattern(int connectorsCount) {
    Entry<Integer, ConnectorPanePattern> safeChoice = countToPattern.ceilingEntry(connectorsCount);
    Preconditions.checkArgument(safeChoice != null);
    return safeChoice.getValue();
  }

  @Override
  public Variant toVariant() {
    return new Variant(pattern);
  }
}
