import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @PackageName: PACKAGE_NAME
 * @ClassName: Demo
 * @Author: dongxiyaohui
 * @Date: 2019/12/12 20:23
 * @Description: //TODO
 */
public class Demo {
    public static void main(String[] args) {
        Calendar instance = Calendar.getInstance();
        instance.setFirstDayOfWeek(Calendar.MONDAY);
        instance.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        Date time = instance.getTime();
        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(time));
    }
}
