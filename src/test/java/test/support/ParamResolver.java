package test.support;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import test.support.util.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ParamResolver implements ParameterResolver {

  private static final String ERR_NOT_REGISTERED =
      "Class %s not registered in param resolver";

  private static final Map<Class<?>, Object> INJECTABLES = new HashMap<Class<?>, Object>(){{
    put(AuthenticatedRequestFactory.class, null);
    put(AuthUtil.class, null);
    put(QuestionUtil.class, null);
    put(RequestFactory.class, null);
    put(StepUtil.class, null);
    put(UserUtil.class, null);
  }};

  @Override
  public boolean supportsParameter(ParameterContext paramCtx,
      ExtensionContext extCtx) throws ParameterResolutionException {
    return INJECTABLES.containsKey(paramCtx.getParameter().getType());
  }

  @Override
  public Object resolveParameter(ParameterContext paramCtx,
      ExtensionContext extCtx) throws ParameterResolutionException {
    final Class cls = paramCtx.getParameter().getType();

    if (!INJECTABLES.containsKey(cls))
      throw new ParameterResolutionException(String.format(ERR_NOT_REGISTERED,
          cls.getSimpleName()));

    return INJECTABLES.computeIfAbsent(paramCtx.getParameter().getType(),
        this::getOrCreate);
  }

  private Object getOrCreate(Class<?> type) {
    if (type.equals(AuthenticatedRequestFactory.class))
      return AuthenticatedRequestFactory
          .getInstance((AuthUtil) getOrCreate(AuthUtil.class));

    try {
      return type.getMethod("getInstance").invoke(null);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }
}
