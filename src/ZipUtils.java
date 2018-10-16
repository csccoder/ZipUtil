import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ZIP 压缩工具类
 */
public class ZipUtils {
    /**
     * 创建压缩包入口方法
     * @param sourceFilePath 源文件/文件夹目录
     * @param targetZipFilePath 创建压缩包目录
     * @throws FileNotFoundException
     */
    public static void createZip(String sourceFilePath , String targetZipFilePath) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(targetZipFilePath));
        writeZip(zos, new File(sourceFilePath), "");
        zos.close();
    }

    public static void createZipDemo(String sourceFilePath , String targetZipFilePath) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(targetZipFilePath));
        //创建zip压缩包
        writeZip(zos, new File(sourceFilePath), "");
        //再往压缩包中新添加一个文件夹xx (文件夹必须以/结尾)
        zos.putNextEntry(new ZipEntry("xx/"));
        //再往压缩包中压缩一个视频文件 （压缩一个文件，会自动创建中间目录）
        ZipEntry zipEntry = new ZipEntry("ssr/牛客网视频/chapter1/1.mp4");
        zos.putNextEntry(zipEntry);
        FileInputStream fis = null;
        try{
            fis = new FileInputStream("C:\\Users\\chenny\\Desktop\\高级-01.mp4");
            byte [] content=new byte[1024];
            int len;
            while((len=fis.read(content))!=-1) {
                zos.write(content, 0, len);
                zos.flush();

            }
            zos.closeEntry();
        }catch (Exception e){
            if(fis!=null){
                fis.close();
            }
        }
        zos.close();
    }

    /**
     * 创建zip压缩包核心方法
     * @param zos
     * @param file
     * @param parentPath
     * @throws IOException
     */
    private static void writeZip(ZipOutputStream zos, File file, String parentPath) throws IOException {
        if(file.exists()){
            if(file.isDirectory()){//当前file为目录
                File[] subFiles = file.listFiles();
                if(subFiles != null && subFiles.length >0){
                    parentPath+=file.getName()+File.separator;
                    for(File subFile : subFiles){
                        writeZip(zos,subFile,parentPath);
                    }
                }else{//空目录 直接创建空目录
                    ZipEntry zipEntry = new ZipEntry(parentPath);//文件夹需以/结尾
                    zos.putNextEntry(zipEntry);
                    zos.closeEntry();
                }
            }else{//压缩文件  创建一个文件，其中间目录会自动创建
                BufferedInputStream fis = null;
                try{
                    ZipEntry zipEntry = new ZipEntry(parentPath+file.getName());
                    zos.putNextEntry(zipEntry);
                    fis = new BufferedInputStream(new FileInputStream(file));
                    byte [] content=new byte[1024];
                    int len;
                    while((len=fis.read(content))!=-1) {
                        zos.write(content, 0, len);
                        zos.flush();
                    }
                    zos.closeEntry();
                }catch (Exception e){
                    if(fis!=null){
                        fis.close();
                    }
                }

            }

        }
    }
}
