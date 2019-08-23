export const AppSettings = {
  "lifecycle": [
    ['onLaunch'],
    ['onShow'],
    ['onHide'],
    ['onError'],
    ['onPageNotFound']
  ],
  "props_o": [],
  "props_a": []
};

export const PageSettings = {
  "lifecycle": [
    ['onLoad'],
    ['onShow'],
    ['onReady'],
    ['onHide'],
    ['onUnload'],
    ['onPullDownRefresh'],
    ['onReachBottom'],
    ['onShareAppMessage'],
    ['onPageScroll'],
    ['onTabItemTap'],
    ['onResize'],
  ],
  "props_o": [
    'data',
  ],
  "props_a": []
};


export const ComponentSettings = {
  "lifecycle": [
    ['created'],
    ['ready'],
    ['attached'],
    ['moved'],
    ['detached'],
    ['pageLifetimes', 'show'],
    ['pageLifetimes', 'hide'],
    ['pageLifetimes', 'resize'],
  ],
  "props_o": [
    'properties',
    'data',
    'observers',
    'relations',
    'options',
    'methods'
  ],
  "props_a": [
    'behaviors',
    'externalClasses',
  ],
  maps: {
    'attached': ['lifetimes', 'attached'],
    'moved': ['lifetimes', 'moved'],
    'detached': ['lifetimes', 'detached'],
  }
};