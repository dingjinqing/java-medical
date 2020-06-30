const routes = [
  //   医师列表
  {
    path: '/admin/home/main/doctor/list',
    name: 'doctorList',
    meta: {
      crumbTitle: 'router.doctorList',
      meta: 'doctor_manger',
      category: 'doctor'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/doctor_manger/doctor/doctorList'
      )
  },
  //   医师职称
  {
    path: '/admin/home/main/doctor/professionalTitle',
    name: 'doctorProfessionalTitle',
    meta: {
      crumbTitle: 'router.doctorProfessionalTitle',
      meta: 'doctor_manger',
      category: 'doctorProfessionalTitle'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/doctor_manger/doctorProfessionalTitle/doctorProfessionalTitle'
      )
  },
  //   科室管理
  {
    path: '/admin/home/main/doctor/offices/list',
    name: 'officesList',
    meta: {
      crumbTitle: 'router.officesList',
      meta: 'doctor_manger',
      category: 'officesList'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/doctor_manger/offices/officesList'
      )
  }
]

export default routes
