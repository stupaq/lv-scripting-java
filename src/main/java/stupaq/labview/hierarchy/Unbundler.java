package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import java.util.List;

import stupaq.labview.scripting.tools.BundlerCreate;

public final class Unbundler extends GrowableFunction<Unbundler> {
  public static final String XML_NAME = "Unbundler";

  public Unbundler(Generic owner, int outputs, Optional<String> label) {
    super(owner, owner.scriptingTools()
        .get(BundlerCreate.class)
        .apply(owner.viPath(), owner.uid(), false, outputs, label));
  }

  public Terminal<Unbundler> input() {
    return single();
  }

  public List<Terminal<Unbundler>> outputs() {
    return multiple();
  }
}
