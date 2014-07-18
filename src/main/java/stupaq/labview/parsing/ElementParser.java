package stupaq.labview.parsing;

import com.ni.labview.Element;

abstract class ElementParser<E extends Exception> {
  public abstract void parse(Element element, ElementProperties properties) throws E;
}
