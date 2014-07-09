package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import java.util.List;

import stupaq.labview.scripting.tools.BundlerCreate;

public final class Bundler extends GrowableFunction<Bundler> {
  public Bundler(Generic owner, int inputs, Optional<String> label) {
    super(owner, owner.scriptingTools()
        .getTool(BundlerCreate.class)
        .apply(owner.viPath(), owner.uid(), true, inputs, label));
  }

  public Terminal<Bundler> output() {
    return single();
  }

  public List<Terminal<Bundler>> inputs() {
    return multiple();
  }
}