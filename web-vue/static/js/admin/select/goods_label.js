$(function () {
  function getSelectIds () {
    let select_ids = $('#select_label_ids').val() ? $('#select_label_ids').val().split(',') : []
    var array = []
    for (var index in select_ids) {
      array.push(parseInt(select_ids[index]))
    }
    return array
  }
  function setSelectIds (select_ids) {
    return $('#select_label_ids').val(select_ids.join(','))
  }
  function getPageIds () {
    return JSON.parse($('#page_all_ids').val() || '{}')
  }
  function getAllIds () {
    return JSON.parse($('#all_label_ids').val() || '{}')
  }

  $('.select-table-tbody tr').click(function () {
    if (event.target.localName !== 'input') {
      $(this).find('.select-row').click()
    }
  })
  $('.select-table-tbody .select-row').click(function () {
    let row_id = parseInt($(this).val())
    let select_ids = getSelectIds()
    if ($(this).is(':checked')) {
      select_ids.push(row_id)
      $(this).parent().parent().addClass('has-select-row')
    } else {
      select_ids.splice($.inArray(row_id, select_ids), 1)
      $(this).parent().parent().removeClass('has-select-row')
    }
    setSelectIds(select_ids)
    changeSelectPage(select_ids)
    changeSelectAll(select_ids)
  })

  $('.select-page-ids').click(function () {
    let page_ids = getPageIds()
    let select_ids = getSelectIds()
    if ($(this).is(':checked')) {
      select_ids = util.mergeArray(select_ids, page_ids)
    } else {
      for (var index in page_ids) {
        select_ids.splice($.inArray(page_ids[index], select_ids), 1)
      }
    }
    setSelectIds(select_ids)
    changeSelectAll(select_ids)
    changeSelectRow(select_ids)
  })

  $('.select-all-ids').click(function () {
    let select_ids = []
    if ($(this).is(':checked')) {
      let all_ids = getAllIds()
      select_ids = util.mergeArray(getSelectIds(), all_ids)
    }
    setSelectIds(select_ids)
    changeSelectPage(select_ids)
    changeSelectRow(select_ids)
  })

  // 初始化全选按钮
  changeSelectPage(getSelectIds())
  changeSelectAll(getSelectIds())

  function changeSelectRow (select_ids) {
    $('.select-table-tbody .select-row').each(function () {
      let row_id = parseInt($(this).val())
      if ($.inArray(row_id, select_ids) >= 0) {
        $(this).prop('checked', true)
        $(this).parent().parent().addClass('has-select-row')
      } else {
        $(this).prop('checked', false)
        $(this).parent().parent().removeClass('has-select-row')
      }
    })
  }
  function changeSelectPage (select_ids) {
    let page_ids = getPageIds()
    console.log(select_ids, page_ids, util.isSubArray(page_ids, select_ids))
    if (util.isSubArray(page_ids, select_ids)) {
      $('.select-page-ids').prop('checked', true)
    } else {
      $('.select-page-ids').prop('checked', false)
    }
  }
  function changeSelectAll (select_ids) {
    let all_ids = getAllIds()
    console.log(select_ids, all_ids, util.isSubArray(all_ids, select_ids))
    if (util.isSubArray(all_ids, select_ids)) {
      $('.select-all-ids').prop('checked', true)
    } else {
      $('.select-all-ids').prop('checked', false)
    }
  }
})
