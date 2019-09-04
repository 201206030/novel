window.onload=function(){
   // AddToFavorites(false);
}
function AddToFavorites(isTip)
{
    if(GetCookie("isCollect") && !isTip){

        return
    }
    else {
        SetCookie("isCollect","1");


        var title = document.title;
        var url = location.href;
        if (window.sidebar) // Firefox
            window.sidebar.addPanel(title, url, '');
        else if (window.opera && window.print) // Opera
        {
            var elem = document.createElement('a');
            elem.setAttribute('href', url);
            elem.setAttribute('title', title);
            elem.setAttribute('rel', 'sidebar'); // required to work in opera 7+
            elem.click();
        }
        else if (navigator.userAgent.indexOf('UCBrowser') > -1) {//UC
            window.location.href = "ext:add_favorite";
        }
        else if (document.all) // IE
            window.external.AddFavorite(url, title);
        else {
            if(isTip){
                alert("该浏览器不支持自动收藏，请点击Ctrl+D手动收藏！");
            }
        }
    }

}



function SetCookie(name, value) {
    var key = '';
    var Days = 365;
    var exp = new Date();
    var domain = "";
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    if (key == null || key == "") {
        document.cookie = name + "=" + encodeURI(value) + ";expires=" + exp.toGMTString() + ";path=/;domain=" + domain + ";";
    }
    else {
        var nameValue = GetCookie(name);
        if (nameValue == "") {
            document.cookie = name + "=" + key + "=" + encodeURI(value) + ";expires=" + exp.toGMTString() + ";path=/;domain=" + domain + ";";
        }
        else {
            var keyValue = getCookie(name, key);
            if (keyValue != "") {
                nameValue = nameValue.replace(key + "=" + keyValue, key + "=" + encodeURI(value));
                document.cookie = name + "=" + nameValue + ";expires=" + exp.toGMTString() + ";path=/;domain=" + domain + ";";
            }
            else {
                document.cookie = name + "=" + nameValue + "&" + key + "=" + encodeURI(value) + ";expires=" + exp.toGMTString() + ";path=/;" + domain + ";";
            }
        }
    }
}

function GetCookie(name) {
    var nameValue = "";
    var key = "";
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)) {
        nameValue = decodeURI(arr[2]);
    }
    if (key != null && key != "") {
        reg = new RegExp("(^| |&)" + key + "=([^(;|&|=)]*)(&|$)");
        if (arr = nameValue.match(reg)) {
            return decodeURI(arr[2]);
        }
        else return "";
    }
    else {
        return nameValue;
    }
}


function DelCookie(name)

{

    var exp = new Date();

    exp.setTime(exp.getTime() - 1);

    var cval=GetCookie(name);

    if(cval!=null)

        document.cookie= name + "="+cval+";expires="+exp.toGMTString();

}




