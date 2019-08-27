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

export const getByIdStandard = id => standardStatus.find(i => i.status === id)

export const getByNameStandard = name => standardStatus.find(i => i.name === name)

export default status
