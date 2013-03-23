package com.vvvv.common.tool.common;  
  
import java.awt.AlphaComposite;  
import java.awt.Color;  
import java.awt.Font;  
import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.FileInputStream;   
import java.io.IOException;  
import java.io.InputStream;
  
import javax.imageio.ImageIO;  
  
import com.mortennobel.imagescaling.AdvancedResizeOp;  
import com.mortennobel.imagescaling.ResampleOp;   
  
/** 
 * @className:ImageUtil.java 
 * @classDescription:Image图像的操作，主要是为了压缩和加水印 
 * @author:xiayingjie 
 * @createTime:2010-11-10 
 */  
public class ImageUtil {  
    /** 
     * 图片水印 
     *  
     * @param pressImg 
     *            水印图片 
     * @param targetImg 
     *            目标图片 
     * @param x 
     *            修正值 默认在中间 
     * @param y 
     *            修正值 默认在中间 
     * @param alpha 
     *            透明度 
     */  
    public final static void pressImage(String pressImg, String targetImg,  
            int x, int y, float alpha) {  
        try {  
            File img = new File(targetImg);  
            Image src = ImageIO.read(img);  
            int wideth = src.getWidth(null);  
            int height = src.getHeight(null);  
            BufferedImage image = new BufferedImage(wideth, height,  
                    BufferedImage.TYPE_INT_RGB);  
            Graphics2D g = image.createGraphics();  
            g.drawImage(src, 0, 0, wideth, height, null);  
            // 水印文件  
            Image src_biao = ImageIO.read(new File(pressImg));  
            int wideth_biao = src_biao.getWidth(null);  
            int height_biao = src_biao.getHeight(null);  
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,  
                    alpha));  
            g.drawImage(src_biao, (wideth - wideth_biao) / 2,  
                    (height - height_biao) / 2, wideth_biao, height_biao, null);  
            // 水印文件结束  
            g.dispose();  
            ImageIO.write((BufferedImage) image, "jpg", img);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 文字水印 
     *  
     * @param pressText 
     *            水印文字 
     * @param targetImg 
     *            目标图片 
     * @param fontName 
     *            字体名称 
     * @param fontStyle 
     *            字体样式 
     * @param color 
     *            字体颜色 
     * @param fontSize 
     *            字体大小 
     * @param x 
     *            修正值 
     * @param y 
     *            修正值 
     * @param alpha 
     *            透明度 
     */  
    public static void pressText(String pressText, String targetImg,  
            String fontName, int fontStyle, Color color, int fontSize, int x,  
            int y, float alpha) {  
        try {  
            File img = new File(targetImg);  
            Image src = ImageIO.read(img);  
            int width = src.getWidth(null);  
            int height = src.getHeight(null);  
            BufferedImage image = new BufferedImage(width, height,  
                    BufferedImage.TYPE_INT_RGB);  
            Graphics2D g = image.createGraphics();  
            g.drawImage(src, 0, 0, width, height, null);  
            g.setColor(color);  
            g.setFont(new Font(fontName, fontStyle, fontSize));  
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,  
                    alpha));  
            g.drawString(pressText, (width - (getLength(pressText) * fontSize))  
                    / 2 + x, (height - fontSize) / 2 + y);  
            g.dispose();  
            ImageIO.write((BufferedImage) image, "jpg", img);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public static int getLength(String text) {  
        int length = 0;  
        for (int i = 0; i < text.length(); i++) {  
            if (new String(text.charAt(i) + "").getBytes().length > 1) {  
                length += 2;  
            } else {  
                length += 1;  
            }  
        }  
        return length / 2;  
    }  
  
    // -----------------压缩图片--------------------  
  
    /** 
     * 压缩图片（默认为jpg格式） 
     *  
     * @param fromPath 
     *            源路径 
     * @param toPath 
     *            输出路径 
     * @param width 
     *            文件转换后的宽度 
     * @param height 
     *            文件转换后的高度 
     * @return 
     */  
    public static boolean resize(String fromPath, String toPath, Integer width,  
            Integer height) {  
        return resize(fromPath, toPath, width, height, false, false, "jpg");  
    }  
  
