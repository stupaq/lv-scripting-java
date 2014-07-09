package stupaq.labview.scripting.hierarchy;

import com.google.common.base.Optional;

import java.util.List;

import stupaq.labview.scripting.tools.BundlerCreate;

public final class Unbundler extends GrowableFunction<Unbundler> {
  public Unbundler(Generic owner, int outputs, Optional<String> label) {
    super(owner, owner.scriptingTools()
        .getTool(BundlerCreate.class)
        .apply(owner.viPath(), owner.uid(), false, outputs, label));
  }

  public Terminal<Unbundler> input() {
    return single();
  }

  public List<Terminal<Unbundler>> outputs() {
    return multiple();
  }
}