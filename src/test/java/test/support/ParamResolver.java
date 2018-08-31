package test.support;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import test.support.util.*;

import java.util.Arrays;

public class ParamResolver implements ParameterResolver {

  private static final Class<?>[] INJECTABLES = {
      Auth.class,
      Questions.class,
      Steps.class,
      StepAnalysis.class,
      Users.class
  };

  @Override
  public boolean supportsParameter(ParameterContext paramCtx,
      ExtensionContext extCtx) throws ParameterResolutionException {
    return Arrays.asList(INJECTABLES)
        .contains(paramCtx.getParameter().getType());
  }

  @Override
  public Object resolveParameter(ParameterContext paramCtx,
      ExtensionContext extCtx) throws ParameterResolutionException {
    try {
      return paramCtx.getParameter()
          .getType()
          .getDeclaredMethod("getInstance")
          .invoke(null);
    } catch (Exception e) {
      throw new ParameterResolutionException(e.getMessage());
    }
  }
}
