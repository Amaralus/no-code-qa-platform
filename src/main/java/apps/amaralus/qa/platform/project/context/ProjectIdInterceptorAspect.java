package apps.amaralus.qa.platform.project.context;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
@Aspect
public class ProjectIdInterceptorAspect {

    @Before("@annotation(apps.amaralus.qa.platform.project.context.InterceptProjectId)")
    public void before(JoinPoint joinPoint)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        var signature = (MethodSignature) joinPoint.getSignature();
        var parameterName = signature.getMethod().getDeclaredAnnotation(InterceptProjectId.class).parameterName();

        String projectId = null;
        for (int i = 0; i < signature.getParameterNames().length; i++)
            if (signature.getParameterNames()[i].equals(parameterName))
                projectId = (String) joinPoint.getArgs()[i];

        if (projectId == null)
            throw new IllegalArgumentException(String.format("Parameter \"%s\" not found in method %s.%s()",
                    parameterName, signature.getDeclaringType().getName(), signature.getName()));

        @SuppressWarnings("unchecked") var projectContext = (DefaultProjectContext) signature
                .getDeclaringType()
                .getSuperclass()
                .getDeclaredMethod("getProjectContext")
                .invoke(joinPoint.getTarget());
        projectContext.setProjectId(projectId);
    }
}
