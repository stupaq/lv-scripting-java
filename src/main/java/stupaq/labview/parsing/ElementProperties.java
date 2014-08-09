package stupaq.labview.parsing;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.Maps;

import com.ni.labview.Element;
import com.ni.labview.LVDataType;

import java.util.Map;

class ElementProperties extends ForwardingMap<String, Object> {
  private final Map<String, Object> delegate = Maps.newHashMap();

  public ElementProperties(Element element) {
    for (Object object : element.getVersionOrI8OrI16()) {
      String name = null;
      if (object instanceof LVDataType) {
        name = ((LVDataType) object).getName();
      }
      if (name != null) {
        delegate.put(name, object);
      }
    }
  }

  @Override
  protected Map<String, Object> delegate() {
    return delegate;
  }
}
