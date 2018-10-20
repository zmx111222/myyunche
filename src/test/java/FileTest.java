import org.junit.Test;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: ZhongMingxiao
 * @create: 2018-09-22 18:02
 * @description: 文件流测试
 **/
public class FileTest
{
    @Test
    public void fileDescriptor() throws IOException {
        FileOutputStream fos = new FileOutputStream(FileDescriptor.out);
        fos.write("FileDescriptor：这里证明了System.out,就是使用FileDescriptor.out来创建的\r".getBytes());
        System.out.println("System.out:控制台输出");
        FileOutputStream fos1 = new FileOutputStream("./src/file/test.txt");
        FileOutputStream fos2 = new FileOutputStream("./src/file/test.txt");
        System.out.println("fos1:"+fos1.getFD());
        System.out.println("fos2:"+fos2.getFD());
        fos.close();
        fos1.close();
        fos2.close();


       }
}
