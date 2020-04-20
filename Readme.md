# Readme.md

官方文档地址: https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/htmlsingle/#boot-features-quartz

本项目采用的是 @QuartzDataSource 分开配置数据源

## 项目结构

前端页面: front
后台服务: back

## jobs

http://localhost:8080/api/jobs

{
	"id":3,
	"jobName":"boommanpro的定时任务",
	"beanName":"testTask",
	"methodName":"run1",
	"params":"123",
	"cronExpression":"0/10 * * * * ?",
	"isPause":false,
	"remark":"boommanpro的第一个定时任务"
}

## 展示:





