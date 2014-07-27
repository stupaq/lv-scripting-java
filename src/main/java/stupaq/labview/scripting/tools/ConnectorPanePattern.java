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
  P4808(4808),
  P4811(4811),
  P4814(4814),
  P4815(4815),
  P4823(4823),
  P4825(4825),
  P4831(4831),
  P4832(4832),
  P4833(4833),
  P4834(4834),
  P4835(4835);
  private static final TreeMap<Integer, ConnectorPanePattern> countToPattern = Maps.newTreeMap();

  static {
    countToPattern.put(1, P4800);
    countToPattern.put(2, P4801);
    countToPattern.put(3, P4803);
    countToPattern.put(4, P4805);
    countToPattern.put(5, P4808);
    countToPattern.put(6, P4811);
    countToPattern.put(7, P4814);
    countToPattern.put(8, P4815);
    countToPattern.put(9, P4823);
    countToPattern.put(10, P4825);
    countToPattern.put(11, P4831);
    countToPattern.put(12, P4832);
    countToPattern.put(16, P4833);
    countToPattern.put(20, P4834);
    countToPattern.put(28, P4835);
  }

  public static final int DO_NOT_CONNECT = -1;
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
