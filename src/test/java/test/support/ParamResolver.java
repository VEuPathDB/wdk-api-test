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
      StepAnalysis.class,
      Steps.class,
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
    final Class cls = paramCtx.getParameter().getType();

    if (cls.equals(Auth.class))
      return Auth.getInstance();

    if (cls.equals(Questions.class))
      return Questions.getInstance();

    if (cls.equals(StepAnalysis.class))
      return StepAnalysis.getInstance();

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
