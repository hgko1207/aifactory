package ins.core.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Stack;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ZipUtils {

    private static Log logger = LogFactory.getLog(ZipUtils.class);

    public static synchronized void unzip(File zippedFile) throws IOException {
        unzip(zippedFile, Charset.defaultCharset().name());
    }

    public static synchronized void unzip(File zippedFile, String charsetName) throws IOException {
        unzip(zippedFile, zippedFile.getParentFile(), charsetName);
    }

    public static synchronized void unzip(File zippedFile, File destDir) throws IOException {
        unzip(new FileInputStream(zippedFile), destDir, Charset.defaultCharset().name());
    }

    public static synchronized void unzip(File zippedFile, File destDir, String charsetName) throws IOException {
        unzip(new FileInputStream(zippedFile), destDir, charsetName);
    }

    public static synchronized void unzip(InputStream is, File destDir) throws IOException {
        unzip(is, destDir, Charset.defaultCharset().name());
    }

    public static synchronized void unzip(InputStream is, File destDir, String charsetName) throws IOException {
        ZipArchiveInputStream zis;
        ZipArchiveEntry entry;
        String name;
        File target;
        int nWritten = 0;
        BufferedOutputStream bos;
        byte[] buf = new byte[1024 * 8];

        zis = new ZipArchiveInputStream(is, charsetName, false);
        while ((entry = zis.getNextZipEntry()) != null) {
            name = URLEncoder.encode(entry.getName().toUpperCase(), "UTF-8");
            target = new File(destDir, name);
            if (entry.isDirectory()) {
                target.mkdirs(); /* does it always work? */
                logger.debug("dir  : " + name);
            } else {
                target.createNewFile();
                bos = new BufferedOutputStream(new FileOutputStream(target));
                while ((nWritten = zis.read(buf)) >= 0) {
                    bos.write(buf, 0, nWritten);
                }
                bos.close();
                logger.debug("file : " + name);
            }
        }
        zis.close();
    }

    /**
     * compresses the given file(or dir) and creates new file under the same
     * directory.
     * 
     * @param src
     *            file or directory
     * @throws IOException
     */
    public static synchronized void zip(File src) throws IOException {
        zip(src, Charset.defaultCharset().name(), true);
    }

    /**
     * zips the given file(or dir) and create
     * 
     * @param src
     *            file or directory to compress
     * @param includeSrc
     *            if true and src is directory, then src is not included in the
     *            compression. if false, src is included.
     * @throws IOException
     */
    public static synchronized void zip(File src, boolean includeSrc) throws IOException {
        zip(src, Charset.defaultCharset().name(), includeSrc);
    }

    /**
     * compresses the given src file (or directory) with the given encoding
     * 
     * @param src
     * @param charSetName
     * @param includeSrc
     * @throws IOException
     */
    public static synchronized void zip(File src, String charSetName, boolean includeSrc) throws IOException {
        zip(src, src.getParentFile(), charSetName, includeSrc);
    }

    /**
     * compresses the given src file(or directory) and writes to the given
     * output stream.
     * 
     * @param src
     * @param os
     * @throws IOException
     */
    public static synchronized void zip(File src, OutputStream os) throws IOException {
        zip(src, os, Charset.defaultCharset().name(), true);
    }

    /**
     * compresses the given src file(or directory) and create the compressed
     * file under the given destDir.
     * 
     * @param src
     * @param destDir
     * @param charSetName
     * @param includeSrc
     * @throws IOException
     */
    public static synchronized void zip(File src, File destDir, String charSetName, boolean includeSrc) throws IOException {
        String fileName = src.getName();
        if (!src.isDirectory()) {
            int pos = fileName.lastIndexOf(".");
            if (pos > 0) {
                fileName = fileName.substring(0, pos);
            }
        }
        fileName += ".zip";

        File zippedFile = new File(destDir, fileName);
        if (!zippedFile.exists())
            zippedFile.createNewFile();
        zip(src, new FileOutputStream(zippedFile), charSetName, includeSrc);
    }

    public static synchronized void zip(File src, OutputStream os, String charsetName, boolean includeSrc) throws IOException {
        ZipArchiveOutputStream zos = new ZipArchiveOutputStream(os);
        zos.setEncoding(charsetName);
        FileInputStream fis;

        int length;
        ZipArchiveEntry ze;
        byte[] buf = new byte[8 * 1024];
        String name;

        Stack<File> stack = new Stack<File>();
        File root;
        if (src.isDirectory()) {
            if (includeSrc) {
                stack.push(src);
                root = src.getParentFile();
            } else {
                File[] fs = src.listFiles();
                for (int i = 0; i < fs.length; i++) {
                    stack.push(fs[i]);
                }
                root = src;
            }
        } else {
            stack.push(src);
            root = src.getParentFile();
        }

        while (!stack.isEmpty()) {
            File f = stack.pop();
            name = toPath(root, f);
            if (f.isDirectory()) {
                logger.debug("dir  : " + name);
                File[] fs = f.listFiles();
                for (int i = 0; i < fs.length; i++) {
                    if (fs[i].isDirectory())
                        stack.push(fs[i]);
                    else
                        stack.add(0, fs[i]);
                }
            } else {
                logger.debug("file : " + name);
                ze = new ZipArchiveEntry(name);
                zos.putArchiveEntry(ze);
                fis = new FileInputStream(f);
                while ((length = fis.read(buf, 0, buf.length)) >= 0) {
                    zos.write(buf, 0, length);
                }
                fis.close();
                zos.closeArchiveEntry();
            }
        }
        zos.close();
    }

    private static synchronized String toPath(File root, File dir) {
        String path = dir.getAbsolutePath();
        path = path.substring(root.getAbsolutePath().length()).replace(File.separatorChar, '/');
        if (path.startsWith("/"))
            path = path.substring(1);
        if (dir.isDirectory() && !path.endsWith("/"))
            path += "/";
        return path;
    }

}
