(function () {
  $('.title-head').click(function () {
    $(this)
      .addClass('title-active')
      .siblings()
      .removeClass('title-active')
    $(this)
      .parent()
      .next()
      .find('.content-account')
      .eq($(this).index())
      .show()
      .siblings()
      .hide()
  })
  $('input').keyup(function (event) {
    if (event.which == 13) {
      $('.btn-login').click()
    }
  })

  $('.btn-login').click(function () {
    var el = $(this).parents('.content-account')
    if (el.find("[name='username']").val().length <= 0) {
      el.find("[name='username']").focus()
      return false
    }

    if (el.find("[name='password']").val().length <= 0) {
      el.find("[name='password']").focus()
      return false
    }

    // if ($('[name="is_sub_login"]').prop("checked")) {
    if (el.find('[name="is_sub_login"]').val() == 1) {
      if (el.find("[name='sub_username']").val().length <= 0) {
        el.find("[name='sub_username']").focus()
        return false
      }
    }

    var url = '/admin/login/attempt'
    var param = {}
    param.username = el.find("[name='username']").val()
    param.sub_username = el.find("[name='sub_username']").val()
    param.password = el.find("[name='password']").val()
    //if($("[name='is_sub_login']").prop("checked"))
    if (el.find('[name="is_sub_login"]').val() == 1) param.is_sub_login = 1
    util.ajax_json(
      url,
      function (d) {
        if (d && d.error == 0) {
          if (el.find('[name="is_sub_login"]').val() == 1) {
            el.find('#loginFormSub').submit()
          } else {
            el.find('#loginForm').submit()
          }
          return true
        } else if (d && d.error < 0) {
          if (d.error == -1) {
            // if ($("[name='is_sub_login']").prop("checked")) {
            if (el.find('[name="is_sub_login"]').val() == 1) {
              el.find("[name='sub_username']").focus()
            } else {
              el.find("[name='username']").focus()
            }
          } else if (d.error == -2) {
            el.find("[name='password']").focus()
          } else if (d.error == -3) {
            el.find("[name='username']").focus()
          }
          layer.msg(d.message)
          return false
        }
      },
      param
    )
  })
})()
