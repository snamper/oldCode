package com.jinrl.powermodule.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import com.jinrl.powermodule.pojo.Tfunction;
import com.jinrl.powermodule.pojo.Tposition;

public class Tools {

	public static void addSyslog(HttpServletRequest request,String operatorID,String functionID,String opearateInfo,String operateResult,String dataID){
		String sL = "http://localhost:"+request.getLocalPort()+"/commonscan/SyslogServlet?operatorID="+operatorID+"&functionID="+functionID+"&opearateInfo="+opearateInfo+"&operateResult="+operateResult+"&dataID="+dataID;
		fc.SendDataViaPost(sL,"","GB2312");
	}

	/**
	 * ������ֵ
	 * @param obj
	 * @return
	 */
	public static boolean GetisusedValue(Object obj) {
		String value = "1";
		try {
			value = (String) obj.getClass().getMethod("getFisused",
					new Class[] {}).invoke(obj, new Object[] {});
			if (null == value) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if(!"0".equals(value)){
			return false;
		}else{
			return true;
		}

	}

	/**
	 * ȥ��list������ظ�ֵ
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public static List removeDuplicateFunction(List<Tfunction> list) {
//		Set<Tfunction> set = new HashSet<Tfunction>();
//		for(Iterator<Tfunction> it = list.iterator();it.hasNext();){
//			set.add(it.next());
//		}
		Set<Tfunction>  set1 = new TreeSet<Tfunction>(list);
		List<Tfunction> listf = new ArrayList<Tfunction>(set1);
//		return l;
		List<Tfunction> l = new ArrayList<Tfunction>();
		for( Tfunction f1 : listf){
			boolean flag = false;
			for(Tfunction f2 : l){
				if(f1.getFid().equals(f2.getFid())){
					flag = true;
					break;
				}
			}
			if(flag == false){
				l.add(f1);
			}
		}

		//调用排序通用类
        SortList<Tfunction> sortList = new SortList<Tfunction>();

        //按userId排序
        sortList.Sort(l, "getForderbynum", null);
        return l;

	}

	/**
	 * 去除重复的岗位，并排序
	 * @param list
	 * @return
	 */
	public static List<Tposition> removeDuplicatePosition(List<Tposition> list) {
//		Set<Tposition> set = new HashSet<Tposition>(list);
//		for(Iterator<Tposition> it = list.iterator();it.hasNext();){
//			set.add(it.next());
//		}
		Set<Tposition>  set1 = new TreeSet<Tposition>(list);
		List<Tposition> l = new ArrayList<Tposition>(set1);
		return l;

	}
}

//http://jardot.javaeye.com/blog/762349
class SortList<E>{
    public void Sort(List<E> list, final String method, final String sort){
        Collections.sort(list, new Comparator() {
            public int compare(Object a, Object b) {
                int ret = 0;
                try{
                    Method m1 = ((E)a).getClass().getMethod(method, null);
                    Method m2 = ((E)b).getClass().getMethod(method, null);
                    if(sort != null && "desc".equals(sort))//倒序
                        ret = m2.invoke(((E)b), null).toString().compareTo(m1.invoke(((E)a), null).toString());
                    else//正序
                        ret = (Integer)m1.invoke(((E)a), null)-(Integer)(m2.invoke(((E)b), null));
                }catch(NoSuchMethodException ne){
                    System.out.println(ne);
                }catch(IllegalAccessException ie){
                    System.out.println(ie);
                }catch(InvocationTargetException it){
                    System.out.println(it);
                }
                return ret;
            }
         });
    }
}

