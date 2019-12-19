const AppSettings = {
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

const PageSettings = {
  "lifecycle": [
    ['onLoad'],
    ['onShow'],
    ['onReady'],
    ['onHide'],
    ['onUnload'],
    ['onPullDownRefresh'],
    ['onReachBottom'],
    // ['onShareAppMessage'],
    ['onPageScroll'],
    ['onTabItemTap'],
    ['onResize'],
  ],
  "props_o": [
    'data',
  ],
  "props_a": []
};


const ComponentSettings = {
  "lifecycle": [
    ['created'],
    ['ready'],
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
    'lifetimes.attached': 'attached',
    'lifetimes.moved': 'moved',
    'lifetimes.detached': 'detached',
  }
};

module.exports = {
  AppSettings: AppSettings,
  PageSettings: PageSettings,
  ComponentSettings: ComponentSettings,
}