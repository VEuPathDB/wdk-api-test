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
      AuthUtil.class,
      QuestionUtil.class,
      AuthenticatedRequestFactory.class,
      StepUtil.class,
      UserUtil.class
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

    if (cls.equals(AuthUtil.class))
      return AuthUtil.getInstance();

    if (cls.equals(QuestionUtil.class))
      return QuestionUtil.getInstance();

    if (cls.equals(AuthenticatedRequestFactory.class))
      return AuthenticatedRequestFactory.getInstance(AuthUtil.getInstance());

    if (cls.equals(StepUtil.class))
      return StepUtil.getInstance();

    if (cls.equals(UserUtil.class))
      return UserUtil.getInstance();

    throw new ParameterResolutionException(String.format(ERR_NOT_REGISTERED,
        cls.getSimpleName()));
  }
}
