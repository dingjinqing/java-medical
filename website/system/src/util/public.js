import { getServiceConfig } from '@/api/admin/storeManage/storemanage/serviceManage'

export default {
  // 获取门店技师职称
  async getJobTitle () {
    let jobtitle = localStorage.getItem('jobtitle')
    if (jobtitle === null || jobtitle === '') {
      let data = await getServiceConfig()
      let title = data.content.technician_title
      localStorage.setItem('jobtitle', title)
      return title
    } else {
      return jobtitle
    }
  }
}
