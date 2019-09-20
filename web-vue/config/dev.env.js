'use strict';
const merge = require('webpack-merge');
const prodEnv = require('./prod.env');

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  FILE_NAME: '"dev"',
  API_DOMAIN: '"localhost:8086"',
  IMAGE_DOMAIN: '"localhost:8080"'
});
