export const cn = {
  options: [{
    value: 0,
    label: '按上传时间从晚到早'
  },
  {
    value: 1,
    label: '按上传时间从早到晚'
  },
  {
    value: 2,
    label: '按视频从大到小'
  },
  {
    value: 3,
    label: '按视频从小到大'
  },
  {
    value: 4,
    label: '按视频名称降序'
  },
  {
    value: 5,
    label: '按视频名称升序'
  }
  ],
  // 视频空间数据
  upload: {
    currentVersionTip: '当前版本为{version}，剩余{leftSpace}M内存空间',
    currentVersionTipMore: '体验版100M内存空间，基础版500M内存空间，高级版2048M内存空间，旗舰版10240M内存空间',
    uploadRule: '上传视频仅支持MP4格式，为保障正常播放，只支持上传大小不超过10M，时长不超过3分钟的视频。由用户上传的视频，存储时效为7天，过期自动删除，如有需要请尽快保存',
    knownMore: '了解更多',
    selectAll: '全选',
    batchDelete: '批量删除',
    batchMove: '批量移动',
    noMatchedVideo: '当前文件夹未找到符合要求的视频',
    uploadVideo: '上传视频',
    select: '请选择',
    inputVideoName: '请输入视频名称',
    search: '搜索',
    play: '播放',
    playVideo: '播放视频',
    del: '删除',
    delVideo: '删除视频',
    tip: '提示',
    cancel: '取消',
    ok: '确定',
    uploadFitVideo: '请上传小于{maxVideoSize}的视频',
    selectVideo: '请选择视频',
    browseVideo: '浏览视频'
  }
}
