<template>
  <div>
    <!-- 查询组件 -->
    <div class="search-container">
      <!-- BeanName jobName updateTime -->
      <a-form
        layout="inline"
        :form="jobQueryForm"
      >
        <a-form-item>
          <a-input
            addonBefore="beanName"
            v-model="jobQueryForm.beanName"
          >
          </a-input>
        </a-form-item>
        <a-form-item>
          <a-input
            addonBefore="jobName"
            v-model="jobQueryForm.jobName"
          >
          </a-input>

        </a-form-item>

        <a-form-item>
          <a-select
            defaultValue=""
            style="width: 120px"
            v-model="jobQueryForm.isPause"
          >
            <a-select-option value="">全部</a-select-option>
            <a-select-option value="false">启动</a-select-option>
            <a-select-option value="true">暂停</a-select-option>
          </a-select>
        </a-form-item>
        <br>
        <a-form-item>
          <label>创建时间:&nbsp;&nbsp;</label>
          <a-range-picker @change="queryCreateTimeRangeChange" />
        </a-form-item>
        <a-form-item>
          <label>更新时间:&nbsp;&nbsp;</label>
          <a-range-picker @change="queryUpdateTimeRangeChange" />
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            @click="queryJobEvent"
          >
            查询
          </a-button>

        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            @click="addJobEvent"
          >
            添加
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
        @change="pageChange"
        rowKey="id"
      >

        <div
          @click="updateStatus(obj.id)"
          slot="isPause"
          slot-scope="isPause,obj"
        >

          <a-button
            shape="circle"
            type="primary"
            v-if="!isPause"
          >启动中</a-button>
          <a-button
            shape="circle"
            type="danger"
            v-else
          >暂停中</a-button>
        </div>
        <div
          slot="action"
          slot-scope="id,obj"
          type="danger"
        >
          <a-button
            style="    margin-right: 10px;"
            type="primary"
            @click="updateJob(obj)"
          >修改</a-button>
          <a-button
            type="danger"
            @click="doDeleteJob(id)"
            v-if="obj.isPause"
          >删除</a-button>
        </div>

      </a-table>
    </div>

    <!-- 添加modal Start-->

    <a-modal
      title="添加Job"
      v-model="addModalConfig.visible"
      @ok="handlerAdd"
    >
      <!-- 添加Job -->
      <a-form
        :label-col="{ span: 5 }"
        :wrapper-col="{ span: 12 }"
        :form="jobAddForm"
      >
        <a-form-item label='jobName'>
          <a-input v-model="jobAddForm.jobName" />
        </a-form-item>
        <a-form-item label='beanName'>
          <a-input v-model="jobAddForm.beanName" />
        </a-form-item>

        <a-form-item label='methodName'>
          <a-input v-model="jobAddForm.methodName" />
        </a-form-item>

        <a-form-item label='params'>
          <a-input v-model="jobAddForm.params" />
        </a-form-item>

        <a-form-item label='cronExpression'>
          <a-input v-model="jobAddForm.cronExpression" />
        </a-form-item>
        <a-form-item label='remark'>
          <a-input
            type='textarea'
            v-model="jobAddForm.remark"
          />
        </a-form-item>
      </a-form>
    </a-modal>

    <!-- 添加modal End-->

    <!-- 修改modal Start-->
    <a-modal
      title="修改Job"
      v-model="updateModalConfig.visible"
      @ok="handlerUpdate"
    >
      <!-- 添加Job -->
      <a-form
        :label-col="{ span: 5 }"
        :wrapper-col="{ span: 12 }"
        :form="jobUpdateForm"
      >
        <a-form-item label='jobName'>
          <a-input v-model="jobUpdateForm.jobName" />
        </a-form-item>
        <a-form-item label='beanName'>
          <a-input v-model="jobUpdateForm.beanName" />
        </a-form-item>

        <a-form-item label='methodName'>
          <a-input v-model="jobUpdateForm.methodName" />
        </a-form-item>

        <a-form-item label='params'>
          <a-input v-model="jobUpdateForm.params" />
        </a-form-item>

        <a-form-item label='cronExpression'>
          <a-input v-model="jobUpdateForm.cronExpression" />
        </a-form-item>
        <a-form-item label='remark'>
          <a-input
            type='textarea'
            v-model="jobUpdateForm.remark"
          />
        </a-form-item>
      </a-form>
    </a-modal>
    <!-- 修改modal End -->
  </div>

</template>

<script>

//引入方法
import { getJobs, updateIsPause, deleteJob, createJob, updateJob } from '@/api/quartz'
import { setPagination, calcCurrentNum, tansformPagination } from '@/utils/pagination'
import { formatDate } from '@/utils/date'

//beanName: "testTask"
//cronExpression: "0/10 * * * * ?"
//id: 3
//isPause: true
//jobName: "boommanpro的定时任务"
//methodName: "run1"
//params: "123"
//remark: "boommanpro的第一个定时任务"
//updateTime: "2020-04-18T07:25:42.000+0000"


