const path =require('path')

function resolve(dir){
  return path.join(__dirname,dir) //path.join(__dirname)设置为绝对路径
}

//vue.config.js 配置
module.exports={
  runtimeCompiler: true,
  publicPath: '/', // 设置打包文件相对路径
  configureWebpack:{
    devtool: 'source-map'
  },
  chainWebpack:(config)=>{
    config.resolve.alias
    .set('@',resolve('src'))
    .set('components',resolve('src/components'))
    .set('views',resolve('src/views'))
    .set('assets',resolve('src/assets'))
    .set('page',resolve('src/page'))
  },
  devServer:{
    host:'localhost',
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
  }
}