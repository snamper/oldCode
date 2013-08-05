
//弹出层

        $(function(){            
            //获取当前浏览器的宽度
           var winWidth=$(window).width();
           //获取当前浏览器的高度
           var winHeight=$(window).height();
           //标题层的高度--提示打开或关闭
           var titHeight=$("#msg_title").height();
           //内容层的高度
           var conHeight=$("#msg_content").height();
           //临时变量,存储内容层的高度
           var temp = conHeight;
           
            //alert(conHeight);
            //加载时设置层的位置
           $("#msg").css("visibility","visible");
            $("#msg").css({top:$(window).height()-$("#msg_title").height(),left:$(window).width()-$("#msg_title").width()-16});
            $("#msg").height(0);
            //窗体改变大小时,重新设置
            $(window).resize(function(){
                //重新获取窗口的宽高
                winWidth=$(window).width();
                winHeight=$(window).height();
                $("#msg").css({top:winHeight-$("#msg_title").height()-conHeight,left:winWidth-$("#msg_title").width()-16});
           });
           
            $(window).scroll(function(){
                 //重新获取窗口的宽高
                winWidth=$(window).width();
                winHeight=$(window).height();
                $("#msg").css({top:winHeight-$("#msg_title").height()-conHeight+$(window).scrollTop()});        
           });    
            //打开过关闭
            $("#msg_title_right").toggle(function(){
                //改变提示
                //$("#msg_title_right").text("关闭");
                //还原内容层的高度
                //alert(titHeight+conHeight);
                //return;
                $("#msg_content").height(temp);
                conHeight=temp;
                //动画--一秒内消息层高度增加,top增加
                //$("#msg").height($(this).height()+temp);
                $("#msg").animate({top:winHeight-titHeight-conHeight+$(window).scrollTop(),height:titHeight+conHeight},1000,function(){                    
                    //展开后执行的函数
                });
            },function(){
            	//clearTimeout(noticeClosetimer);
                //改变提示
                //$("#msg_title_right").text("打开");                
                //alert(temp+" "+titHeight);
                $("#msg").animate({top:winHeight-titHeight+$(window).scrollTop(),height:0},1000,function(){
                    //关闭后执行的函数
                    conHeight=0;
                    //设置内容层的高度为0
                    $("#msg").height(0);
                });
            })
            
            
            
            //执行,没有用到
            var myTimer,i=8;
            function starFun()
            {
                //触发click事件,显示            
                if(i==4)
                {
                    $("#msg_title_right").click();
                }
                //清除timeout,触发click事件,关闭层
                if(i==0)
                {
                    window.clearTimeout(myTimer);
                    if($("#msg_title_right").text()!="打开")
                    $("#msg_title_right").click();
                }
                myTimer=window.setTimeout(starFun,1000);
                i=i-1;
            }
            
            //去掉注释的话就是每次打开页面自动执行
            //starFun()  
        });
        

