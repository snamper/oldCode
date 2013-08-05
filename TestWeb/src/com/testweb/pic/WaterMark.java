package com.testweb.pic;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 类名称：TestPic   
 * 类描述：
 * 创建人: renfy   
 * 创建时间：2012-1-6 下午03:45:12   
 * @version 1.0
 *  
 */
public class WaterMark {
	  /**  
     * 获取指定矩形中的像素的矩阵  
     *   
     * @param imageSrc  
     * @param startX  
     * @param startY  
     * @param w  
     * @param h  
     * @return  
     */  
    private int[] getPixArray(Image imageSrc, int startX, int startY,   
            int w, int h) {   
        int[] pix = new int[(w - startX) * (h - startY)];   
           
        /*下面是别人程序中的一段,我实在不明白为何要加这一段,因为我去掉也没有问题,加上还会报错*/  
        PixelGrabber pg = null;   
        try {   
            pg = new PixelGrabber(imageSrc, startX, startY, w-startX, h-startY, pix, 0, w);   
            if (pg.grabPixels() != true) {   
                try {   
                    throw new java.awt.AWTException("pg error" + pg.status());   
                } catch (Exception eq) {   
                    eq.printStackTrace();   
                }   
            }   
        } catch (Exception ex) {   
        }   
        return pix;   
    }   
  
    /**  
     * 将1张图片和另1张图片的指定区域重合。可用于制作水印。图片的左上角坐标为0，0  
     *   
     * @param lightnessWaterImg  
     *            颜色比较亮的水印图片，适合底色比较暗的情况  
     * @param darknessWaterImg  
     *            颜色比较暗的水印图片，适合底色比较亮的情况,如果不想区分，则输入null，平均灰度边界同时失效。  
     * @param targetImg  
     *            源图片  
     * @param startX  
     * @param startY  
     * @param x  
     * @param y  
     * @param alpha  
     *            透明度,0f为全透明,1f为完全不透明,0.5f为半透明  
     * @param averageGray  
     *            平均灰度边界（0-255），大于此值，则打暗的水印图片，小于此值则打亮的水印图片。  
     *            默认值128。超过范围，按默认值进行。  
     */  
    private final void pressImage(String lightnessWaterImg,   
            String darknessWaterImg, String targetImg, int startX, int startY,   
            int x, int y, float alpha, float averageGray) {   
        try {   
            // 先判断亮水印和源文件的值是否为null，否则抛出异常   
            if (lightnessWaterImg == null || lightnessWaterImg == ""  
                    || targetImg == null || targetImg == "") {   
                throw new Exception("亮水印或者源图片的地址不能为空");   
            }    
            // 再判断平均灰度边界是否越界   
            if (averageGray>255||averageGray<0) {   
                averageGray = 128;   
            }   
               
  
            // 装载源图片   
            File _file = new File(targetImg);   
            // 图片装入内存   
            BufferedImage src = ImageIO.read(_file);   
            // 获取图片的尺寸   
            int width = src.getWidth(null);   
            int height = src.getHeight(null);   
            // 根据源图片尺寸，设置预装载的一个图片，默认是RGB格式的   
            BufferedImage image = new BufferedImage(width, height,   
                    BufferedImage.TYPE_INT_RGB);   
            Graphics2D graphics = image.createGraphics();   
            // 绘制内存中的源图片至指定的矩形内   
            graphics.drawImage(src, 0, 0, width, height, null);   
            // 在已经绘制的图片中加入透明度通道   
            graphics.setComposite(AlphaComposite.getInstance(   
                    AlphaComposite.SRC_ATOP, alpha));   
               
  
            // 获取源图片中和设定的同样大小的区域内的像素集合   
            int[] pixels = getPixArray(src, startX, startY, x, y);   
  
            //查询此集合的平均灰度   
            float average = getAverageGrap(x-startX,y-startY,pixels);   
  
            // 如果平均灰度大于130,则说明此区域比较亮，否则则比较暗   
            System.out.println(average);   
  
               
            //装载水印图片所需参数   
            File water;   
            BufferedImage bufferwater;   
               
            // 根据设定的平均灰度边界来装载不同的水印   
            if (darknessWaterImg == null||average>=averageGray) {   
                // 装载亮水印文件   
                water = new File(darknessWaterImg);   
            }else{   
                // 装载暗水印文件   
                water = new File(lightnessWaterImg);   
            }   
            // 装入内存   
            bufferwater = ImageIO.read(water);   
                           
            graphics.drawImage(bufferwater, startX, startY, x, y,   
                    null);   
            // 水印文件结束   
            graphics.dispose();   
            FileOutputStream out = new FileOutputStream(targetImg);   
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
            // 绘制新的文件   
            encoder.encode(image);   
            out.close();   
               
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
           
    /**  
     * 查询某个区域的平均灰度  
     * @param width  
     * @param height  
     * @param pixels  
     * @return  
     */  
    private float getAverageGrap(int width,int height,int[] pixels){   
        /* 下面是开始算这个区域的亮度了，灰度等同于亮度 */  
        ColorModel colorModel = ColorModel.getRGBdefault();   
        int i = 0;   
        int j = 0;   
        int k = 0;   
        int r = 0;   
        int g = 0;   
        int b = 0;   
        int gray = 0;   
        float average = 0;// 平均灰度   
        for (i = 0; i < height; i++) {   
            for (j = 0; j < width; j++) {   
                // 定位像素点   
                k = i * width + j;   
                r = colorModel.getRed(pixels[k]);   
                g = colorModel.getGreen(pixels[k]);   
                b = colorModel.getBlue(pixels[k]);   
  
                // 计算灰度值   
                gray = (r * 38 + g * 75 + b * 15) >> 7;   
  
                average = average + gray;   
            }   
        }   
        // 计算平均灰度   
        average = average / ((i - 1) * (j - 1));   
        return average;   
    }   
    public static void main(String[] args) {   
        WaterMark waterMark = new WaterMark();   
  
//        waterMark.pressImage("d:/a/2.png", "d:/a/2.png","d:/a/1.png", 520, 500, 900, 800, 0.5f, 50);   
        waterMark.pressImage("d:/a/2.png", "d:/a/2.png","d:/a/1.jpg", 120, 62, 300, 135, 1f, 50);   
        System.out.print("添加成功");   
    }   
}
