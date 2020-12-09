const path = require('path')

const FileManagerPlugin = require('filemanager-webpack-plugin')

function resolve (dir) {
  return path.join(__dirname, dir) //path.join(__dirname)设置为绝对路径
}

//vue.config.js 配置
module.exports = {
  runtimeCompiler: true,
  publicPath: '/', // 设置打包文件相对路径
  configureWebpack: {
    devtool: 'source-map',
    plugins: [
      // help => https://www.npmjs.com/package/filemanager-webpack-plugin
      new FileManagerPlugin({  //初始化 filemanager-webpack-plugin 插件实例
        events: {
          onEnd: {
            delete: [   //首先需要删除项目根目录下的dist.zip
              './dist.zip',
            ],
            archive: [ //然后我们选择dist文件夹将之打包成dist.zip并放在根目录
              { source: './dist', destination: './dist.zip' },
            ]
          }
        }
      })
    ],
  },
  chainWebpack: (config) => {
    config.resolve.alias
      .set('@', resolve('src'))
      .set('components', resolve('src/components'))
      .set('views', resolve('src/views'))
      .set('assets', resolve('src/assets'))
      .set('page', resolve('src/page'))
  },
  devServer: {
    host: 'localhost',
    port: 8222,
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8080/', //对应自己的接口
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '/api': '/api'
        }
      }
    }
  },
}