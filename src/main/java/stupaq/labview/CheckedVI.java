package stupaq.labview;

import com.google.common.base.Function;

import com.jacob.activeX.ActiveXComponent;

public abstract class CheckedVI extends VI {

  protected CheckedVI(Application application, VIName viName) {
    super(application, viName);
  }

  @Override
  protected <Result> Result run(final Function<ActiveXComponent, Result> runner) throws VIErrorException {
    return super.run(new Function<ActiveXComponent, Result>() {
      @Override
      public Result apply(ActiveXComponent vi) {
        Result result =  runner.apply(vi);
        vi.
      }
    });
  }
}