const columns = [
  {
    title: 'index',
    dataIndex: 'index',
    width: '5%',
    scopedSlots: { customRender: 'index' },
  },
  {
    title: 'beanName',
    dataIndex: 'beanName',
    width: '7%',
    scopedSlots: { customRender: 'beanName' },
  },
  {
    title: 'cronExpression',
    dataIndex: 'cronExpression',
    width: '10%',
    scopedSlots: { customRender: 'cronExpression' },
  },

  {
    title: 'jobName',
    dataIndex: 'jobName',
    scopedSlots: { customRender: 'jobName' },
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
    title: 'remark',
    dataIndex: 'remark',
    scopedSlots: { customRender: 'remark' },
  },
  {
    title: '创建时间',
    dataIndex: 'createTimeShow',
    scopedSlots: { customRender: 'createTimeShow' },
  },
  {
    title: '更新时间',
    dataIndex: 'updateTimeShow',
    scopedSlots: { customRender: 'updateTimeShow' },
  },
  {
    title: 'status',
    dataIndex: 'isPause',
    width: '5%',
    scopedSlots: { customRender: 'isPause' },
  },
  {
    title: 'Action',
    dataIndex: 'id',
    scopedSlots: { customRender: 'action' },
  },
];

export default {
  name: 'job-manager',
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
      jobQueryForm: {
        beanName: null,
        jobNAme: null,
        isPause: null,
        createTimeRange: null,
        updateTimeRange: null
      },
      jobAddForm: {
        jobName: null,
        beanName: null,
        methodName: null,
        params: null,
        cronExpression: null,
        remark: null
      },
      jobUpdateForm: {
        id: null,
        jobName: null,
        beanName: null,
        methodName: null,
        params: null,
        cronExpression: null,
        remark: null
      },
      addModalConfig: {
        visible: false
      },
      updateModalConfig: {
        visible: false
      }
    };
  },
  mounted () {
    this.getJobList()
  },
  methods: {

    getJobList (queryForm, page) {
      let self = this
      getJobs(queryForm, page).then(res => {
        let currentNum = calcCurrentNum(self.pagination);
        setPagination(self.pagination, res.data.data)
        self.data = []
        res.data.data.list.forEach(function (element, index) {
          element.index = currentNum + index
          element.updateTimeShow = formatDate(element.createTime)
          element.createTimeShow = formatDate(element.updateTime)
          self.data.push(element)
        })
        setPagination(self.pagination, res.data.data)
      })
    },
    onTableChange (date, dateString) {
      console.log(date, dateString);
      console.log(this.jobQueryForm)
    },
    pageChange (pagination) {
      let form = this.jobQueryForm
      let page = tansformPagination(pagination)
      this.getJobList(form, page)
    },
    addJobEvent () {
      this.addModalConfig.visible = true;
    },
    handlerAdd () {
      let self = this
      createJob(this.jobAddForm).then(res => {
        console.log(res)
        self.queryJobEvent();
        this.addModalConfig.visible = false;
      })


    },
    queryJobEvent () {
      let form = this.jobQueryForm
      let page = { "pageSize": this.pagination.pageSize }
      this.getJobList(form, page)
    },
    queryCreateTimeRangeChange (dates) {
      if (dates.length == 0) {
        this.jobQueryForm.createTimeRange = null
      } else {
        this.jobQueryForm.createTimeRange = Date.parse(dates[0]) + "," + Date.parse(dates[1])
      }
    },
    queryUpdateTimeRangeChange (dates) {
      if (dates.length == 0) {
        this.jobQueryForm.updateTimeRange = null
      } else {
        this.jobQueryForm.updateTimeRange = Date.parse(dates[0]) + "," + Date.parse(dates[1])
      }
    },
    updateStatus (id) {
      updateIsPause(id).then(res => {
        console.log(res)
        this.queryJobEvent();
      })
    },
    doDeleteJob (id) {
      let self = this
      deleteJob(id).then(res => {
        console.log(res)
        self.queryJobEvent();
      })

    },
    updateJob (obj) {
      this.jobUpdateForm.id = obj.id
      this.jobUpdateForm.jobName = obj.jobName
      this.jobUpdateForm.beanName = obj.beanName
      this.jobUpdateForm.methodName = obj.methodName
      this.jobUpdateForm.params = obj.params
      this.jobUpdateForm.cronExpression = obj.cronExpression
      this.jobUpdateForm.remark = obj.remark
      this.updateModalConfig.visible = true
    },
    handlerUpdate () {
      let self = this
      updateJob(this.jobUpdateForm).then(res => {
        console.log(res)
        self.queryJobEvent();
        self.updateModalConfig.visible = false
      })
    }
  },


}
</script>

<style scoped>
.search-container {
  text-align: left;
  margin-bottom: 20px;
}
</style>