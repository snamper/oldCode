package com.agents.servlet;


import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import java.awt.Font;

public class RandomServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "image/jpeg";

    //Initialize global variables
    public void init() throws ServletException {
    }

    //���������ɫ����getRandColor
    Color getRandColor(int fc, int bc) {
        Random r = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int red = fc + r.nextInt(bc - fc); //��
        int green = fc + r.nextInt(bc - fc); //��
        int blue = fc + r.nextInt(bc - fc); //��
        return new Color(red, green, blue);
    }

    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //����IE�����͵���һ��ͼƬ
        response.setContentType(CONTENT_TYPE);
        HttpSession session = request.getSession();
        //����ҳ�治����
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //���������
        Random r = new Random();

        // ���ڴ��д���ͼ�񣬿��Ϊwidth���߶�Ϊheight
        int width = 60, height = 20;
        BufferedImage pic = new BufferedImage(width, height,
                                              BufferedImage.TYPE_INT_RGB);

        // ��ȡͼ�������Ļ���
        Graphics gc = pic.getGraphics();

        // �趨����ɫ���������
        gc.setColor(getRandColor(200, 250));
        gc.fillRect(0, 0, width, height);

        //�趨ͼ�������Ļ�������
        gc.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        // �������200������ֱ�ߣ�ʹͼ���е���֤�벻�ױ�������������̽�⵽
        for (int i = 0; i < 200; i++) {
            int x1 = r.nextInt(width);
            int y1 = r.nextInt(height);
            int x2 = r.nextInt(15);
            int y2 = r.nextInt(15);
            gc.setColor(getRandColor(160, 200));
            gc.drawLine(x1, y1, x1 + x2, y1 + y2);
        }
        //�������100�����ŵ㣬ʹͼ���е���֤�벻�ױ�������������̽�⵽

        for (int i = 0; i < 100; i++) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);
            gc.setColor(getRandColor(120, 240));
            gc.drawOval(x, y, 0, 0);
        }

        // �������4λ���ֵ���֤��
        String RS = "";
        String rn = "";
        RS = r.nextInt(9000) + 1000 + "";
        /*
         for(int i=0;i<4;i++){
          //����10�����������rn
          rn=String.valueOf(r.nextInt(10));
          RS+=rn;
          //����֤����drawString������ʾ��ͼ����
         gc.setColor(new Color(20+r.nextInt(110),20+r.nextInt(110),20+r.nextInt(110)));
          gc.drawString(rn,13*i+6,16);
         }
         */
        gc.setColor(new Color(20 + r.nextInt(110), 20 + r.nextInt(110),
                              20 + r.nextInt(110)));
        gc.drawString(RS, 10, 16);

        // �ͷ�ͼ�������Ļ���
        gc.dispose();

        // ����֤��RS����SESSION�й���
        session.setAttribute("random", RS);

        // ������ɺ����֤��ͼ��ҳ��
        ImageIO.write(pic, "JPEG", response.getOutputStream());

    }

    //Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }

    //Clean up resources
    public void destroy() {
    }
}
