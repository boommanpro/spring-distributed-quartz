import request from '@/request/request'

import { queryString } from '@/request/transform'


export function getJobs (form, page) {
  return request.get(
    '/api/jobs' + queryString(form, page)
  )
}

export function getJobLogs (form, page) {
  return request.get(
    '/api/jobLogs' + queryString(form, page)
  )
}


export function createJob (body) {
  return request.post(
    '/api/jobs',
    body
  )
}

export function updateJob (body) {
  return request.put(
    '/api/jobs',
    body
  )
}

export function deleteJob (id) {
  return request.delete(
    '/api/jobs/' + id
  )
}

export function updateIsPause (id) {
  return request.put(
    '/api/jobs/' + id,
  )
}


export function execution (id) {
  return request.post(
    '/api/jobs/exec' + id,
  )
}

