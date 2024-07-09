export const formatDate = (ldt?: string, longYear = false, time = false) => {
    if (ldt) {
        const indexT = ldt.indexOf('T')
        const dateStr = indexT === -1 ? ldt : ldt.substring(0, indexT)
        let timeStr = indexT === -1 ? '' : ldt.substring(indexT + 1, ldt.length)
        const indexP = timeStr.indexOf('.')
        timeStr = indexP === -1 ? timeStr : timeStr.substring(0, indexP)
        const dateArray = dateStr.split('-')
        const yearString = longYear ? dateArray[0] : dateArray[0].substring(2, 4)
        return dateArray[2] + '.' + dateArray[1] + '.' + yearString + (time ? (' ' + timeStr) : '')
    }
    return ''
}

export const dateToIsoString = (date: Date) => {
    if (date == null)
        return null;
    date.setHours(date.getHours() - (date.getTimezoneOffset() / 60)) //TODO: разобраться со сдвигом времени и убрать костыль
    return date.toISOString()
}

export const getCurrentDate = () => {
    const currentDateWithTime = new Date();
    return new Date(currentDateWithTime.toDateString());
}

export const dateOnlyToIsoString = (date: Date | null | undefined) => {
    if (!date || isNaN(date.getTime()))
        return null;

    const isoString = date.toISOString();
    const indexT = isoString.indexOf('T');
    const dateStr = indexT === -1 ? isoString : isoString.substring(0, indexT);
    return dateStr
}

export const truncateDate = (srcDate:Date) => {
    return (new Date(srcDate.getTime() - ((srcDate.getTime() - srcDate.getTimezoneOffset() * 60 * 1000) % (3600 * 1000 * 24))));
}

function _formatDatetime(date: Date, format: string) {
    const _padStart = (value: number): string => value.toString().padStart(2, '0');
    return format
        .replace(/yyyy/g, _padStart(date.getFullYear()))
        .replace(/dd/g, _padStart(date.getDate()))
        .replace(/mm/g, _padStart(date.getMonth() + 1))
        .replace(/hh/g, _padStart(date.getHours()))
        .replace(/ii/g, _padStart(date.getMinutes()))
        .replace(/ss/g, _padStart(date.getSeconds()));
}
function isValidDate(d: Date): boolean {
    return !isNaN(d.getTime());
}
export function formatDateTime(date: any): string {
    const datetime = new Date(date);
    return isValidDate(datetime) ? _formatDatetime(datetime, 'dd.mm.yyyy hh:ii:ss') : '';
}