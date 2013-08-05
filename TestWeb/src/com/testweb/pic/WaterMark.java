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
 * �����ƣ�TestPic   
 * ��������
 * ������: renfy   
 * ����ʱ�䣺2012-1-6 ����03:45:12   
 * @version 1.0
 *  
 */
public class WaterMark {
	  /**  
     * ��ȡָ�������е����صľ���  
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
           
        /*�����Ǳ��˳����е�һ��,��ʵ�ڲ�����Ϊ��Ҫ����һ��,��Ϊ��ȥ��Ҳû������,���ϻ��ᱨ��*/  
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
     * ��1��ͼƬ����1��ͼƬ��ָ�������غϡ�����������ˮӡ��ͼƬ�����Ͻ�����Ϊ0��0  
     *   
     * @param lightnessWaterImg  
     *            ��ɫ�Ƚ�����ˮӡͼƬ���ʺϵ�ɫ�Ƚϰ������  
     * @param darknessWaterImg  
     *            ��ɫ�Ƚϰ���ˮӡͼƬ���ʺϵ�ɫ�Ƚ��������,����������֣�������null��ƽ���Ҷȱ߽�ͬʱʧЧ��  
     * @param targetImg  
     *            ԴͼƬ  
     * @param startX  
     * @param startY  
     * @param x  
     * @param y  
     * @param alpha  
     *            ͸����,0fΪȫ͸��,1fΪ��ȫ��͸��,0.5fΪ��͸��  
     * @param averageGray  
     *            ƽ���Ҷȱ߽磨0-255�������ڴ�ֵ����򰵵�ˮӡͼƬ��С�ڴ�ֵ�������ˮӡͼƬ��  
     *            Ĭ��ֵ128��������Χ����Ĭ��ֵ���С�  
     */  
    private final void pressImage(String lightnessWaterImg,   
            String darknessWaterImg, String targetImg, int startX, int startY,   
            int x, int y, float alpha, float averageGray) {   
        try {   
            // ���ж���ˮӡ��Դ�ļ���ֵ�Ƿ�Ϊnull�������׳��쳣   
            if (lightnessWaterImg == null || lightnessWaterImg == ""  
                    || targetImg == null || targetImg == "") {   
                throw new Exception("��ˮӡ����ԴͼƬ�ĵ�ַ����Ϊ��");   
            }    
            // ���ж�ƽ���Ҷȱ߽��Ƿ�Խ��   
            if (averageGray>255||averageGray<0) {   
                averageGray = 128;   
            }   
               
  
            // װ��ԴͼƬ   
            File _file = new File(targetImg);   
            // ͼƬװ���ڴ�   
            BufferedImage src = ImageIO.read(_file);   
            // ��ȡͼƬ�ĳߴ�   
            int width = src.getWidth(null);   
            int height = src.getHeight(null);   
            // ����ԴͼƬ�ߴ磬����Ԥװ�ص�һ��ͼƬ��Ĭ����RGB��ʽ��   
            BufferedImage image = new BufferedImage(width, height,   
                    BufferedImage.TYPE_INT_RGB);   
            Graphics2D graphics = image.createGraphics();   
            // �����ڴ��е�ԴͼƬ��ָ���ľ�����   
            graphics.drawImage(src, 0, 0, width, height, null);   
            // ���Ѿ����Ƶ�ͼƬ�м���͸����ͨ��   
            graphics.setComposite(AlphaComposite.getInstance(   
                    AlphaComposite.SRC_ATOP, alpha));   
               
  
            // ��ȡԴͼƬ�к��趨��ͬ����С�������ڵ����ؼ���   
            int[] pixels = getPixArray(src, startX, startY, x, y);   
  
            //��ѯ�˼��ϵ�ƽ���Ҷ�   
            float average = getAverageGrap(x-startX,y-startY,pixels);   
  
            // ���ƽ���Ҷȴ���130,��˵��������Ƚ�����������Ƚϰ�   
            System.out.println(average);   
  
               
            //װ��ˮӡͼƬ�������   
            File water;   
            BufferedImage bufferwater;   
               
            // �����趨��ƽ���Ҷȱ߽���װ�ز�ͬ��ˮӡ   
            if (darknessWaterImg == null||average>=averageGray) {   
                // װ����ˮӡ�ļ�   
                water = new File(darknessWaterImg);   
            }else{   
                // װ�ذ�ˮӡ�ļ�   
                water = new File(lightnessWaterImg);   
            }   
            // װ���ڴ�   
            bufferwater = ImageIO.read(water);   
                           
            graphics.drawImage(bufferwater, startX, startY, x, y,   
                    null);   
            // ˮӡ�ļ�����   
            graphics.dispose();   
            FileOutputStream out = new FileOutputStream(targetImg);   
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
            // �����µ��ļ�   
            encoder.encode(image);   
            out.close();   
               
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
           
    /**  
     * ��ѯĳ�������ƽ���Ҷ�  
     * @param width  
     * @param height  
     * @param pixels  
     * @return  
     */  
    private float getAverageGrap(int width,int height,int[] pixels){   
        /* �����ǿ�ʼ���������������ˣ��Ҷȵ�ͬ������ */  
        ColorModel colorModel = ColorModel.getRGBdefault();   
        int i = 0;   
        int j = 0;   
        int k = 0;   
        int r = 0;   
        int g = 0;   
        int b = 0;   
        int gray = 0;   
        float average = 0;// ƽ���Ҷ�   
        for (i = 0; i < height; i++) {   
            for (j = 0; j < width; j++) {   
                // ��λ���ص�   
                k = i * width + j;   
                r = colorModel.getRed(pixels[k]);   
                g = colorModel.getGreen(pixels[k]);   
                b = colorModel.getBlue(pixels[k]);   
  
                // ����Ҷ�ֵ   
                gray = (r * 38 + g * 75 + b * 15) >> 7;   
  
                average = average + gray;   
            }   
        }   
        // ����ƽ���Ҷ�   
        average = average / ((i - 1) * (j - 1));   
        return average;   
    }   
    public static void main(String[] args) {   
        WaterMark waterMark = new WaterMark();   
  
//        waterMark.pressImage("d:/a/2.png", "d:/a/2.png","d:/a/1.png", 520, 500, 900, 800, 0.5f, 50);   
        waterMark.pressImage("d:/a/2.png", "d:/a/2.png","d:/a/1.jpg", 120, 62, 300, 135, 1f, 50);   
        System.out.print("��ӳɹ�");   
    }   
}
