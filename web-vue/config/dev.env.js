'use strict';
const merge = require('webpack-merge');
const prodEnv = require('./prod.env');

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // API_DOMAIN: '"jmpdev.weipubao.cn"',
  API_DOMAIN: '"localhost:8086"',
  IMAGE_DOMAIN: '"jmpdevimg.weipubao.cn"'
});
