const routes = [
  //   医师列表
  {
    path: '/admin/home/main/doctor',
    redirect: '/admin/home/main/doctor/list',
    name: 'doctor',
    meta: {
      crumbTitle: 'router.doctorList',
      meta: 'doctor_manger'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/doctor_manger/doctor/doctorManagement'
      ),
    children: [
      // 医师列表
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
      // 添加医师
      {
        path: '/admin/home/main/doctor/addDoctor',
        name: 'addDoctor',
        meta: {
          crumbTitle: 'router.addDoctor',
          meta: 'doctor_manger',
          category: 'doctorList'
        },
        component: () =>
          import(
            '@/view/admin/index/leftNavComponents/doctor_manger/doctor/addAndUpdateDoctor'
          )
      },
      {
        // 编辑医师
        path: '/admin/home/main/doctor/updateDoctor/:doctorId',
        name: 'addDoctor',
        meta: {
          crumbTitle: 'router.addDoctor',
          meta: 'doctor_manger',
          category: 'doctorList'
        },
        component: () =>
          import(
            '@/view/admin/index/leftNavComponents/doctor_manger/doctor/addAndUpdateDoctor'
          )
      }
    ]
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
  // 科室管理
  {
    path: '/admin/home/main/doctor/offices',
    redirect: '/admin/home/main/doctor/offices/list',
    name: 'offices',
    meta: {
      crumbTitle: 'router.officesList',
      meta: 'doctor_manger'
    },
    component: () =>
      import(
        '@/view/admin/index/leftNavComponents/doctor_manger/offices/officesManagement'
      ),
    children: [
      //  医师管理/科室管理/科室列表
      {
        path: '/admin/home/main/doctor/offices/list',
        name: 'officesList',
        meta: {
          crumbTitle: 'router.officesList',
          meta: 'doctor_manger',
          category: 'offices'
        },
        component: () =>
          import(
            '@/view/admin/index/leftNavComponents/doctor_manger/offices/officesList'
          )
      },
      {
        path: '/admin/home/main/doctor/offices/addOffices',
        name: 'addOffices',
        meta: {
          crumbTitle: 'router.officesList',
          meta: 'doctor_manger',
          category: 'officesList'
        },
        component: () =>
          import(
            '@/view/admin/index/leftNavComponents/doctor_manger/offices/addAndUpdateOffices'
          )
      },
      {
        path: '/admin/home/main/doctor/offices/updateOffices/:sortId',
        name: 'updateOffices',
        meta: {
          crumbTitle: 'router.officesList',
          meta: 'doctor_manger',
          category: 'offices'
        },
        component: () =>
          import(
            '@/view/admin/index/leftNavComponents/doctor_manger/offices/addAndUpdateOffices'
          )
      }
    ]
  }
]

export default routes
