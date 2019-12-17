import cn.itcast.util.SMSUtils;
import com.aliyuncs.exceptions.ClientException;

/**
 * @PackageName: PACKAGE_NAME
 * @ClassName: demo1
 * @Author: dongxiyaohui
 * @Date: 2019/12/6 22:32
 * @Description: //TODO
 */
public class demo1 {
    public static void main(String[] args) throws ClientException {
        SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,"18391555262","9527");
    }
}
