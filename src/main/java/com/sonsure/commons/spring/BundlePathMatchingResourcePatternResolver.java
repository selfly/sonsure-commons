package com.sonsure.commons.spring;


import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

public class BundlePathMatchingResourcePatternResolver extends PathMatchingResourcePatternResolver {

    public BundlePathMatchingResourcePatternResolver(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    public BundlePathMatchingResourcePatternResolver(ClassLoader classLoader) {
        super(classLoader);
    }

    /**
     * Find all class location resources with the given location via the ClassLoader.
     *
     * @param location the absolute path within the classpath
     * @return the result as Resource array
     * @throws IOException in case of I/O errors
     * @see ClassLoader#getResources
     * @see #convertClassLoaderURL
     */
    @Override
    protected Resource[] findAllClassPathResources(String location) throws IOException {
        String path = location;
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        ClassLoader cl = getClassLoader();
        Enumeration<URL> resourceUrls = (cl != null ? cl.getResources(path) : ClassLoader.getSystemResources(path));
        Set<Resource> result = new LinkedHashSet<Resource>(16);
        while (resourceUrls.hasMoreElements()) {
            URL url = resourceUrls.nextElement();
            try {
                URLConnection urlConnection = url.openConnection();
                String connectionClsName = "org.apache.felix.framework.URLHandlersBundleURLConnection";
                if (connectionClsName.equals(urlConnection.getClass().getName())) {
                    Class<?> aClass = urlConnection.getClass().getClassLoader().loadClass(connectionClsName);
                    Method method = aClass.getDeclaredMethod("getLocalURL", null);
                    method.setAccessible(true);
                    Object invoke = method.invoke(urlConnection);
                    url = ((URL) invoke);
                }
            } catch (Exception e) {
                throw new RuntimeException("获取bundle的localURL失败", e);
            }
            result.add(convertClassLoaderURL(url));
        }
        return result.toArray(new Resource[result.size()]);
    }

}
