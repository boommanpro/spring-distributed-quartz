<template>
  <div>
    <h1>Quartz 后台</h1>
    <div class="card-container">
      <a-tabs type="card"
      size="large"
      >
        <a-tab-pane
          tab="Jobs"
          key="1"
        >
          <job-manager></job-manager>
        </a-tab-pane>
        <a-tab-pane
          tab="JobLogs"
          key="2"
        >
          <job-log-manager></job-log-manager>
        </a-tab-pane>
      </a-tabs>
    </div>
  </div>
</template>

<script>
import JobManager from 'components/JobManager'
import JobLogManager from 'components/JobLogManager'
export default {
  name: 'quartz',
  components: {
    JobManager,
    JobLogManager

  }
}
</script>

<style scoped>
/deep/ .ant-tabs-nav-scroll{
    text-align: right;
}
</style>