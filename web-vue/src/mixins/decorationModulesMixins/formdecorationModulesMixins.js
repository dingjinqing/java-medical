export default {
  methods: {
    // 页面装修模块数据填充处理
    handleToAddModules (index) {
      let obj = {}
      switch (index) {
        case 0: // 姓名模块
          obj = {
            'module_name': 'm_input_name',
            'form_title': '姓名',
            'image_type': 0,
            'confirm': 0,
            'ok_ajax': 1
          }
          break
        case 1: // 姓名模块
          obj = {
            'module_name': 'm_input_mobile',
            'form_title': '手机号',
            'image_type': 0,
            'confirm': 0,
            'ok_ajax': 1
          }
          break
        case 2: // 省市区模块
          obj = {
            'module_name': 'm_address',
            'form_title': '省/市/区',
            'with_detail': 0,
            'confirm': 0,
            'ok_ajax': 1
          }
          break
      }
      console.log(obj)
      return obj
    }
  }
}
