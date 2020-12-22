export function formatDate(date: Date): string {
    let d: Date = new Date(date)
    const year: string = d.getFullYear().toString()
    const month: number = d.getMonth() + 1
    let monthString
    let dayString
    if(month <  10){
        monthString = `0${month}`
    }
    else monthString = month.toString()
    const day: number = d.getDate()
    if(day < 10){
        dayString = `0${day}`
    }
    else dayString = day.toString()


    return `${dayString}/${monthString}/${year}`;
}

export function isCallOpen(openingDate: Date, closingDate: Date): boolean{
    const todaysDate = new Date();
    return closingDate >= todaysDate && openingDate <= todaysDate
}