    /** 
     * 压缩图片（默认为jpg格式） 
     *  
     * @param fromPath 
     *            源路径 
     * @param toPath 
     *            输出路径 
     * @param width 
     *            文件转换后的宽度 
     * @param height 
     *            文件转换后的高度 
     * @return 
     */  
    public static boolean resize(String fromPath, String toPath, Integer width,  
            Integer height, boolean isOtherThan) {  
        return resize(fromPath, toPath, width, height, isOtherThan, true, "jpg");  
    }  
    /** 
     * 压缩图片 
     *  
     * @param fromPath 
     *            源路径 
     * @param toPath 
     *            输出路径 
     * @param width 
     *            文件转换后的宽度 
     * @param height 
     *            文件转换后的高度 
     * @param isOtherThan 
     *            是否等比缩小 true:等比 false:不等比 
     * @param isFiller 
     *            是否补白，前提条件是图片是等比缩小 
     * @param format 
     *            文件压缩后的格式(jpg格式文件小，gif质量差，文件稍微大一点，bmp文件大，清晰度还可以) 
     * @return 
     */  
    public static boolean resize(InputStream is, String toPath, Integer width,  
            Integer height, boolean isOtherThan, boolean isFiller, String format) {  
        try {  
            // 将图片文件读入到缓存中  
            BufferedImage inImage = ImageIO.read(is);  
  
            // System.out.println("转前图片高度和宽度：" + inImage.getHeight() + ":"+  
            // inImage.getWidth());  
            int scaledW = width;  
            int scaledH = height;  
            if (isOtherThan) {  
                int imh = inImage.getWidth();  
                int imw = inImage.getHeight();  
  
                double scale;  
                if (imh <= height && imw <= width)  
                    scale = 1;  
                else if (imh > imw)  
                    scale = (double) height / (double) imh;  
                else  
                    scale = (double) width / (double) imw;  
  
                scaledW = (int) (scale * imw);  
                scaledH = (int) (scale * imh);  
            }  
  
            // 将图片分辨率转成指定分辨率（高+宽）  
            AdvancedResizeOp resampleOp = new ResampleOp(scaledW, scaledH);  
            // 重新生成图片  
            BufferedImage rescaledTomato = resampleOp.filter(inImage, null);  
  
            // 是否补白(必须是等比缩放才会补白)  
            if (isFiller && isOtherThan) {  
                //  
                BufferedImage image = new BufferedImage(width, height,  
                        BufferedImage.TYPE_INT_RGB);  
                Graphics2D g = image.createGraphics();  
                g.setColor(Color.white);  
                g.fillRect(0, 0, width, height);  
                if (width == rescaledTomato.getWidth(null))  
                    g.drawImage(rescaledTomato, 0, (height - rescaledTomato  
                            .getHeight(null)) / 2, rescaledTomato  
                            .getWidth(null), rescaledTomato.getHeight(null),  
                            Color.white, null);  
                else  
                    g.drawImage(rescaledTomato, (width - rescaledTomato  
                            .getWidth(null)) / 2, 0, rescaledTomato  
                            .getWidth(null), rescaledTomato.getHeight(null),  
                            Color.white, null);  
                g.dispose();  
                rescaledTomato = image;  
            }  
  
            // 将文件写入输入文件  
            ImageIO.write(rescaledTomato, format, new File(toPath));  
  
            // System.out.println("转后图片高度和宽度：" + rescaledTomato.getHeight() +  
            // ":"+ rescaledTomato.getWidth());  
            return true;  
        } catch (IOException e) {  
            e.printStackTrace();  
            return false;  
        }  
  
    }  
    /** 
     * 压缩图片 
     *  
     * @param fromPath 
     *            源路径 
     * @param toPath 
     *            输出路径 
     * @param width 
     *            文件转换后的宽度 
     * @param height 
     *            文件转换后的高度 
     * @param isOtherThan 
     *            是否等比缩小 true:等比 false:不等比 
     * @param isFiller 
     *            是否补白，前提条件是图片是等比缩小 
     * @param format 
     *            文件压缩后的格式(jpg格式文件小，gif质量差，文件稍微大一点，bmp文件大，清晰度还可以) 
     * @return 
     */  
    public static boolean resize(String fromPath, String toPath, Integer width,  
            Integer height, boolean isOtherThan, boolean isFiller, String format) {  
        try {  
            // 将图片文件读入到缓存中  
            BufferedImage inImage = ImageIO.read(new FileInputStream(fromPath));  
  
            // System.out.println("转前图片高度和宽度：" + inImage.getHeight() + ":"+  
            // inImage.getWidth());  
            int scaledW = width;  
            int scaledH = height;  
            if (isOtherThan) {  
                int imh = inImage.getWidth();  
                int imw = inImage.getHeight();  
  
                double scale;  
                if (imh <= height && imw <= width)  
                    scale = 1;  
                else if (imh > imw)  
                    scale = (double) height / (double) imh;  
                else  
                    scale = (double) width / (double) imw;  
  
                scaledW = (int) (scale * imw);  
                scaledH = (int) (scale * imh);  
            }  
  
            // 将图片分辨率转成指定分辨率（高+宽）  
            AdvancedResizeOp resampleOp = new ResampleOp(scaledW, scaledH);  
            // 重新生成图片  
            BufferedImage rescaledTomato = resampleOp.filter(inImage, null);  
  
            // 是否补白(必须是等比缩放才会补白)  
            if (isFiller && isOtherThan) {  
                //  
                BufferedImage image = new BufferedImage(width, height,  
                        BufferedImage.TYPE_INT_RGB);  
                Graphics2D g = image.createGraphics();  
                g.setColor(Color.white);  
                g.fillRect(0, 0, width, height);  
                if (width == rescaledTomato.getWidth(null))  
                    g.drawImage(rescaledTomato, 0, (height - rescaledTomato  
                            .getHeight(null)) / 2, rescaledTomato  
                            .getWidth(null), rescaledTomato.getHeight(null),  
                            Color.white, null);  
                else  
                    g.drawImage(rescaledTomato, (width - rescaledTomato  
                            .getWidth(null)) / 2, 0, rescaledTomato  
                            .getWidth(null), rescaledTomato.getHeight(null),  
                            Color.white, null);  
                g.dispose();  
                rescaledTomato = image;  
            }  
  
            // 将文件写入输入文件  
            ImageIO.write(rescaledTomato, format, new File(toPath));  
  
            // System.out.println("转后图片高度和宽度：" + rescaledTomato.getHeight() +  
            // ":"+ rescaledTomato.getWidth());  
            return true;  
        } catch (IOException e) {  
            e.printStackTrace();  
            return false;  
        }  
  
    }  
  
    public static void main(String[] args) {
       // resize("C:/Documents and Settings/Administrator/桌面/Image/vvvv_Android_v2.1.jpg", "C:/Documents and Settings/Administrator/桌面/Image/vvvv_Android.jpg",180,80); 
        String fileName="C:/Program Files/Apache Software Foundation/Tomcat 6.0/webapps/2011051002303018150.jpg";
   //     System.out.println(fileName.lastIndexOf(".")+1);
        String copyFileName=fileName.substring(0, fileName.lastIndexOf(".")).concat("copy.jpg");
    }  
}  