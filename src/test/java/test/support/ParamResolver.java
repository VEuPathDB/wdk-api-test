package test.support;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import test.support.util.*;

import java.util.Arrays;

public class ParamResolver implements ParameterResolver {

  private static final String ERR_NOT_REGISTERED =
      "Class %s not registered in param resolver";

  private static final Class<?>[] INJECTABLES = {
      Auth.class,
      Questions.class,
      Requests.class,
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

    if (cls.equals(Requests.class))
      return Requests.getInstance(Auth.getInstance());

    if (cls.equals(Steps.class))
      return Steps.getInstance();

    if (cls.equals(Users.class))
      return Users.getInstance();

    throw new ParameterResolutionException(String.format(ERR_NOT_REGISTERED,
        cls.getSimpleName()));
  }
}
