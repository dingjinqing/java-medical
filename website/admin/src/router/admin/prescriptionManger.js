const routes = [
  //   医师列表
  {
    path: '/admin/home/main/prescription/list',
    name: 'prescriptionList',
    meta: {
      crumbTitle: 'router.prescriptionList',
      meta: 'prescription_manger',
      category: 'prescription'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/prescription_manger/prescription/prescriptionList'
      )
  }
]

export default routes
