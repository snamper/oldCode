
//������

        $(function(){            
            //��ȡ��ǰ������Ŀ��
           var winWidth=$(window).width();
           //��ȡ��ǰ������ĸ߶�
           var winHeight=$(window).height();
           //�����ĸ߶�--��ʾ�򿪻�ر�
           var titHeight=$("#msg_title").height();
           //���ݲ�ĸ߶�
           var conHeight=$("#msg_content").height();
           //��ʱ����,�洢���ݲ�ĸ߶�
           var temp = conHeight;
           
            //alert(conHeight);
            //����ʱ���ò��λ��
           $("#msg").css("visibility","visible");
            $("#msg").css({top:$(window).height()-$("#msg_title").height(),left:$(window).width()-$("#msg_title").width()-16});
            $("#msg").height(0);
            //����ı��Сʱ,��������
            $(window).resize(function(){
                //���»�ȡ���ڵĿ��
                winWidth=$(window).width();
                winHeight=$(window).height();
                $("#msg").css({top:winHeight-$("#msg_title").height()-conHeight,left:winWidth-$("#msg_title").width()-16});
           });
           
            $(window).scroll(function(){
                 //���»�ȡ���ڵĿ��
                winWidth=$(window).width();
                winHeight=$(window).height();
                $("#msg").css({top:winHeight-$("#msg_title").height()-conHeight+$(window).scrollTop()});        
           });    
            //�򿪹��ر�
            $("#msg_title_right").toggle(function(){
                //�ı���ʾ
                //$("#msg_title_right").text("�ر�");
                //��ԭ���ݲ�ĸ߶�
                //alert(titHeight+conHeight);
                //return;
                $("#msg_content").height(temp);
                conHeight=temp;
                //����--һ������Ϣ��߶�����,top����
                //$("#msg").height($(this).height()+temp);
                $("#msg").animate({top:winHeight-titHeight-conHeight+$(window).scrollTop(),height:titHeight+conHeight},1000,function(){                    
                    //չ����ִ�еĺ���
                });
            },function(){
            	//clearTimeout(noticeClosetimer);
                //�ı���ʾ
                //$("#msg_title_right").text("��");                
                //alert(temp+" "+titHeight);
                $("#msg").animate({top:winHeight-titHeight+$(window).scrollTop(),height:0},1000,function(){
                    //�رպ�ִ�еĺ���
                    conHeight=0;
                    //�������ݲ�ĸ߶�Ϊ0
                    $("#msg").height(0);
                });
            })
            
            
            
            //ִ��,û���õ�
            var myTimer,i=8;
            function starFun()
            {
                //����click�¼�,��ʾ            
                if(i==4)
                {
                    $("#msg_title_right").click();
                }
                //���timeout,����click�¼�,�رղ�
                if(i==0)
                {
                    window.clearTimeout(myTimer);
                    if($("#msg_title_right").text()!="��")
                    $("#msg_title_right").click();
                }
                myTimer=window.setTimeout(starFun,1000);
                i=i-1;
            }
            
            //ȥ��ע�͵Ļ�����ÿ�δ�ҳ���Զ�ִ��
            //starFun()  
        });
        

