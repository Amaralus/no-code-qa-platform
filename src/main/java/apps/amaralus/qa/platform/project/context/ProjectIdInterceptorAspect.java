package apps.amaralus.qa.platform.project.context;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class ProjectIdInterceptorAspect {

    private final ThreadLocalProjectContext projectContext;

    @Around("@annotation(apps.amaralus.qa.platform.project.context.InterceptProjectId)")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {

        var signature = (MethodSignature) joinPoint.getSignature();
        var parameterName = signature.getMethod().getDeclaredAnnotation(InterceptProjectId.class).parameterName();

        String projectId = null;
        for (int i = 0; i < signature.getParameterNames().length; i++)
            if (signature.getParameterNames()[i].equals(parameterName))
                projectId = (String) joinPoint.getArgs()[i];

        if (projectId == null)
            throw new IllegalArgumentException(String.format("Parameter \"%s\" not found in method %s.%s()",
                    parameterName, signature.getDeclaringType().getName(), signature.getName()));

        projectContext.setProjectId(projectId);

        try {
            return joinPoint.proceed();
        } finally {
            projectContext.clear();
        }
    }
}
