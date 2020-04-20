
//只支持单层表单
export function jsonTransform (values) {
  const form = new FormData();
  for (var key in values) {//遍历json对象的每个key/value对,p为key
    form.append(key, values[key]);
  }
  return form
}

export function queryString (form, page) {
  if(!form&&!page){
    return ""
  }
  if(form&&page){
    return "?" + stringify(form) +"&"+ stringify(page)
  }
  if(form){
    return "?"+stringify(form)
  }
  if(page){
    return "?"+stringify(page)
  }
}

function stringify (obj, sep, eq) {
  sep = sep || '&';
  eq = eq || '=';
  var tmp = [];
  var res = [];
  for (var item in obj) {
    if (Array.isArray(obj[item])) {
      obj[item].forEach(function (value) {
        tmp.push(item, value);
        res.push(tmp.join(eq));
        tmp.length = 0;
      });
    } else {
      tmp.push(item, obj[item]);
      res.push(tmp.join(eq));
      tmp.length = 0;
    }
  }
  return res.join(sep);
}