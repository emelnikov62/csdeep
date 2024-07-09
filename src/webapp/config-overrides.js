const path = require("path")
const DotenvWebpackPlugin = require("dotenv-webpack")
const CopyWebpackPlugin = require("copy-webpack-plugin")

module.exports = function override(config) {
    config.output.path = path.resolve(__dirname, "../main/resources/static")

    config.resolve.alias = {
        "$src": path.resolve(__dirname, "src"),
        "$public": path.resolve(__dirname, "public"),
        "$config": path.resolve(__dirname, "src", "config"),
        "$store": path.resolve(__dirname, "src", "store"),
        "$pages": path.resolve(__dirname, "src", "pages"),
        "$components": path.resolve(__dirname, "src", "components"),
        "$utils": path.resolve(__dirname, "src", "utils")
    }

    config.resolve.extensions.push(".d.ts")

    const i = config.plugins.findIndex(p => p.constructor.name === "DefinePlugin")
    if (i >= 0)
        config.plugins.splice(i, 1, new DotenvWebpackPlugin({ systemvars: true }))

    config.plugins.push(
        new CopyWebpackPlugin({
            patterns: [{
                from: "./public/",
                to: path.resolve(__dirname, '../main/resources/static/'),
                filter: (filepath) => !filepath.endsWith("index.html")}]
        })
    )

    return config
}
