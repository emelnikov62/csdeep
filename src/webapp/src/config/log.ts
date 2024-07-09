import log, {LogLevelDesc} from "loglevel"
import prefix from "loglevel-plugin-prefix"

prefix.reg(log)
log.setLevel(process.env.LOG_root?.toUpperCase().trim() as LogLevelDesc || "info")

prefix.apply(log, {
    template: '[%t] %l (%n):',
    levelFormatter: (level) => level.toUpperCase(),
    nameFormatter: (name) => name || "root",
    timestampFormatter: (date) => date.toISOString()
})

export default log

export const getLogger = (name: string) => {
    const logger = log.getLogger(name)
    logger.setLevel(process.env[`LOG_${name}`]?.toUpperCase().trim() as LogLevelDesc || log.getLevel())
    return logger
}

export enum LogLevel {
    TRACE,
    DEBUG,
    INFO,
    WARN,
    ERROR,
    SILENT
}
