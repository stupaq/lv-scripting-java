package stupaq.labview.hierarchy;

import com.google.common.base.Optional;

import java.util.List;

import stupaq.labview.scripting.tools.BundlerCreate;

public final class Bundler extends GrowableFunction<Bundler> {
  public static final String XML_NAME = "Bundler";

  public Bundler(Generic owner, int inputs, Optional<String> label) {
    super(owner, owner.scriptingTools()
        .get(BundlerCreate.class)
        .apply(owner.viPath(), owner.uid(), true, inputs, label));
  }

  public Terminal<Bundler> output() {
    return single();
  }

  public List<Terminal<Bundler>> inputs() {
    return multiple();
  }
}
