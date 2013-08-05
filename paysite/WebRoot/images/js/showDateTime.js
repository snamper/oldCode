
function getCurDate(divT)
{
var d = new Date();
var week;
switch (d.getDay()){
case 1: week="星期一"; break;
case 2: week="星期二"; break;
case 3: week="星期三"; break;
case 4: week="星期四"; break;
case 5: week="星期五"; break;
case 6: week="星期六"; break;
default: week="星期日";
}
var years = d.getYear();
var month = add_zero(d.getMonth()+1);
var days = add_zero(d.getDate());
var hours = add_zero(d.getHours());
var minutes = add_zero(d.getMinutes());
var seconds=add_zero(d.getSeconds());
var ndate = years+"年"+month+"月"+days+"日 "+week;
document.getElementById(divT).innerHTML= ndate;
}

function getCurTime(divT1)
{
var d = new Date();
var hours = add_zero(d.getHours());
var minutes = add_zero(d.getMinutes());
var seconds=add_zero(d.getSeconds());
var ndate = hours+":"+minutes+":"+seconds;
document.getElementById(divT1).innerHTML= ndate;

}

function add_zero(temp)
{
if(temp<10) return "0"+temp;
else return temp;
}
