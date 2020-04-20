//转换data为pagination
export function setPagination(pagination,data){
  pagination.current=data.pageNum
  pagination.pageSize=data.pageSize
  pagination.total=data.total
}

export function calcCurrentNum(pagination){
 return (pagination.current-1) * pagination.pageSize+1;
}

export function tansformPagination(pagination){
  return {
    "pageNum":pagination.current,
    "pageSize":pagination.pageSize
  }
}