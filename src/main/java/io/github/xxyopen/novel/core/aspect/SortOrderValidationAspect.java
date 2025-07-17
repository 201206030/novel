package io.github.xxyopen.novel.core.aspect;

import io.github.xxyopen.novel.core.annotation.ValidateSortOrder;
import io.github.xxyopen.novel.core.common.req.PageReqDto;
import io.github.xxyopen.novel.core.common.util.SortWhitelistUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

/**
 * 排序字段和排序方式的安全校验切面类
 * <p>
 * 该切面用于拦截所有 Mapper 方法的调用，对带有 @ValidateSortOrder 注解的参数进行统一处理，
 * 校验并清理其中的排序字段（sort）和排序方式（order）参数，防止 SQL 注入攻击。
 * <p>
 * 支持处理以下类型的参数：
 * - PageReqDto 类型对象
 * - Map 类型参数
 * - 任意带有 sort/order 字段的 POJO 对象
 *
 * @author xiongxiaoyang
 * @date 2025/7/17
 */
@Aspect
@Component
@RequiredArgsConstructor
public class SortOrderValidationAspect {

    /**
     * 拦截所有 Mapper 方法的调用，检查参数中是否包含 @ValidateSortOrder 注解。
     * 如果有，则对参数中的 sort 和 order 字段进行安全校验和清理。
     */
    @SneakyThrows
    @Around("execution(* io.github.xxyopen.*.dao.mapper.*Mapper.*(..))")
    public Object processSortOrderFields(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 获取方法参数上的所有注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        // 遍历所有参数，检查是否有 @ValidateSortOrder 注解
        for (int i = 0; i < parameterAnnotations.length; i++) {
            boolean hasAnnotation = Arrays.stream(parameterAnnotations[i])
                .anyMatch(a -> a.annotationType().equals(ValidateSortOrder.class));

            if (hasAnnotation && args[i] != null) {
                // 对带注解的参数进行处理
                handleAnnotatedParameter(args[i]);
            }
        }

        // 继续执行原方法
        return joinPoint.proceed(args);
    }

    /**
     * 根据参数类型，分别处理不同形式的 sort/order 字段。
     */
    @SneakyThrows
    private void handleAnnotatedParameter(Object obj) {
        if (obj instanceof PageReqDto dto) {
            processPageReqDto(dto);
        } else if (obj instanceof Map<?, ?> map) {
            processMap(map);
        } else {
            processGenericObject(obj);
        }
    }

    /**
     * 处理 PageReqDto 类型参数中的 sort 和 order 字段。
     */
    private void processPageReqDto(PageReqDto dto) {
        if (dto.getSort() != null) {
            dto.setSort(SortWhitelistUtil.sanitizeColumn(dto.getSort()));
        }
        if (dto.getOrder() != null) {
            dto.setOrder(SortWhitelistUtil.sanitizeOrder(dto.getOrder()));
        }
    }

    /**
     * 处理 Map 类型参数中的 sort 和 order 字段。
     */
    private void processMap(Map map) {
        if (map.get("sort") instanceof String sortStr) {
            map.put("sort", SortWhitelistUtil.sanitizeColumn(sortStr));
        }
        if (map.get("order") instanceof String orderStr) {
            map.put("order", SortWhitelistUtil.sanitizeOrder(orderStr));
        }
    }

    /**
     * 使用反射处理任意对象中的 sort 和 order 字段。
     * 支持任何带有这两个字段的 POJO。
     */
    @SneakyThrows
    private void processGenericObject(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            switch (field.getName()) {
                case "sort", "order" -> {
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    if (value instanceof String strValue) {
                        String sanitized = "sort".equals(field.getName())
                            ? SortWhitelistUtil.sanitizeColumn(strValue)
                            : SortWhitelistUtil.sanitizeOrder(strValue);
                        field.set(obj, sanitized);
                    }
                }
                default -> {
                    // 忽略其他字段
                }
            }
        }
    }
}