export const booleanToString = (value?: Boolean) => {
    return value == true ? 'Да' : (value == false ? 'Нет' : undefined)
}

export const booleanToNumber = (value?: Boolean) => {
    return value == true ? 1 : (value == false ? 0 : undefined)
}