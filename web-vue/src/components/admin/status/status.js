const status = [
  {
    status: null,
    name: '全部活动'
  },
  {
    status: 0,
    name: '进行中'
  },
  {
    status: 1,
    name: '未开始'
  }, {
    status: 2,
    name: '已过期'
  }, {
    status: 3,
    name: '已停用'
  }
]

export const standardStatus = [
  {
    status: 0,
    name: '全部活动'
  },
  {
    status: 1,
    name: '进行中'
  },
  {
    status: 2,
    name: '未开始'
  }, {
    status: 3,
    name: '已过期'
  }, {
    status: 4,
    name: '已停用'
  }
]

export const getById = id => status.find(i => i.status === id)

export const getByName = name => status.find(i => i.name === name)

export const getNameById = id => status.find(i => i.status === id).name

export const getByIdStandard = id => standardStatus.find(i => i.status === id)

export const getByNameStandard = name => standardStatus.find(i => i.name === name)

/**
 * 是否可以编辑
 * @param {*} row 活动
 */
export const couldEdit = row => [status[1], status[2]].map(({status}) => status).includes(row.status)

/**
 * 是否可以停用
 * @param {*} row 活动
 */
export const couldStop = row => [status[1], status[2]].map(({status}) => status).includes(row.status)

/**
 * 是否可以启用
 * @param {*} row 活动
 */
export const couldStart = row => row.status === status[4].status

/**
 * 是否可以删除
 * @param {*} row 活动
 */
export const couldDelete = row => [status[2], status[3], status[4]].map(({status}) => status).includes(row.status)

export default status
