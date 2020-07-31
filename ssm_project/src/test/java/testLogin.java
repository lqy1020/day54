import com.lqy.Utils.EncryptUtils;
import com.lqy.config.SpringMybatis;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @auth lqy
 * @date 2020/7/31
 * @Description
 */

public class testLogin {

    @Test
    public void test1() throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update("admin".getBytes());
        byte[] digest = md5.digest();
        System.out.println(digest);
        System.out.println(EncryptUtils.toHexString(digest));
        md5.update((EncryptUtils.toHexString(digest)+"admin").getBytes());
        digest=md5.digest();
        System.out.println(EncryptUtils.toHexString(digest));
    }


}
