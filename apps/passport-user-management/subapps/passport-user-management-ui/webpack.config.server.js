const path = require('path');
module.exports = {
    name: 'server',
    target: 'node',
    entry: './source/server.ts',
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'server.js',
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader'
            },
        ],
    },
    resolve: {
        extensions: ['.tsx', '.ts', '.js'],
        fallback: {
            assert: require.resolve("assert/"),
            buffer: require.resolve("buffer/"),
            crypto: require.resolve("crypto-browserify"),
            //"crypto-browserify": false,
            fs: false,
            http: require.resolve("stream-http"),
            //https: false,
            net: false,
            path: require.resolve("path-browserify"),
            //querystring: false,
            stream: require.resolve("stream-browserify"),
            //string_decoder: false,
            //tls: false,
            util: require.resolve("util/"),
            url: require.resolve("url/"),
            zlib: require.resolve("browserify-zlib"),
        }
    },
    
};