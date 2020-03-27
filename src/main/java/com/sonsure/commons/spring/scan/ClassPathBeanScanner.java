package com.sonsure.commons.spring.scan;

import com.sonsure.commons.exception.SonsureException;
import com.sonsure.commons.spring.BundlePathMatchingResourcePatternResolver;
import com.sonsure.commons.spring.PathMatchingResourcePatternResolver;
import com.sonsure.commons.spring.Resource;
import com.sonsure.commons.spring.ResourcePatternResolver;
import com.sonsure.commons.utils.FileIOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liyd
 */
public class ClassPathBeanScanner {

    public static final String CLASSPATH_ALL_URL_PREFIX = "classpath*:";

    public static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    public static final String PATH_SEPARATOR = "/";

    public static final String PACKAGE_SEPARATOR = ".";

    protected static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();

    /**
     * 扫描包下的所有class
     *
     * @param basePackage
     * @return
     */
    public static List<String> scanClasses(String basePackage) {
        return scanClasses(basePackage, RESOURCE_PATTERN_RESOLVER);
    }

    /**
     * 扫描包下的所有class
     *
     * @param basePackage
     * @return
     */
    public static List<String> scanClasses(String basePackage, ClassLoader classLoader) {
        return scanClasses(basePackage, new BundlePathMatchingResourcePatternResolver(classLoader));
    }

    /**
     * 扫描包下的所有class
     *
     * @param basePackage
     * @return
     */
    public static List<String> scanClasses(String basePackage, ResourcePatternResolver resourcePatternResolver) {

        String basePackagePath = StringUtils.replace(basePackage, PACKAGE_SEPARATOR, PATH_SEPARATOR);
        String packageSearchPath = CLASSPATH_ALL_URL_PREFIX + basePackagePath + '/' + DEFAULT_RESOURCE_PATTERN;

        List<String> classes = new ArrayList<>();
        try {
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                try (InputStream is = resource.getInputStream()) {
                    ClassReader classReader = new ClassReader(FileIOUtils.toByteArray(is));
                    String className = StringUtils.replace(classReader.getClassName(), PATH_SEPARATOR, PACKAGE_SEPARATOR);
                    classes.add(className);
                }
            }
        } catch (IOException e) {
            throw new SonsureException("扫描class失败,package:" + basePackage, e);
        }
        return classes;
    }

}
