<template>
  <div>
    <!-- 查询组件 -->
    <div class="search-container">
      <!-- BeanName jobName updateTime -->
      <a-form
        layout="inline"
        :form="jobLogQueryForm"
      >
        <a-form-item>
          <a-input addonBefore="beanName" v-model="jobLogQueryForm.beanName">
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-input addonBefore="jobName" v-model="jobLogQueryForm.jobName">
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-select
            defaultValue=""
            style="width: 120px"
            v-model="jobLogQueryForm.isSuccess"
          >
            <a-select-option value="">全部</a-select-option>
            <a-select-option value="true">正常</a-select-option>
            <a-select-option value="false">失败</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
            <a-range-picker
      :showTime="{ format: 'HH:mm' }"
      format="YYYY-MM-DD HH:mm"
      :placeholder="['开始时间', '结束时间']"
      @change="queryCreateTimeRangeChange"
    />
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            @click="queryJobLogsEvent"
          >
            查询
          </a-button>

        </a-form-item>
      </a-form>
    </div>
    <!-- table组件 -->
    <div>
      <a-table
        :columns="columns"
        :dataSource="data"
        bordered
        :pagination="pagination"
        @change="onTableChange"
        rowKey="id"
      >
      <a-button slot="exceptionDetail"
          slot-scope="exceptionDetail,obj"
          v-if="!obj.isSuccess"
          @click="showErrorModal(exceptionDetail)"
          >查看错误详情</a-button>


      </a-table>
      
    </div>

<!-- 错误详情modal -->

  <a-modal title="错误详情" v-model="errorModal.visible" @ok="hideErrorModal" okText="确认" cancelText="取消">
      <p>{{errorModal.exceptionDetail}}</p>
    </a-modal>
  </div>
</template>

<script>
import { getJobLogs } from '@/api/quartz'
import { setPagination, calcCurrentNum, tansformPagination } from '@/utils/pagination'
import { formatDate} from '@/utils/date'
const columns = [
  {
    title: 'index',
    dataIndex: 'index',
    width: '5%',
    scopedSlots: { customRender: 'index' },
  },
    {
    title: 'jobName',
    dataIndex: 'jobName',
    scopedSlots: { customRender: 'jobName' },
  },
  {
    title: 'beanName',
    dataIndex: 'beanName',
    width: '10%',
    scopedSlots: { customRender: 'beanName' },
  },
    {
    title: 'traceId',
    dataIndex: 'traceId',
    scopedSlots: { customRender: 'traceId' },
  },
  {
    title: 'cronExpression',
    dataIndex: 'cronExpression',
    width: '10%',
    scopedSlots: { customRender: 'cronExpression' },
  },
  {
    title: 'methodName',
    dataIndex: 'methodName',
    scopedSlots: { customRender: 'methodName' },
  },
  {
    title: 'params',
    dataIndex: 'params',
    scopedSlots: { customRender: 'params' },
  },
    {
    title: '耗时(毫秒)',
    dataIndex: 'time',
    scopedSlots: { customRender: 'time' },
  },
  {
    title: 'createTime',
    dataIndex: 'createTimeShow',
    scopedSlots: { customRender: 'createTimeShow' },
  },
 {
    title: '错误详情',
    dataIndex: 'exceptionDetail',
    scopedSlots: { customRender: 'exceptionDetail' },
  },
];


export default {
  name: 'job-log-manager',
  data () {
    return {
      data: [],
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '50'],
        current: 1,
        pageSize: 10,
        total: 50,
        showSizeChanger: true,
      },
      columns,
      editingKey: '',
      jobLogQueryForm: {
        beanName:null,
        jobName:null,
        isSuccess:null,
        createTimeRange:null,
      },
      errorModal:{
        visible:false,
        exceptionDetail:""
      }
    };
  },
  mounted () {
    this.getJobLogsList()
  },
  methods: {
    getJobLogsList (queryCondition, page) {
      let self = this
      getJobLogs(queryCondition, page).then(res => {
        setPagination(self.pagination, res.data.data)
        let currentNum = calcCurrentNum(self.pagination);
        self.data = []
        res.data.data.list.forEach(function (element, index) {
          element.index = currentNum + index
          element.createTimeShow=formatDate(element.createTime)
          self.data.push(element)
        })

      })
    },
    onTableChange (pagination) {
      let form = this.jobLogQueryForm
      let page = tansformPagination(pagination)
      this.getJobLogsList(form, page)
    },
    queryJobLogsEvent () {
      let form = this.jobLogQueryForm
      let page = {"pageSize":this.pagination.pageSize}
      this.getJobLogsList(form, page)
    },
    queryCreateTimeRangeChange(dates){
      if(dates.length==0){
         this.jobLogQueryForm.createTimeRange=null
      }else{
     this.jobLogQueryForm.createTimeRange=Date.parse(dates[0])+","+Date.parse(dates[1])
      }
    },
    hideErrorModal(){
      this.errorModal.visible=false
    },
    showErrorModal(exceptionDetail){
      this.errorModal.visible=true
      this.errorModal.exceptionDetail=exceptionDetail
    }
  }
}
</script>

<style scoped>
</style>